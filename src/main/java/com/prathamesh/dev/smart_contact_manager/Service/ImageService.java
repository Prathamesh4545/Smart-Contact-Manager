package com.prathamesh.dev.smart_contact_manager.Service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadimage(MultipartFile contactImg);
}