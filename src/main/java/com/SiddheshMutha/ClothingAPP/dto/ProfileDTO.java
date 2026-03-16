package com.SiddheshMutha.ClothingAPP.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileDTO {

    @NotBlank(message = "Cannot leave age blank")
    @Max(value = 80, message = "Please enter a valid age" )
    int age;


    String gender;

    String skincolor;

    @Max(value = 272, message = "Please enter a valid height")
    Double Height;



}
