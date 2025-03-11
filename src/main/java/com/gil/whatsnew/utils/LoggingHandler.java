package com.gil.whatsnew.utils;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class LoggingHandler {

	private static final Logger logger = Logger.getLogger(LoggingHandler.class.getName());
	
	private static FileHandler fileHadnler;
	
	public static void infoLogHandler(String filePath, String message) {
		try {
			fileHadnler = new FileHandler(filePath , true);
			logger.addHandler(fileHadnler);
			logger.setUseParentHandlers(false);
			logger.log(Level.SEVERE, message);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}finally {
			fileHadnler.close();
		}
	}
	
	public static void serverLogHandler(String filePath, String message) {
		try {
			fileHadnler = new FileHandler(filePath , true);
			logger.addHandler(fileHadnler);
			logger.setUseParentHandlers(false);
			logger.log(Level.SEVERE, message);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}finally {
			fileHadnler.close();
		}
	}
}
