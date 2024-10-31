package com.prathamesh.dev.smart_contact_manager.Service.Implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.prathamesh.dev.smart_contact_manager.Service.ImageService;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String UPLOAD_DIR = "src/main/resources/static/upload/directory";

    @Override
    public String uploadimage(MultipartFile contactImg) {
        if (contactImg.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            // Create the upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique filename
            String originalFilename = contactImg.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            // Define the file path
            Path filePath = uploadPath.resolve(newFileName);

            // Save the file to the specified path using buffered streams
            try (BufferedInputStream bis = new BufferedInputStream(contactImg.getInputStream())) {
                Files.copy(bis, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Return the new filename
            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage(), e);
        }
    }
}
