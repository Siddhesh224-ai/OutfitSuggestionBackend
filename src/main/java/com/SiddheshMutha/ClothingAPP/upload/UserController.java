package com.SiddheshMutha.ClothingAPP.upload;

import com.SiddheshMutha.ClothingAPP.dto.SignUpReq;
import com.SiddheshMutha.ClothingAPP.entities.UserEntity;
import com.SiddheshMutha.ClothingAPP.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@Valid @RequestBody SignUpReq req){
        userService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

//    @PostMapping("/user/profileUpdate")





}
