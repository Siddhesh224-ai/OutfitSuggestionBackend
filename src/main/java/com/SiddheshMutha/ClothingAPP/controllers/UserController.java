package com.SiddheshMutha.ClothingAPP.controllers;

import com.SiddheshMutha.ClothingAPP.dto.ModelRequestDTO;
import com.SiddheshMutha.ClothingAPP.dto.ProfileDTO;
import com.SiddheshMutha.ClothingAPP.dto.SignUpReq;
import com.SiddheshMutha.ClothingAPP.entities.UserEntity;
import com.SiddheshMutha.ClothingAPP.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PutMapping("/profile")
    public ResponseEntity<UserEntity> updateProfile(@RequestBody ProfileDTO profile) {
        return ResponseEntity.ok(userService.profile(profile));
    }

    @PostMapping("/model-request")
    public ResponseEntity<Map<String, Object>> modelRequest(@ModelAttribute ModelRequestDTO req) {
        return ResponseEntity.ok(userService.modelRequest(req));
    }

    @GetMapping("/model-url")
    public ResponseEntity<String> getModelUrl() {
        return ResponseEntity.ok(userService.getModelUrl());
    }

    @GetMapping("/measurements")
    public ResponseEntity<Map<String, Object>> getMeasurements() {
        return ResponseEntity.ok(userService.getMeasurements());
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }



    @GetMapping("/recommendations")
    public ResponseEntity<String> getRecommendations( @RequestBody String occasion) {
        return ResponseEntity.ok(userService.getRecommendations(occasion));
    }
}
