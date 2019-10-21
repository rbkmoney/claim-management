package com.rbkmoney.cm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ClaimManagementApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaimManagementApplication.class, args);
    }

}
