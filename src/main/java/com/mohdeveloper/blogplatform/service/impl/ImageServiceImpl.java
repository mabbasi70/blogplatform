package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.ImageData;
import com.mohdeveloper.blogplatform.repository.ImageDataRepository;
import com.mohdeveloper.blogplatform.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    private ImageDataRepository imageDataRepository;

    public ImageServiceImpl(ImageDataRepository imageDataRepository){
        this.imageDataRepository = imageDataRepository;
    }
    @Value("${image.dir}")
    private String imagesDir;
    @Override
    @Transactional
    public String saveImage(MultipartFile image) throws IOException {
        if(image.isEmpty()){
            throw new IOException("There is no image to upload!");
        }
        Path dirLoc = Paths.get(imagesDir);
        if(!Files.exists(dirLoc)){Files.createDirectories(dirLoc);}
        String originalFileName = image.getOriginalFilename();

        String fileExtensionWithDot = "";
        if (originalFileName != null) {
            fileExtensionWithDot = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String newImageName = "_IMG" + System.currentTimeMillis() + fileExtensionWithDot;
        Path finalPathAndName = dirLoc.resolve(newImageName);
        Files.copy(image.getInputStream(), finalPathAndName);
//        String relativePath = "/post-images/"+ newImageName;
//        return finalPathAndName.toString();
        return "/blog-images/" + newImageName;
    }

    @Override
    @Transactional
    public ImageData save(ImageData imageData) {
        return imageDataRepository.save(imageData);
    }

}
