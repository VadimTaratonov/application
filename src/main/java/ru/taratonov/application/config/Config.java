package ru.taratonov.application.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.taratonov.application.util.ApplicationResponseErrorHandler;

@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder
                .errorHandler(new ApplicationResponseErrorHandler())
                .build();
    }
}
