package com.gil.whatsnew.api;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.UserLogic;
import com.gil.whatsnew.utils.Authentication;

@RestController
@RequestMapping("/upload")
public class FileUploadApi {

	@Autowired
	private UserLogic userLogic;

	@Autowired
	private Authentication authentication;
	
	@RequestMapping(value="/files/{filename:.+}" , method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = userLogic.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@RequestMapping(value="/" , method = RequestMethod.POST)
	public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("email") String email,@RequestParam("X-TOKEN")String token) throws ApplicationException {

		if(file == null) return null;
		
		
		try {
			String[] details = authentication.verifyJwtToken(token);
			if(details != null) {
				User user = userLogic.store(file,details[0],details[1]);
				if(user != null) {
					ResponseEntity<Object> res = new ResponseEntity<Object>(user,HttpStatus.OK);
					return res;
				}
			}
			

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	
	}
}
