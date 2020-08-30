package com.example.fileSystem.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fileSystem.bean.Response;
import com.example.fileSystem.service.IAuthenticationService;
import com.example.fileSystem.service.IfileSystemService;



@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileSystemController {

	@Autowired
	IAuthenticationService iAuthenticationService;
	@Autowired
	IfileSystemService ifileSystemService; 
	public static final Logger logger = LoggerFactory.getLogger(FileSystemController.class);
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity downloadFile(@PathVariable String fileName) {

		Resource resource = ifileSystemService.downloadFile(fileName);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("text/html"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" 
						+ resource.getFilename() + "\"")
				.body(resource);

	}

	@GetMapping("/delete/{fileName:.+}")
	public void deleteFile(@PathVariable String fileName) throws IOException {

		boolean isDeleted=ifileSystemService.deleteFile(fileName);
		Response.builder().result(null).success(isDeleted);			

	}

	@PostMapping("/copy/{fileName:.+}")
	public void copyFile(@PathVariable String fileName) throws IOException {

		boolean isCopied=ifileSystemService.copyFile(fileName);
		Response.builder().result(null).success(isCopied);	

	}

}
