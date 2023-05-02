package com.quotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableJpaRepositories
@EnableSwagger2
@EntityScan(basePackages = "com.quotation.entity")
public class QuotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotationApplication.class, args);
    }

}
