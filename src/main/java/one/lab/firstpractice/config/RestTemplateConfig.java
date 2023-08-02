package one.lab.firstpractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    public static final String BASE_URL = "http://localhost:8080/api/v1/";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
