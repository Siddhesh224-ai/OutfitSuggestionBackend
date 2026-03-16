package com.SiddheshMutha.ClothingAPP.services;

import com.SiddheshMutha.ClothingAPP.entities.UserEntity;
import com.SiddheshMutha.ClothingAPP.exception.UserDoesNotExistsException;
import com.SiddheshMutha.ClothingAPP.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(username);
        if(user!=null){
           return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles("USER")
                   .build();
        }
       throw new UserDoesNotExistsException("User not found with username: " + username);
    }
}
