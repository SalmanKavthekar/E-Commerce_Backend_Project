package com.salman.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadImpl implements FileUpload{

	public String uploadImage(String path, MultipartFile file) throws IOException {
		String originalFileName=file.getOriginalFilename();
		String randomImageName=UUID.randomUUID().toString();
		String randomImageNameWithExtension=randomImageName.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
		String fullPath=path+File.separator+randomImageNameWithExtension;
		
		File folderFile=new File(path);
		
		if(!folderFile.exists()) {
			folderFile.mkdirs();
		}
		try {
		Files.copy(file.getInputStream(), Paths.get(fullPath));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return randomImageNameWithExtension;
	}
}
