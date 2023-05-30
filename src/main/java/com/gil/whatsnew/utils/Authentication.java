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
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.gil.whatsnew.bean.ErrorLog;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.enums.Cookies;
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

	private static final String secret = "Secret";

	private static String[] countries;
	
	@Autowired
	private BaseRequest baseRequest;

	public String generatedJwtToken(String email,String uuid) throws ApplicationException {

		try {
			algorithmRSA();
			
			KeyFactory kf = KeyFactory.getInstance("RSA");

			PKCS8EncodedKeySpec headerSpec = new PKCS8EncodedKeySpec(getPrivateKey(null).getEncoded());

			PrivateKey prvKey2 = kf.generatePrivate(headerSpec);

			String token = Jwts.builder().claim("email", new String(email)).claim("id", uuid)
					.setIssuer("159.89.12.15").signWith(SignatureAlgorithm.RS256, prvKey2)
					.compact();


			return token;

		} catch (NoSuchAlgorithmException | JWTCreationException | InvalidKeySpecException exception) {
			throw new ApplicationException(ErrorType.General_Error, "Generated token failed", false);
		}
	}

	public String[] verifyJwtToken(String token) throws ApplicationException {

		String[] details = new String[2];
		
		try {
			if(token.startsWith("X-TOKEN=")) {
				String[] values = token.split(";");
				token = values[0].substring(8,values[0].length());
			}
			
			details[0] = JWT.decode(token).getClaim("email").asString();
			
			details[1] = JWT.decode(token).getClaim("id").asString();
			
			JWTVerifier verifier = JWT.require(algorithmRSA()).withClaim("email", details[0]).withClaim("id", details[1])
					.withIssuer("159.89.12.15").build();

			verifier.verify(token);
				
			return details;

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

		String csrfToken = request.getHeader(Cookies.XCSRFTOKEN.getName());

		if(csrfToken == null) return false;
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) return false;
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(Cookies.XCSRFTOKEN.getName()) && !cookieFilled) {
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
			String googleResponse = request.getHeader(Cookies.RECAPTCHA.getName());

			responseCorrect = googleResponse == null || "".equals(googleResponse) ? false : true;

			if (!responseCorrect)
				return false;

			boolean isVerified = baseRequest.verifiedCaptcha(secret, googleResponse);

			return isVerified;

		} catch (IOException e) {
			throw new ApplicationException(ErrorType.General_Error, "Authentication failed", false);
		}
	}

	public ResponseEntity<Object> getConnection(User user,int seconds,HttpServletResponse response) throws ApplicationException {

		try {
			String csrf = generateCSRFToken();
			String uuid = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();
			String jwtToken = generatedJwtToken(user.getEmail(),uuid);
			

			user.setUserId(null);
			user.setPassword(null);
			
			ResponseCookie csrfCookie = ResponseCookie.from(Cookies.XCSRFTOKEN.getName(), csrf)
		            .httpOnly(true)
		            .sameSite("None")
		            .secure(true)
		            .path("/")
		            .maxAge(Math.toIntExact(seconds * 60))
		            .build();
			
			ResponseCookie jwtCookie = ResponseCookie.from(Cookies.XTOKEN.getName(), jwtToken)
		            .httpOnly(true)
		            .sameSite("None")
		            .secure(true)
		            .path("/")
		            .maxAge(Math.toIntExact(seconds * 60))
		            .build();
			

			HttpHeaders headers =new HttpHeaders();
			headers.add(Cookies.XCSRFTOKEN.getName(), csrfCookie.toString());
			headers.add(Cookies.XTOKEN.getName(), jwtCookie.toString());

		    response.addHeader("Set-Cookie", csrfCookie.toString());
		    response.addHeader("Set-Cookie", jwtCookie.toString());

		    
		    ResponseEntity<Object> res = new ResponseEntity<Object>(user,headers,HttpStatus.OK);
		    
			return res;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;

	}

	public boolean verifyCookies(HttpServletRequest request) throws ApplicationException {
		String[] details = new String[2];
		boolean csrfVerified = false;
		boolean tokenVerified = false;

		Cookie[] cookies = request.getCookies();

		if(cookies == null) return false;
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(Cookies.XTOKEN.getName())) {
				details = verifyJwtToken(cookie.getValue());
				tokenVerified = details != null  ? true : false;
			}

			if (cookie.getName().equals(Cookies.XCSRFTOKEN.getName())) {
				csrfVerified = verifyCSRFToken(request) ? true : false;
	
				String newToken = generateCSRFToken();
				
				if(csrfVerified) cookie.setValue(newToken);
			}
		}

		if (!tokenVerified || !csrfVerified)
			return false;

		
		return true;

	}

	
	public boolean isCountryExist(String countryValue) {
		countries = Locale.getISOCountries();
			
		for(String country : countries) {
			if(country.equals(countryValue)) {
				return true;
			}	
		}
		return false;
	}

	public void errorLogFile(ErrorLog errorLog) throws ApplicationException {
		try {
			JsonUtils.writeIntoJsonFile("errorLog", errorLog, StringPaths.getLogs("errorLog"));
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
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
