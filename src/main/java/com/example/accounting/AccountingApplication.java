package com.example.accounting;

import com.example.accounting.models.GoodsObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AccountingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingApplication.class, args);
    }

//    @Bean
//    GoodsObject goodsObject() {
//        return new GoodsObject();
//    }

}
