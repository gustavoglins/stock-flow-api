package com.stockflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockFlowApplication {

    private static final Logger logger = LoggerFactory.getLogger(StockFlowApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StockFlowApplication.class, args);
        logger.info("StockFlowAPI Started.");
    }
}
