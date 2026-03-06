package com.stadium_parika.services.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stadium_parika.services.StorageService;

@Service
public class StorageServiceImpl implements StorageService {
	
	private final String uploadDir = "uploads/vehicle-types/";

	@Override
	public String store(MultipartFile file) {
		
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		
		try {
			
			Path path = Paths.get(uploadDir + fileName);
			
			Files.createDirectories(path.getParent());
			
			Files.write(path, file.getBytes());
			
		} catch (Exception e) {
			throw new RuntimeException("Erreur upload fichier");
		}
		
		return fileName;
	}

}
