package com.gil.whatsnew.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class StringPaths {

	private static final String basePath =  StringPaths.class.getClassLoader().getResource("").getPath();;

	private static Resource utils = new ClassPathResource("data/utils.json");
	private static Resource sites = new ClassPathResource("data/sql.json");
	private static Resource domains = new ClassPathResource("data/domains.json");

	//private static final String utils = basePath + "data/utils.json";
	//private static final String sites = basePath + "data/sql.json";
	//private static final String domains = basePath + "data/domains.json";
	
	private static final String logs =  basePath + "logs\\";
	
	private static final String info = logs + "Info\\";
	private static final String exception = logs + "Exception\\";
	
	private static final String infoLogic = info + "InfoLogicLogFile.log";
	private static final String infoDao = info + "InfoDaoLogFile.log";
	
	private static final String exceptionLogicLogFile = exception + "ExceptionLogicLogFile.log";
	private static final String exceptionDaoLogFile = exception + "ExceptionDaoLogFile.log";
	private static final String errorLog = basePath + "errorLog.log";

	private static final String stockResponse = info + "stockResponse.txt";
	
	
	public static String getPath(String type) throws ApplicationException {
		Set<Resource>paths = new HashSet<Resource>();
		paths.add(utils);
		paths.add(sites);
		paths.add(domains);

		try {
			for(Resource path : paths) {
				String location = path.getFile().getPath().toString();
				if(location.contains(type)) return location;
			}
		}catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}

		
		return "No such path";
	}
	
	public static String getLogs(String type) {
		Set<String>paths = new HashSet<String>();
		paths.add(infoLogic);
		paths.add(infoDao);
		paths.add(exceptionLogicLogFile);
		paths.add(exceptionDaoLogFile);
		paths.add(errorLog);
		paths.add(stockResponse);

		for(String path : paths) {
			if(path.contains(type)) return path;
		}
		
		return "No such logs";
	}
}
