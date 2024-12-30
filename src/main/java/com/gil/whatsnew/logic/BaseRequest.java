package com.gil.whatsnew.logic;


import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.gil.whatsnew.bean.CaptchaResponse;
import com.gil.whatsnew.enums.Requests;


@Service
public class BaseRequest {

	private HttpClient client;
	private HttpGet httpGet;
	private HttpResponse response;
	private String getRequestUrl;
	
	public HttpResponse getNewsArticles(String type) {
		client = new DefaultHttpClient();

		try {
			URIBuilder builder = new URIBuilder(Requests.CurrentsApi.getValue());
			builder.setParameter(Requests.CurrentsKeyParameter.getValue(),Requests.CurrentsAPIKeyValue.getValue())
					.setParameter(Requests.LanguageParameter.getValue(),Requests.LanguageValue.getValue())
					.setParameter(Requests.CategoryParameter.getValue(),type);

			httpGet = new HttpGet(builder.build());
			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setHeader(Requests.CurrentsKeyParameter.getValue(), Requests.CurrentsAPIKeyValue.getValue());
			response = (HttpResponse) client.execute(httpGet);

			return response;

		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
        return null;
	}

	public boolean verifiedCaptcha(String secret, String gRecaptchaResponse)
			throws IOException {

		RestTemplate restTemplate = new RestTemplate();
		
		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("secret", secret);
		requestMap.add("response", gRecaptchaResponse);

		CaptchaResponse apiResponse = restTemplate.postForObject(Requests.Captcha.getValue(), requestMap,
				CaptchaResponse.class);
		if (apiResponse == null) {
			return false;
		}

		return apiResponse.getSuccess();

	}

}
