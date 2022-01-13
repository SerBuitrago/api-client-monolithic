package com.pragma.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties("spring.datasource")
@NoArgsConstructor
public class PragmaConfig {

}
