package com.gil.whatsnew.utils;

import java.io.FileReader;




import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;

@Service
public class JsonUtils {

	public static String readJsonFile(String type,String value,String path) throws ApplicationException {
		JSONParser  parser = new JSONParser ();
		String property = "";
		
		try(FileReader reader = new FileReader(path)) {
			Object obj = parser.parse(reader);
			
			org.json.simple.JSONArray values = (org.json.simple.JSONArray)obj;

			for(int i=0; i < values.size(); i++) {
				if(parserJsonObject((org.json.simple.JSONObject)values.get(i),type,value) != null) {
					property = parserJsonObject((org.json.simple.JSONObject) values.get(i),type,value);
					break;
				}
			}

	        return property;

		}catch(IOException | ParseException e) {
			throw new ApplicationException(ErrorType.Read_Json_Failed,ErrorType.Read_Json_Failed.getMessage(),false);
		}
	}
	
    private static String parserJsonObject(org.json.simple.JSONObject object,String type, String value) 
    {
    	org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) object.get(type);
        
        if(jsonObject == null) return null;

        String jsonValue = (String) jsonObject.get(value);
        
        return jsonValue;
         
    }
    
	public static JSONArray stringToJson(HttpResponse response) throws ParseException, IOException {
 
		HttpEntity entity = response.getEntity();

		String responseString = EntityUtils.toString(entity);

		JSONObject json = new JSONObject(responseString);
		
		JSONObject result = (JSONObject) json.get("response");
		JSONArray array = (JSONArray) result.get("docs");
		
		return array;
	}
}
