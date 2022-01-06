package com.pragma;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties("spring.datasource")
@NoArgsConstructor
@Getter
@Setter
public class PragmaConfig {

}