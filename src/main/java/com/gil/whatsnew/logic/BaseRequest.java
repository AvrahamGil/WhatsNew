package com.gil.whatsnew.logic;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.gil.whatsnew.bean.CaptchaResponse;
import com.gil.whatsnew.enums.RequestsUrl;


@Service
public class BaseRequest {

	private HttpClient client;
	private HttpGet httpGet;
	private HttpResponse response = null;
	private String getRequestUrl;

	public List<HttpResponse> getNewsArticles(String category, RequestsUrl requestUrl) throws IOException {
		List<HttpResponse> responses = new ArrayList<>();

		client = new DefaultHttpClient();

		try {
				if(!requestUrl.getApiKeyValue().equalsIgnoreCase("null")) {
					URIBuilder builder = new URIBuilder(requestUrl.getDomain());
					builder.setParameter(requestUrl.getApiKeyParameter(),requestUrl.getApiKeyValue())
							.setParameter(requestUrl.getLanguageParameter(),requestUrl.getLanguageValue());

					if(!category.isEmpty()) {
						builder.setParameter(requestUrl.getCategoryParameter(), category);
					}

					httpGet = new HttpGet(builder.build());
					httpGet.setHeader("Content-Type", "application/json");
					response = (HttpResponse) client.execute(httpGet);
					responses.add(response);
					response = null;
					client = new DefaultHttpClient();
				}


			return responses;

		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		} finally {
			if(response != null) {
				EntityUtils.consume(response.getEntity());
			}
			client = null;
			httpGet = null;
		}
        return null;
	}

	public boolean verifiedCaptcha(String secret, String gRecaptchaResponse)
			throws IOException {

		RestTemplate restTemplate = new RestTemplate();
		
		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("secret", secret);
		requestMap.add("response", gRecaptchaResponse);

		CaptchaResponse apiResponse = restTemplate.postForObject(RequestsUrl.Captcha.getDomain(), requestMap,
				CaptchaResponse.class);
		if (apiResponse == null) {
			return false;
		}

		return apiResponse.getSuccess();

	}

}
