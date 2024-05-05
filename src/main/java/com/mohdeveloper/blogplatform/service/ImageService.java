package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.ImageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String saveImage(MultipartFile image) throws IOException;
    ImageData save(ImageData imageData);
}
