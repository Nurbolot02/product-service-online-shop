package com.ntg.productserviceonlineshop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    //@Scope("thread")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
