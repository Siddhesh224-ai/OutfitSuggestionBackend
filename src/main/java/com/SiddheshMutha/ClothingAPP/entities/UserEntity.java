package com.SiddheshMutha.ClothingAPP.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {
    @Id
    private ObjectId userId;

    @Indexed(unique = true)
    @NonNull
    private String email;

    @JsonIgnore
    private String password;

    private String gender;

    private String skinColor;

    private Integer age;

    private List<CloudinaryImageEntity> imageEntities;

    private String modelUrl;

    private Double height;

    private Map<String, Object> measurements;

    String bodyShapeDescription;
}
