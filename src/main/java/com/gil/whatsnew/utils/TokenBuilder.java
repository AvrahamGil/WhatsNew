package com.gil.whatsnew.utils;

import java.security.KeyFactory;
import java.security.KeyPair;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;

public class TokenBuilder {

	private static RSAPrivateKey privateKey;
	private static RSAPublicKey publicKey;

	public static String generatedToken(String email, String password)
			throws ApplicationException {

		Date expired = new Date();
		
		try {
			algorithmRSA();
			
			KeyFactory kf = KeyFactory.getInstance("RSA");
				
			PKCS8EncodedKeySpec headerSpec = new PKCS8EncodedKeySpec(getPrivateKey(null).getEncoded());

			PrivateKey prvKey2 = kf.generatePrivate(headerSpec);
				
			String token = Jwts.builder().claim("email", new String(email)).claim("password", password)
					.setIssuer("159.89.12.15").setExpiration(expired).signWith(SignatureAlgorithm.RS256, prvKey2).compact();
			
			return token;
			
		} catch (NoSuchAlgorithmException | JWTCreationException | InvalidKeySpecException exception) {
			throw new ApplicationException(ErrorType.General_Error, "Generated token failed", false);
		}
	}

	public static boolean verifyToken(String token, String email, String password)
			throws ApplicationException {
		long min = ((System.currentTimeMillis() / (1000*60)) % 60) + 15;
		
		try {
		   
			JWTVerifier verifier = JWT.require(algorithmRSA()).acceptExpiresAt(min)
					.withClaim("email", email).withClaim("password", password).withIssuer("159.89.12.15").build(); 

			verifier.verify(token);
			
			return true;
			
		} catch (NoSuchAlgorithmException | JWTVerificationException  exception) {
			throw new ApplicationException(ErrorType.General_Error, "Verified token failed", false);
		}
	}

	private static Algorithm algorithmRSA() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey publicKey = getPublicKey((RSAPublicKey)keyPair.getPublic());
		RSAPrivateKey privateKey = getPrivateKey((RSAPrivateKey) keyPair.getPrivate());
		
		Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
		return algorithm;
	}
	
	private static RSAPrivateKey getPrivateKey(RSAPrivateKey key) {
		
		if(privateKey == null) privateKey = key;
		
		return privateKey;
	}
	
	private static RSAPublicKey getPublicKey(RSAPublicKey key) {
		
		if(publicKey == null) publicKey = key;
		
		return publicKey;
	}
}
