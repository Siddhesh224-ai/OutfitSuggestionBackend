package com.SiddheshMutha.ClothingAPP.services;

//import ch.qos.logback.core.net.server.Client;
import com.SiddheshMutha.ClothingAPP.dto.Three_D_ModelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerRequest;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequestMapping("/test")
public class MicroService {
    String finalAPI = "http://127.0.0.1:8000/process_measurements" ;


    @Value("${GEMINI_API_KEY}")
    String googleApiKey;

    @Autowired
    private RestTemplate restTemplate;


    public Three_D_ModelResponse get3dModel(String frontImageUrl,
                                            String sideImageUrl,
                                            Double heightCm) throws Exception{

        HashMap<String,Object> body = new HashMap<>();
        body.put("front_image_url",frontImageUrl);
        body.put("side_image_url",sideImageUrl);
        body.put("height_cm", heightCm);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<HashMap<String,Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Three_D_ModelResponse> response = restTemplate.exchange(finalAPI,HttpMethod.POST,requestEntity,Three_D_ModelResponse.class);

        //System.out.println("RAW FASTAPI RESPONSE:");
        //System.out.println(Objects.requireNonNull(response.getBody()).getModelUrl());
//        System.out.println(response.getBody());git,.a.add add .git commit " -m "newer versio n" "--git push

        return response.getBody();

    }



    public String getBodyShape(String context) {

        Client client = Client.builder()
                .apiKey(googleApiKey)
                .build();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        context,
                        null
                );

        return response.text();
    }

}

