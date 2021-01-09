package com.gil.whatsnew.logic;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gil.whatsnew.enums.BusinessType;
import com.gil.whatsnew.enums.NewsType;
import com.gil.whatsnew.enums.Requests;
import com.gil.whatsnew.enums.SportType;
import com.gil.whatsnew.enums.TechnologyType;


@Service
public class BaseRequest {

	private HttpClient client;
	private HttpGet httpGet;
	private HttpResponse response;
	private String getRequestUrl;


	public HttpResponse getNewsArticleByType(String type) throws ClientProtocolException, IOException {
		client = new DefaultHttpClient();
		getRequestUrl = "request-url";
		httpGet = new HttpGet(getRequestUrl);
		httpGet.addHeader(Requests.RequestApi.getUrl(),Requests.RequestApi.getUrl());
		httpGet.addHeader(Requests.RequestApi.getUrl(),Requests.RequestApi.getUrl());
		response = client.execute(httpGet);
		
		return response; 
		
	}
	
	public HttpResponse getNewYorkBusinessArticleByType(String category) throws ClientProtocolException, IOException {
		client = new DefaultHttpClient();
		getRequestUrl = "request-url";
		httpGet = new HttpGet(getRequestUrl);
		response = client.execute(httpGet);
		
		return response; 
		
	}
	
	public HttpResponse getOtherNewsByType(String type) throws ClientProtocolException, IOException {
		client = new DefaultHttpClient();
		getRequestUrl = "request-url";
		httpGet = new HttpGet(getRequestUrl);
		httpGet.addHeader(Requests.RequestApi.getUrl(),Requests.RequestApi.getUrl());
		httpGet.addHeader(Requests.RequestApi.getUrl(),Requests.RequestApi.getUrl());
		response = client.execute(httpGet);
		
		return response; 
		
	}
}
