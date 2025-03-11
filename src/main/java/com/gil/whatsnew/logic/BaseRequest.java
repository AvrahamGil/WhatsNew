package com.gil.whatsnew.logic;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
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

	public HttpResponse verifyImageUrl(String requestUrl) throws IOException, URISyntaxException {
		CloseableHttpClient client = HttpClients.createDefault();

		if(requestUrl == null) return null;

		if(!requestUrl.startsWith("http")) return null;

		String correctUrl = encodeUrl(requestUrl);

		try {
			if(correctUrl == null) return null;
			HttpGet httpGet = new HttpGet(correctUrl);

            return client.execute(httpGet);

		} catch ( IOException e) {
			e.printStackTrace();
		} finally {
			if(response != null) {
				EntityUtils.consume(response.getEntity());
			}
		}
		return null;
	}

	private String encodeUrl(String url) throws UnsupportedEncodingException, URISyntaxException {
		int protocolEndIndex = url.indexOf("://");
		if (protocolEndIndex == -1) return null;

		String protocol = url.substring(0, protocolEndIndex + 3);
		String restOfUrl = url.substring(protocolEndIndex + 3);

		URI uri = new URI(url);

		if((uri.getScheme() == null && (!uri.getScheme().startsWith("http") && !uri.getScheme().startsWith("https")) &&
				uri.getHost() == null)) {
			return null;
		}

		String encodedRest = URLEncoder.encode(restOfUrl, StandardCharsets.UTF_8.toString())
				.replaceAll("\\+", "%20")
				.replaceAll("%2F", "/");

		return protocol + encodedRest;
	}

}
