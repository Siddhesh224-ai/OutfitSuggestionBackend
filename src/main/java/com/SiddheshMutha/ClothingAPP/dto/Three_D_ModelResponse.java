package com.SiddheshMutha.ClothingAPP.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.databind.JsonNode;


import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Three_D_ModelResponse {
    @JsonProperty("model_url")
    private String modelUrl;

    @JsonProperty("measurements")
    private JsonNode measurements;
}
