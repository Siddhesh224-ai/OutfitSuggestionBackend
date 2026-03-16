package com.SiddheshMutha.ClothingAPP.services;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    Cloudinary cloudinary;

    @SuppressWarnings("unchecked")
    public Map<String, Object> upload(MultipartFile file){
        try {
            Map data = cloudinary.uploader().upload(file.getBytes(),new HashMap<>());
            return (Map<String, Object>) data;
        } catch (Exception e) {
            throw new RuntimeException("Image upload to cloudinary failed!" + e);
        }
    }
}
