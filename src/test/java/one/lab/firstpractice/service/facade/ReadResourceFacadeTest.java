package one.lab.firstpractice.service.facade;

import one.lab.firstpractice.model.dto.response.news.NewsResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadResourceFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private NewsService newsService;

    @InjectMocks
    private ReadResourceFacade readResourceFacade;


    @Test
    void testGetAllUsers() {
        Page<UserResponse> expectedResponse = new PageImpl<>(List.of(
                new UserResponse(1L, "johnDoe", "John", "Doe")),
                PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id")),
                1);

        when(userService.fetchAll(any(), any(), any())).thenReturn(expectedResponse);

        Page<UserResponse> response = readResourceFacade.getAllUsers(Optional.of(1), Optional.of(10), Optional.of("name"));

        verify(userService).fetchAll(Optional.of(1), Optional.of(10), Optional.of("name"));
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        UserResponse expectedResponse = new UserResponse(userId, "johnDoe", "John", "Doe");

        when(userService.fetchById(userId)).thenReturn(expectedResponse);

        UserResponse response = readResourceFacade.getUserById(userId);

        verify(userService).fetchById(userId);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetCurrentUser() {
        String username = "johnDoe";
        UserResponse expectedResponse = new UserResponse(1L, "johnDoe", "John", "Doe");

        when(userService.fetchCurrentUser(username)).thenReturn(expectedResponse);

        UserResponse response = readResourceFacade.getCurrentUser(username);

        verify(userService).fetchCurrentUser(username);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetAllAdmins() {
        Page<UserResponse> expectedResponse = createMockedPageOfUserResponses();

        when(userService.fetchAllAdmins()).thenReturn(expectedResponse);

        Page<UserResponse> response = readResourceFacade.getAllAdmins();

        verify(userService).fetchAllAdmins();
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetAllAuthors() {
        Page<UserResponse> expectedResponse = createMockedPageOfUserResponses();

        when(userService.fetchAllAuthors()).thenReturn(expectedResponse);

        Page<UserResponse> response = readResourceFacade.getAllAuthors();

        verify(userService).fetchAllAuthors();
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetNewsById() {

        Long newsId = 1L;
        NewsResponse expectedResponse = createMockedNewsResponse(newsId);

        when(newsService.fetchById(newsId)).thenReturn(expectedResponse);

        NewsResponse response = readResourceFacade.getNewsById(newsId);

        verify(newsService).fetchById(newsId);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetAllNews() {
        Page<NewsResponse> expectedResponse = createMockedNewsPage();

        when(newsService.fetchAll()).thenReturn(expectedResponse);

        Page<NewsResponse> response = readResourceFacade.getAllNews();

        verify(newsService).fetchAll();
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetNewsByTitle() {
        String title = "News Title";
        String normalizedTitle = title.trim();
        NewsResponse expectedResponse = createMockedNewsResponse();

        when(newsService.fetchByTitle(normalizedTitle)).thenReturn(expectedResponse);

        // Act
        NewsResponse response = readResourceFacade.getNewsByTitle(title);

        verify(newsService).fetchByTitle(normalizedTitle);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetNewsByTopicName() {
        String topicName = "Sport";
        String normalizedTopicName = topicName.trim();
        Page<NewsResponse> expectedResponse = createMockedNewsPage();

        when(newsService.fetchByTopicName(normalizedTopicName)).thenReturn(expectedResponse);

        Page<NewsResponse> response = readResourceFacade.getNewsByTopicName(topicName);

        verify(newsService).fetchByTopicName(normalizedTopicName);
        assertEquals(expectedResponse, response);
    }


    private Page<UserResponse> createMockedPageOfUserResponses() {
        List<UserResponse> userResponses = List.of(
                new UserResponse(1L, "username1", "Name 1", "User"),
                new UserResponse(2L, "username2", "Name 2", "User")
        );
        return new PageImpl<>(userResponses);
    }

    private NewsResponse createMockedNewsResponse() {
        return new NewsResponse(1L, "News Title",
                "News Content",
                0,
                LocalDateTime.now(),
                new UserResponse(1L, "johnDoe", "John", "Doe"),
                "Sport");
    }

    private NewsResponse createMockedNewsResponse(Long id) {
        return new NewsResponse(id, "News Title",
                "News Content",
                0,
                LocalDateTime.now(),
                new UserResponse(1L, "johnDoe", "John", "Doe"),
                "Sport");
    }

    private Page<NewsResponse> createMockedNewsPage() {
        List<NewsResponse> newsList = new ArrayList<>();
        newsList.add(new NewsResponse(1L, "News Title",
                "News Content",
                0,
                LocalDateTime.now(),
                new UserResponse(1L, "johnDoe", "John", "Doe"),
                "Sport"));
        newsList.add(new NewsResponse(2L, "News Title 2",
                "News Content 2",
                0,
                LocalDateTime.now(),
                new UserResponse(2L, "janeDoe", "Jane", "Doe"),
                "Sport"));
        return new PageImpl<>(newsList);
    }


}

