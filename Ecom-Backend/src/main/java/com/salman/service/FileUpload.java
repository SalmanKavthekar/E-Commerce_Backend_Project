package com.salman.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {

	public String uploadImage(String path, MultipartFile file)throws IOException;
}
