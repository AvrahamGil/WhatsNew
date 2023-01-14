package com.gil.whatsnew.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class StringPaths {

	private static final String basePath = "\\whatsnew\\src\\main\\resources\\";
	
	private static final String utils = basePath + "utils.json";
	private static final String sites = basePath + "sql.json";
	private static final String domains = basePath + "domains.json";
	
	private static final String logs =  basePath + "logs\\";
	
	private static final String info = logs + "Info\\";
	private static final String exception = logs + "Exception\\";
	
	private static final String infoLogic = info + "InfoLogicLogFile.log";
	private static final String infoDao = info + "InfoDaoLogFile.log";
	
	private static final String exceptionLogicLogFile = exception + "ExceptionLogicLogFile.log";
	private static final String exceptionDaoLogFile = exception + "ExceptionDaoLogFile.log";
	
	
	public static String getPath(String type) {
		Set<String>paths = new HashSet<String>();
		paths.add(utils);
		paths.add(sites);
		paths.add(domains);
		
		for(String path : paths) {
			if(path.contains(type)) return path;
		}
		
		return "No such path";
	}
	
	public static String getLogs(String type) {
		Set<String>paths = new HashSet<String>();
		paths.add(infoLogic);
		paths.add(infoDao);
		paths.add(exceptionLogicLogFile);
		paths.add(exceptionDaoLogFile);

		for(String path : paths) {
			if(path.contains(type)) return path;
		}
		
		return "No such logs";
	}
}
