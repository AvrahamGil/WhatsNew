package com.gil.whatsnew.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.utils.Authentication;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@RestController
@RequestMapping("/donates")
public class DonateApi {

	@Autowired
	private Authentication authentication;

	@CrossOrigin(origins = "http://localhost:4200/WhatsNew#/", allowedHeaders = "true", allowCredentials = "true")
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public ResponseEntity<Object> addDonate(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {

		/*
		if (!authentication.verifyCookies(request))
			return null;
*/
		Stripe.apiKey = "sk_test_51MiCeXIobgJo6HjBswbswTJcp36TCo3i5uXZKYCYrUUXFQhZqkmao7wqJLKF510gPefuQ6EVfsWgHEXM3m01ZeSc00opIsaRGf";

		ResourceHandlerRegistry registry = new ResourceHandlerRegistry(new GenericWebApplicationContext(),
				new MockServletContext());

		registry.addResourceHandler(Paths.get("public").toAbsolutePath().toString());

		try {
			String YOUR_DOMAIN = "http://localhost:8080/WhatsNew#/";
			SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
					.setSuccessUrl(YOUR_DOMAIN + "success.html").setCancelUrl(YOUR_DOMAIN + "cancel.html")
					.addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
							.setPrice("price_1Mot4NIobgJo6HjB9IbiIONV").build())
					.build();
			Session session = Session.create(params);
			
			String json = "{" + "\"value\"" + ":" +"\"" + session.getUrl().toString() + "\"" + "}";
			ResponseEntity<Object> res = new ResponseEntity<Object>(json,HttpStatus.OK);

			return res;
			
		} catch (Error | StripeException e) {
			throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);
		}
	}

	@RequestMapping(value = "/apiKey", method = RequestMethod.GET)
	public String getApiKey(HttpServletRequest request, HttpServletResponse response) {
		return Stripe.apiKey = "sk_test_51MiCeXIobgJo6HjBswbswTJcp36TCo3i5uXZKYCYrUUXFQhZqkmao7wqJLKF510gPefuQ6EVfsWgHEXM3m01ZeSc00opIsaRGf";

	}
}
