package dev.victor.paulo.startwarsAPI.external.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExternalRestAPIConfig {

    @Value("${external.api.path}")
    private String EXTERNAL_API_BASE_URI;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .rootUri(EXTERNAL_API_BASE_URI)
                .build();
    }
}
