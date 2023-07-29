package one.lab.firstpractice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import one.lab.firstpractice.model.dto.request.LoginRequest;
import one.lab.firstpractice.model.dto.request.RegistrationRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.LoginResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    void testRegister_SuccessfulRegistration() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setUsername("johndoe");
        request.setPassword("password123");
        request.setPasswordConfirmation("password123");

        UserResponse userResponse = new UserResponse(1L, "johndoe", "John", "Doe");
        CreatedResponse createdResponse = new CreatedResponse(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                LocalDateTime.now(),
                userResponse);
        when(authService.register(any(RegistrationRequest.class))).thenReturn(createdResponse);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.getReasonPhrase().toUpperCase()))
                .andExpect(jsonPath("$.created_object.first_name").value("John"))
                .andExpect(jsonPath("$.created_object.last_name").value("Doe"))
                .andExpect(jsonPath("$.created_object.username").value("johndoe"));

        verify(authService, times(1)).register(any(RegistrationRequest.class));
    }

    @Test
    void testAuthenticate_SuccessfulAuthentication() throws Exception {
        // Prepare test data
        LoginRequest request = new LoginRequest();
        request.setUsername("johndoe");
        request.setPassword("password123");

        // Mock the behavior of the authService.authenticate method
        LoginResponse loginResponse = new LoginResponse("generated_jwt_token");
        when(authService.authenticate(any(LoginRequest.class))).thenReturn(loginResponse);

        // Perform the test
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.jwt_token").value("generated_jwt_token"));

        // Verify the interaction with the authService.authenticate method
        verify(authService, times(1)).authenticate(any(LoginRequest.class));
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}

