package com.SiddheshMutha.ClothingAPP.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "images")
public class CloudinaryImageEntity {

    @JsonProperty("asset_id")
    private String assetId;

    @JsonProperty("public_id")
    private String publicId;

    @JsonProperty("version_id")
    private String versionId;

    private int version;

    private String signature;
    private String format;

    @JsonProperty("resource_type")
    private String resourceType;

    private String type;

    private String url;

    @JsonProperty("secure_url")
    private String secureUrl;

    @JsonProperty("asset_folder")
    private String assetFolder;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("original_filename")
    private String originalFilename;

    private int bytes;
    private int width;
    private int height;

    private String etag;
    private boolean placeholder;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("api_key")
    private String apiKey;

    private List<String> tags;
}
