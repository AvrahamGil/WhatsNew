package com.gil.whatsnew.utils;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.BaseRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class Authentication {

	private static RSAPrivateKey privateKey;
	private static RSAPublicKey publicKey;

	private static String email = "email";
	private static String password = "password";

	private static final String secret = "secret";

	private static final char[] letters = {'L','E','T','T','E','R','S'};
	
	private Random random;
	
	@Autowired
	private BaseRequest baseRequest;

	public String generatedJwtToken(String email, String password) throws ApplicationException {

		random = new Random();
		int number = random.nextInt(8);
		
		
		try {
			algorithmRSA();
			
			KeyFactory kf = KeyFactory.getInstance("RSA");

			PKCS8EncodedKeySpec headerSpec = new PKCS8EncodedKeySpec(getPrivateKey(null).getEncoded());

			PrivateKey prvKey2 = kf.generatePrivate(headerSpec);

			String token = Jwts.builder().claim("email", new String(email)).claim("password", password)
					.setIssuer("159.89.12.15").signWith(SignatureAlgorithm.RS256, prvKey2)
					.compact();

			String[] parts = token.split("[.]");
			
			parts[1] = letters[number] + parts[1];
			
			token = parts[0] + "." + parts[1] + "." + parts[2];
			
			return token;

		} catch (NoSuchAlgorithmException | JWTCreationException | InvalidKeySpecException exception) {
			throw new ApplicationException(ErrorType.General_Error, "Generated token failed", false);
		}
	}

	public boolean verifyJwtToken(String token,String uuid) throws ApplicationException {
		try {
			String[] parts = token.split("[.]");

			parts[1] = parts[1].substring(1, parts[1].length());
			
			String newToken = parts[0] + "." + parts[1] + "." + parts[2];
			
			email = JWT.decode(newToken).getClaim("email").asString();
			password = JWT.decode(newToken).getClaim("password").asString();
			
			if (email != null && password != null) {
				JWTVerifier verifier = JWT.require(algorithmRSA()).withClaim("email", email)
						.withClaim("password", password).withIssuer("159.89.12.15").build();

				verifier.verify(newToken);

				return true;
			}

			return false;

		} catch (NoSuchAlgorithmException | JWTVerificationException exception) {
			throw new ApplicationException(ErrorType.General_Error, "Authentication failed", false);
		}
	}

	public String generateCSRFToken() throws ApplicationException {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			byte[] data = new byte[16];
			secureRandom.nextBytes(data);

			return Base64.getEncoder().encodeToString(data);

		} catch (Exception e) {
			throw new ApplicationException(ErrorType.General_Error, "Generated CSRF token failed", false);
		}
	}

	public boolean verifyCSRFToken(HttpServletRequest request) throws ApplicationException {
		boolean cookieFilled = false;
		
		String csrfCookieValue = "";

		String csrfToken = request.getHeader("X-CSRFTOKEN");

		if(csrfToken == null) return false;
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) return false;
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("X-CSRFTOKEN") && !cookieFilled) {
				csrfCookieValue = cookie.getValue();
				cookieFilled = true;
			}
		}

		if(csrfCookieValue.endsWith("%3D%3D")) {
			csrfCookieValue = csrfCookieValue.replace("%3D", "=");
		}
		
		if(csrfCookieValue.contains("%2F")) {
			csrfCookieValue = csrfCookieValue.replace("%2F", "/");
		}
		if(csrfCookieValue.contains("%2B")) {
			csrfCookieValue = csrfCookieValue.replace("%2B", "+");
		}
		 
		
		if(csrfToken.equals(csrfCookieValue)) {
			return true;
		}
		
		return false;
	}

	public boolean verifyCaptcha(HttpServletRequest request) throws ApplicationException {
		boolean responseCorrect = false;

		try {
			String googleResponse = request.getHeader("recaptcha-response");

			responseCorrect = googleResponse == null || "".equals(googleResponse) ? false : true;

			if (!responseCorrect)
				return false;

			boolean isVerified = baseRequest.verifiedCaptcha(secret, googleResponse);

			return isVerified;

		} catch (IOException e) {
			throw new ApplicationException(ErrorType.General_Error, "Authentication failed", false);
		}
	}

	public ResponseEntity<Object> getConnection(String email, String password) throws ApplicationException {

		try {
			String token = generateCSRFToken();
			String jwtToken = generatedJwtToken(email, password);
			String uuid = UUID.nameUUIDFromBytes(email.getBytes()).toString();
			
			Cookie csrfCookie = new Cookie("X-CSRFTOKEN", token);
			csrfCookie.setMaxAge(15 * 60);

			Cookie jwtCookie = new Cookie("X-TOKEN", jwtToken);
			jwtCookie.setMaxAge(15 * 60);

			Cookie uuidtCookie = new Cookie("X-UUID", uuid);
			uuidtCookie.setMaxAge(15 * 60);
			
			Cookie[] cookies = new Cookie[3];
			
			cookies[0] = csrfCookie;
			cookies[1] = jwtCookie;
			cookies[2] = uuidtCookie;

			ResponseEntity<Object> res = new ResponseEntity<Object>(cookies, HttpStatus.OK);

			return res;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;

	}

	public boolean verifyCookies(HttpServletRequest request) throws ApplicationException {
		boolean tokenVerified = false;
		boolean csrfVerified = false;
		boolean uuidVerified = false;
		
		Cookie[] cookies = request.getCookies();

		if(cookies == null) return false;
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("X-TOKEN")) {
				tokenVerified = verifyJwtToken(cookie.getValue(),null) ? true : false;
			}

			if (cookie.getName().equals("X-CSRFTOKEN")) {
				csrfVerified = verifyCSRFToken(request) ? true : false;
				
				String newToken = generateCSRFToken();
				
				if(csrfVerified) cookie.setValue(newToken);
			}
			if(cookie.getName().equals("X-UUID")) {
				String expectedUuid = UUID.nameUUIDFromBytes(email.getBytes()).toString();
				uuidVerified = expectedUuid.equals(cookie.getValue()) ? true : false;
			}
		}

		if (!tokenVerified || !csrfVerified || !uuidVerified)
			return false;

		return true;

	}

	private Algorithm algorithmRSA() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey publicKey = getPublicKey((RSAPublicKey) keyPair.getPublic());
		RSAPrivateKey privateKey = getPrivateKey((RSAPrivateKey) keyPair.getPrivate());

		Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
		return algorithm;
	}

	private RSAPrivateKey getPrivateKey(RSAPrivateKey key) {

		if (privateKey == null)
			privateKey = key;

		return privateKey;
	}

	private RSAPublicKey getPublicKey(RSAPublicKey key) {

		if (publicKey == null)
			publicKey = key;

		return publicKey;
	}
}
