package com.SiddheshMutha.ClothingAPP.repositories;


import com.SiddheshMutha.ClothingAPP.entities.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<UserEntity, String> {
    boolean existsByEmail(String email);


    public UserEntity findByEmail(String email);
}
