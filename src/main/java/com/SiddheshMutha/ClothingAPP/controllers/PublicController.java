package com.SiddheshMutha.ClothingAPP.controllers;

import com.SiddheshMutha.ClothingAPP.JwtUtil;
import com.SiddheshMutha.ClothingAPP.dto.LoginDTO;
import com.SiddheshMutha.ClothingAPP.dto.SignUpReq;
import com.SiddheshMutha.ClothingAPP.entities.UserEntity;
import com.SiddheshMutha.ClothingAPP.services.UserDetailsServiceImpl;
import com.SiddheshMutha.ClothingAPP.services.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody SignUpReq request) {

        return new ResponseEntity<>(userService.register(request), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO user ){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String jwt = jwtUtil.generateToken(user.getEmail());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect username or password" +e , HttpStatus.BAD_REQUEST);
        }
    }





}
