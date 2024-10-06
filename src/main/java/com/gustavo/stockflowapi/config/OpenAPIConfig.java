package com.gustavo.stockflowapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Stock Flow API")
                        .version("v1")
                        .description("API for Stock Flow application")
                        .termsOfService("https://github.com/gustavoglins/stock-flow-api/blob/master/License%20Agreement%20for%20StockFlowAPI")
                        .license(new License().name("StockFlow 1.0").url("https://github.com/gustavoglins/stock-flow-api"))
                );
    }
}
