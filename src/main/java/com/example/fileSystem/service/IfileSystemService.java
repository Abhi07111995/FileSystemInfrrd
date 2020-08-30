package com.example.fileSystem.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IfileSystemService {
	
	public Resource downloadFile(String fileName);
	public boolean deleteFile(String fileName);
	public boolean copyFile(String fileName);

}
