package com.gil.whatsnew.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

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
	
	public static void writeIntoJsonFile(String type, Object value, String path) throws ApplicationException {
		Gson gson = new GsonBuilder().setDateFormat(LocalDate.now().toString()).create();
		boolean isValidLog = true;
		
		try {
			if(type.equals("errorLog") || type.equals("infoLog")) {
				if(value == null && path.isEmpty()) isValidLog = false;
				if(value.toString().contains("!@#$%^&*()") || path.contains("!@#$%^&*()")) isValidLog = false;	
					
				if(!isValidLog) throw new ApplicationException(ErrorType.Write_Json_Failed,ErrorType.Write_Json_Failed.getMessage(),false);
				
				gson.toJson(value,new FileWriter(path));
			}
		} catch (JsonIOException | IOException e) {
			throw new ApplicationException(ErrorType.Write_Json_Failed,ErrorType.Write_Json_Failed.getMessage(),false);
		}
	}
	
    private static String parserJsonObject(org.json.simple.JSONObject object,String type, String value) 
    {
    	org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) object.get(type);
        
        if(jsonObject == null) return null;

        String jsonValue = (String) jsonObject.get(value);
        
        return jsonValue;
         
    }

	public static ResponseEntity<String> readFromAFile(String path) throws IOException {
		ResponseEntity<String>res;
		if(Files.exists(Paths.get(path))) {
			String content = Files.readString(Paths.get(path));
			res= new ResponseEntity<>(content, HttpStatus.OK);

			return res;
		}
		return null;
	}

	public static void writeToAFile(String path, ResponseEntity<String> response) throws IOException {
		try(FileWriter writer = new FileWriter(path)) {
			writer.write((response.getBody().toString()));
			System.out.println("Response has been written to the file.");
		}catch (IOException e) {
			e.printStackTrace();
		}
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
