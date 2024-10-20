package com.stockflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("StockFlow API")
                        .version("v1")
                        .description("StockFlowAPI - Streamlined Inventory Management Solution")
                        .termsOfService("https://github.com/gustavoglins/stock-flow-api/blob/master/LICENSE.md")
                        .license(
                                new License()
                                        .name("StockFlow 1.0")
                                        .url("https://github.com/gustavoglins/stock-flow-api")
                        )
                );
    }
}
