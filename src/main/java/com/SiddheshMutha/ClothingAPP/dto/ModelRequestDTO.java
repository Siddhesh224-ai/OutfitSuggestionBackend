package com.SiddheshMutha.ClothingAPP.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ModelRequestDTO {
    @NotNull
    MultipartFile frontImage;
    @NonNull
    MultipartFile sideImage;
}
