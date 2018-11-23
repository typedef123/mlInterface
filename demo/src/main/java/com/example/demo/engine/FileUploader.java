package com.example.demo.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;


public class FileUploader {
	private static String UPLOADED_FOLDER = "C:\\upload\\";
	
	
	public static MultipartFile uploadFile(MultipartFile file) {
		if (file.isEmpty()) {
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            
            System.out.println("You successfully uploaded '" + file.getOriginalFilename() + "'");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
	}
}
