package com.gustavo.stockflowapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Startup {

    private static final Logger logger = LoggerFactory.getLogger(Startup.class);

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
        logger.info("StockFlow Started");
    }

}
