package com.SiddheshMutha.ClothingAPP.repositories;

import com.SiddheshMutha.ClothingAPP.entities.CloudinaryImageEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CloudinaryRepo extends MongoRepository<CloudinaryImageEntity, String> {
}