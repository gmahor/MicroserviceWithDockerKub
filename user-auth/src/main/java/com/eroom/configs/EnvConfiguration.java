package com.eroom.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Profile({"dev","uat"})
@ConfigurationProperties
@Component
public class EnvConfiguration {

    private String jwtSecret;

    private long accessTokenExpiryTime;

    private String backendServerDetail;
}
