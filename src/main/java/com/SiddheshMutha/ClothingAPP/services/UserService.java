package com.SiddheshMutha.ClothingAPP.services;

import com.SiddheshMutha.ClothingAPP.dto.ModelRequestDTO;
import com.SiddheshMutha.ClothingAPP.dto.ProfileDTO;
import com.SiddheshMutha.ClothingAPP.dto.SignUpReq;
import com.SiddheshMutha.ClothingAPP.dto.Three_D_ModelResponse;
import com.SiddheshMutha.ClothingAPP.entities.CloudinaryImageEntity;
import com.SiddheshMutha.ClothingAPP.entities.UserEntity;
import com.SiddheshMutha.ClothingAPP.exception.EmailAlreadyExistsException;
import com.SiddheshMutha.ClothingAPP.exception.ModelNotExistsException;
import com.SiddheshMutha.ClothingAPP.exception.UserDoesNotExistsException;
import com.SiddheshMutha.ClothingAPP.repositories.CloudinaryRepo;
import com.SiddheshMutha.ClothingAPP.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MicroService microService;

    @Autowired
    private CloudinaryRepo cloudinaryRepo;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private String getCurrentEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() ){
            throw new RuntimeException("Not authenticated");
        }
        return authentication.getName();
    }


    @org.springframework.transaction.annotation.Transactional
    public UserEntity register(SignUpReq request){
        if(userRepo.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode((request.getPassword())));
        return userRepo.save(user);
    }

    @org.springframework.transaction.annotation.Transactional
    public UserEntity profile(ProfileDTO profile) {
        String email = getCurrentEmail();
        UserEntity user = userRepo.findByEmail(email);
        user.setGender(profile.getGender());
        user.setSkinColor(profile.getSkincolor());
        user.setAge(profile.getAge());
        user.setHeight(profile.getHeight());
        return userRepo.save(user);
    }

    @org.springframework.transaction.annotation.Transactional
    public Map<String, Object> modelRequest(ModelRequestDTO req){
        String email = getCurrentEmail();
        UserEntity user = userRepo.findByEmail(email);
        if (user.getHeight() == null || user.getHeight().isNaN() || user.getHeight() == 0){
            throw new RuntimeException("Please enter a valid height to generate your 3D model!");
        }
        MultipartFile file1 = req.getFrontImage();
        MultipartFile file2 = req.getSideImage();
        if (file1 == null || file2 == null || file1.isEmpty() || file2.isEmpty()) {
            throw new RuntimeException("Please select valid image files");
        }

        Map<String, Object> response1 = cloudinaryService.upload(file1);
        Map<String, Object> response2 = cloudinaryService.upload(file2);

        CloudinaryImageEntity frontImage = mapper.convertValue(response1, CloudinaryImageEntity.class);

        CloudinaryImageEntity sideImage = mapper.convertValue(response2, CloudinaryImageEntity.class);

        cloudinaryRepo.save(frontImage);
        cloudinaryRepo.save(sideImage);
        List<CloudinaryImageEntity> images = new ArrayList<>();
        images.add(frontImage);
        images.add(sideImage);

        user.setImageEntities(images);


        String frontImageUrl = frontImage.getSecureUrl();
        String sideImageUrl = sideImage.getSecureUrl();
        Double userHeight = user.getHeight();
        if (user.getHeight() == null) {
            throw new RuntimeException("User height not found. Complete profile first.");
        }

        try{
            Three_D_ModelResponse result = microService.get3dModel(frontImageUrl,sideImageUrl,userHeight);
            String modelUrl = result.getModelUrl();
            user.setModelUrl(modelUrl);
            Map<String, Object> measurementMap =
                    mapper.convertValue(result.getMeasurements(), new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
            user.setMeasurements(measurementMap);
            userRepo.save(user);
            return user.getMeasurements();
        } catch (Exception e) {
            throw new RuntimeException("Error generating 3D model: " + e.getMessage(), e);
        }

    }

    public String getModelUrl(){
        String email = getCurrentEmail();
        UserEntity user = userRepo.findByEmail(email);
        String url = user.getModelUrl();
        if( url == null || url.isEmpty()){
            String message = "Please make a model first";
            throw new ModelNotExistsException(message);
        }
        return url;
    }

    public Map<String,Object> getMeasurements(){
        String email = getCurrentEmail();
        UserEntity user = userRepo.findByEmail(email);
        Map<String,Object> mea = user.getMeasurements();
        if( mea == null || mea.isEmpty()){
            String message = "Please make a model first so that we can calliberate your measurements";
            throw new ModelNotExistsException(message);
        }
        return mea;
    }

    public Map<String,Object> getProfile(){
        String email = getCurrentEmail();
        UserEntity user = userRepo.findByEmail(email);
        Map<String,Object> map = new HashMap<>();
        map.put("SkinColor",user.getSkinColor());
        map.put("Height",user.getHeight());
        map.put("Gender",user.getGender());
        List<CloudinaryImageEntity> images = user.getImageEntities();
        if (images == null || images.size() < 2) {
            map.put("FrontImage", null);
            map.put("SideImage", null);
        } else {
            map.put("FrontImage", images.get(0).getSecureUrl());
            map.put("SideImage", images.get(1).getSecureUrl());
        }
        return map;
    }


    @org.springframework.transaction.annotation.Transactional
    public String getBodyShapeDescription(){
        String email = getCurrentEmail();
        UserEntity user = userRepo.findByEmail(email);

        Map<String,Object> mea = user.getMeasurements();
        if( mea == null || mea.isEmpty()){
            String message = "Please make a model first so that we can calliberate your measurements";
            throw new ModelNotExistsException(message);
        }

        String context =
                """
                You are a professional men's fashion stylist and body-shape analyst.
                
                User details:
                - Age: %s years
                - Gender: Male
                - Height: %s cm
                
                Body measurements (in inches):
                %s
                
                Your task:
                1. Identify the user's primary body shape (e.g., rectangle, triangle, inverted triangle, oval).
                2. Explain WHY this body shape was identified using the measurements.
                3. Highlight 2–3 physical strengths.
                4. Highlight 2–3 areas that need visual balancing while dressing.
                
                Rules:
                - Keep the explanation simple and user-friendly.
                - Do NOT mention exact measurement numbers in the explanation.
                - Do NOT give outfit suggestions yet.
                
                Output format:
                Body Shape:
                Explanation:
                Strengths:
                Areas to Balance:
                """.formatted(
                        user.getAge(),
                        user.getHeight(),
                        mea.toString()
                );

        String bodyDescription = microService.getBodyShape(context);

        user.setBodyShapeDescription(bodyDescription);

        userRepo.save(user);

        return bodyDescription;

    }

    public String getRecommendations(String occasion){
        String email = getCurrentEmail();
        UserEntity user = userRepo.findByEmail(email);

        Map<String,Object> mea = user.getMeasurements();
        if( mea == null || mea.isEmpty()){
            String message = "Please make a model first so that we can calliberate your measurements";
            throw new ModelNotExistsException(message);
        }

        String bD = user.getBodyShapeDescription();
        String uA = user.getAge().toString();
        String uH = user.getHeight().toString();

        if (uA == null || uA.isBlank()) {
            uA = "Age not entered ";
        }

        String context =
                """
                You are an expert men's fashion stylist specializing in body-type based styling.
                
                User profile:
                - Age: %s years
                - Gender: Male
                - Height: %s cm
                - Skin tone: %s
                - Body shape analysis:
                %s
                
                Occasion:
                %s
                
                Your task:
                1. Suggest a complete outfit suitable for the occasion.
                2. Explain why each clothing choice works for the user's body shape.
                3. Suggest suitable colors based on skin tone.
                4. Provide grooming and accessory tips.
                
                Constraints:
                - Assume Indian climate and fashion sensibilities.
                - Focus on modern, stylish, and practical outfits.
                - Avoid brand names.
                - Avoid mentioning measurements.
                - Avoid overly generic advice.
                
                Output format:
                Outfit Recommendation:
                - Top:
                - Bottom:
                - Footwear:
                
                Color Suggestions:
                Styling Tips:
                Grooming & Accessories:
                """.formatted(
                        user.getAge(),
                        user.getHeight(),
                        user.getSkinColor(),
                        user.getBodyShapeDescription(),
                        occasion
                );
        String bodyDescription = microService.getBodyShape(context);

        return bodyDescription;

    }






}
