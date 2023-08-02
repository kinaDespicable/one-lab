package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.config.jwt.JwtService;
import one.lab.firstpractice.exception.exceptions.ResourceNotFoundException;
import one.lab.firstpractice.model.dto.response.AggregatedDTO;
import one.lab.firstpractice.model.dto.response.auth.LoginResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.service.ComputableFutureService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static one.lab.firstpractice.config.RestTemplateConfig.BASE_URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComputableFutureServiceImpl implements ComputableFutureService {

    private static final String LOGIN_ENDPOINT = "auth/login";
    private static final String BEARER = "Bearer ";

    private static String JWT_TOKEN;

    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Override
    public CompletableFuture<AggregatedDTO> makeCall() {
        CompletableFuture<UserResponse> response = CompletableFuture.supplyAsync(this::callGetUserByIdEndpoint);
        CompletableFuture<Page<UserResponse>> response2 = CompletableFuture.supplyAsync(this::callGetAuthorsEndpoint);
        CompletableFuture<Page<UserResponse>> response3 = CompletableFuture.supplyAsync(this::callGetAdminsEndpoint);

        AggregatedDTO aggregatedDTO = new AggregatedDTO();

        return CompletableFuture.allOf(response, response2, response3)
                .thenApplyAsync(v -> {
                    aggregatedDTO.setUserByIdResponse(response.join());
                    aggregatedDTO.setAuthorsIdResponse(response2.join());
                    aggregatedDTO.setAdminsResponse(response3.join());
                    return aggregatedDTO;
                });
    }

    private UserResponse callGetUserByIdEndpoint() {
        checkToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, BEARER + JWT_TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<UserResponse> response = restTemplate.exchange(
                BASE_URL + "/user/1",
                HttpMethod.GET,
                requestEntity,
                UserResponse.class
        );
        return response.getBody();
    }

    private Page<UserResponse> callGetAuthorsEndpoint() {
        checkToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, BEARER + JWT_TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<Page<UserResponse>> responseType =
                new ParameterizedTypeReference<Page<UserResponse>>() {};

        ResponseEntity<Page<UserResponse>> response = restTemplate.exchange(
                BASE_URL + "/user/fetch-authors",
                HttpMethod.GET,
                requestEntity,
                responseType
        );
        return response.getBody();
    }

    private Page<UserResponse> callGetAdminsEndpoint() {
        checkToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, BEARER + JWT_TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<Page<UserResponse>> responseType =
                new ParameterizedTypeReference<Page<UserResponse>>() {};

        ResponseEntity<Page<UserResponse>> response = restTemplate.exchange(
                BASE_URL + "/user/fetch-admins",
                HttpMethod.GET,
                requestEntity,
                responseType
        );
        return response.getBody();
    }


    private void checkToken() {
        if (Objects.isNull(JWT_TOKEN) || jwtService.isTokenExpired(JWT_TOKEN)) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("username", "adminUser");
            requestBody.put("password", "password");

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<LoginResponse> response = restTemplate.exchange(BASE_URL + LOGIN_ENDPOINT,
                    HttpMethod.POST,
                    requestEntity,
                    LoginResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                LoginResponse responseBody = response.getBody();

                if (Objects.nonNull(responseBody)) {
                    JWT_TOKEN = responseBody.getJwtToken();
                }
            } else {
                log.error("Request to {}{} produced status: {}", BASE_URL, LOGIN_ENDPOINT, response.getStatusCode());
                throw new ResourceNotFoundException("Response error");
            }
        }
    }

}
