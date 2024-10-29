package com.prathamesh.dev.smart_contact_manager.Validators;


import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
    // private static final int MAX_WIDTH = 1024;
    // private static final int MAX_HEIGHT = 1024;

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
            return false;
        }

        // file size
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File should be less than 2MB").addConstraintViolation();
            return false;
        }

        // Resolution
        // try {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        //     if (bufferedImage.getWidth() > MAX_WIDTH || bufferedImage.getHeight() > MAX_HEIGHT) {
        //         context.disableDefaultConstraintViolation();
        //         context.buildConstraintViolationWithTemplate("Image resolution should be less than " + MAX_WIDTH + "x" + MAX_HEIGHT);
        //         return false;
        //     }
        // } catch (IOException e) {
        //     context.disableDefaultConstraintViolation();
        //     context.buildConstraintViolationWithTemplate("Invalid image file");
        //     return false;
        // }

        return true;
    }

}