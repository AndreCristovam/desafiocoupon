package com.andrecristovam.desafiocoupon.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerApiConfig {

	 @Bean
	    public OpenAPI couponApi() {
	        return new OpenAPI()
	            .info(new Info()
	                .title("Coupon API")
	                .description("API para gerenciamento de cupons — Desafio Técnico")
	                .version("1.0.0"));
	    }
}
