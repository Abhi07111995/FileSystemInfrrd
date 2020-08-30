package com.example.fileSystem.service.Implementation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.example.fileSystem.exception.FileStorageException;
import com.example.fileSystem.exception.LocationBasedException;
import com.example.fileSystem.service.IfileSystemService;

@Service
public class FileSystemService implements IfileSystemService {

	private static final String SOURCE_FILE_PATH = "/Users/lenskart/fileServer/";
	private static final String DESTINATION_FILE_PATH = "/Users/lenskart/fileDest/";
	@Override
	public Resource downloadFile(String fileName) {
		
		Path path = Paths.get(SOURCE_FILE_PATH + fileName);
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
			return resource;
		} catch (MalformedURLException e) {
			throw new LocationBasedException("url may not be parsed or, "
					+ "without legal protocol ");
			}
	}

	@Override
	public boolean deleteFile(String fileName) {

		Path fileToDeletePath = Paths.get(SOURCE_FILE_PATH+fileName);
		try {
			Files.delete(fileToDeletePath);
		} catch (IOException e) {
			throw new FileStorageException("file may not be present",e); 
			}
		return true;

	}

	@Override
	public boolean copyFile(String fileName) {

		File copiedfile=null;
		Resource resource = null;
		try {
			Path path = Paths.get(SOURCE_FILE_PATH + fileName);
			resource = new UrlResource(path.toUri());
			copiedfile = new File(DESTINATION_FILE_PATH+fileName);
			FileSystemUtils.copyRecursively(resource.getFile(),
					copiedfile);
		} catch(IOException e) {
			throw new FileStorageException("file may not be present",e); 
			}
		return true;

	}

}
