package com.auth.authentication.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class isValidPhotoUtil {
    public static boolean isValidPhoto(MultipartFile file) {
        // Check if the content type starts with "image/"
        return Objects.requireNonNull(file.getContentType()).startsWith("image/");
    }


}
