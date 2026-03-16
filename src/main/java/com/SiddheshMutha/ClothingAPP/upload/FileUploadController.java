package com.SiddheshMutha.ClothingAPP.upload;



import com.SiddheshMutha.ClothingAPP.dto.Three_D_ModelResponse;
import com.SiddheshMutha.ClothingAPP.entities.CloudinaryImageEntity;
import com.SiddheshMutha.ClothingAPP.services.CloudinaryService;
import com.SiddheshMutha.ClothingAPP.services.MicroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.springframework.web.servlet.function.ServerResponse.status;


@RestController
@RequestMapping("/files")
public class FileUploadController {



    @Autowired
    private MicroService microService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam MultipartFile file1 ,
            @RequestParam MultipartFile file2,
            @RequestParam Double height){

        if(height== null || (height == 0.0) || (height.isNaN())){
            return  ResponseEntity.status(400).body("Please enter a valid height!");
        }
        if(file1.isEmpty() || file2.isEmpty()){
            return  ResponseEntity.status(400).body("Please upload both the files!");
        }

        Map<String, Object> response1 = cloudinaryService.upload(file1);
        Map<String, Object> response2 = cloudinaryService.upload(file2);

        ObjectMapper mapper = new ObjectMapper();

        CloudinaryImageEntity frontImage = mapper.convertValue(response1, CloudinaryImageEntity.class);

        CloudinaryImageEntity sideImage = mapper.convertValue(response2, CloudinaryImageEntity.class);

        try{

            String frontImageUrl = frontImage.getSecureUrl();
            String sideImageUrl = sideImage.getSecureUrl();

            try{
                Three_D_ModelResponse resul = microService.get3dModel(frontImageUrl,sideImageUrl,height);
                return new ResponseEntity<>(resul ,HttpStatus.CREATED);
            } catch (Exception e) {
                String message = "Some Error Occured!" + e ;
                return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload: " + e.getMessage());
        }


    }

}
