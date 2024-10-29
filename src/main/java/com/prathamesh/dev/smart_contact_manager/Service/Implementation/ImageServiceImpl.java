package com.prathamesh.dev.smart_contact_manager.Service.Implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prathamesh.dev.smart_contact_manager.Service.ImageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final String UPLOAD_DIR = "src/main/resources/static/upload/directory"; // Update this path

    @Override
    public String uploadimage(MultipartFile contactImg) {
        if (contactImg.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            // Create the upload directory if it doesn't exist
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Generate a unique filename
            String originalFilename = contactImg.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            // Define the file path
            Path filePath = Paths.get(UPLOAD_DIR, newFileName);

            // Save the file to the specified path
            Files.copy(contactImg.getInputStream(), filePath);

            // Return the path or URL of the uploaded image
            return newFileName; // You can return a full path or URL if needed

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        }
    }
}