package com.ntg.productserviceonlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceOnlineShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceOnlineShopApplication.class, args);
    }

}
