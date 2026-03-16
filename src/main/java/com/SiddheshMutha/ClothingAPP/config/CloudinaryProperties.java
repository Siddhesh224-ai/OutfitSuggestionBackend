package com.SiddheshMutha.ClothingAPP.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("cloudinary")
public class CloudinaryProperties {

        private String cloudName;
        private String apiKey;
        private String apiSecret;

        public String getCloudName() {
            return cloudName;
        }

        public String getApiKey() {
            return apiKey;
        }

        public String getApiSecret() {
            return apiSecret;
        }

        public void setCloudName(String cloudName) {
            this.cloudName = cloudName;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public void setApiSecret(String apiSecret) {
            this.apiSecret = apiSecret;
        }
}
