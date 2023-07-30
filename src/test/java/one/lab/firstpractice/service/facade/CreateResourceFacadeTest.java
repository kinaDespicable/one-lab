package one.lab.firstpractice.service.facade;

import one.lab.firstpractice.model.dto.request.CreateUserRequest;
import one.lab.firstpractice.model.dto.request.NewsRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateResourceFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private NewsService newsService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CreateResourceFacade createResourceFacade;

    @Test
    void testCreateUser_SuccessfulCreation() {
        CreateUserRequest createUserRequest = new CreateUserRequest("johnDoe", "password123", "password123", "John", "Doe", List.of("ROLE_USER"));
        CreatedResponse expectedResponse = new CreatedResponse(HttpStatus.CREATED, HttpStatus.CREATED.value(), LocalDateTime.now(), null);

        when(userService.createUser(authentication, createUserRequest)).thenReturn(expectedResponse);

        CreatedResponse response = createResourceFacade.createUser(authentication, createUserRequest);

        verify(userService).createUser(authentication, createUserRequest);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testCreateNews_SuccessfulCreation() {

        NewsRequest newsRequest = new NewsRequest("Test Title", "Test Content", "Test Sport");
        CreatedResponse expectedResponse = new CreatedResponse(HttpStatus.CREATED, HttpStatus.CREATED.value(), LocalDateTime.now(), null);

        when(newsService.create(newsRequest, authentication)).thenReturn(expectedResponse);

        CreatedResponse response = createResourceFacade.createNews(newsRequest, authentication);

        verify(newsService).create(newsRequest, authentication);
        assertEquals(expectedResponse, response);
    }

}

