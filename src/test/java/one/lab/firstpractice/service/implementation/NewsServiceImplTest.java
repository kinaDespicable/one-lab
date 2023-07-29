package one.lab.firstpractice.service.implementation;

import one.lab.firstpractice.exception.exceptions.ResourceNotFoundException;
import one.lab.firstpractice.model.dto.response.news.NewsResponse;
import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Role;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.NewsRepository;
import one.lab.firstpractice.service.TopicService;
import one.lab.firstpractice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @Mock
    NewsRepository newsRepository;

    @Mock
    UserService userService;

    @Mock
    TopicService topicService;

    @InjectMocks
    NewsServiceImpl newsServiceImpl;


    @Test
    void testFetchById_Successful() {
        Long newsId = 1L;
        News news = new News(newsId, "Test News Title", "Test News Content", 0, LocalDateTime.now(), new User(), new Topic());

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));

        NewsResponse response = newsServiceImpl.fetchById(newsId);

        assertNotNull(response);
        assertEquals(newsId, response.getId());
        assertEquals("Test News Title", response.getTitle());
        assertEquals("Test News Content", response.getContent());

        verify(newsRepository, times(1)).findById(newsId);
    }

    @Test
    void testFetchById_NotFound() {
        Long nonExistentId = 123L;

        when(newsRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> newsServiceImpl.fetchById(nonExistentId));

        verify(newsRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void testFetchByTitle_ExistingNews() {
        String title = "Test News Title";
        News existingNews = new News(1L, "Test News Title", "Test News Content", 0, LocalDateTime.now(), new User(), new Topic());

        when(newsRepository.findByTitle(title)).thenReturn(Optional.of(existingNews));

        NewsResponse response = newsServiceImpl.fetchByTitle(title);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(title, response.getTitle());
        assertEquals("Test News Content", response.getContent());

        verify(newsRepository, times(1)).findByTitle(title);
    }

    @Test
    void testFetchByTitle_NonExistingNews() {
        String nonExistingTitle = "Non-existing News Title";

        when(newsRepository.findByTitle(nonExistingTitle)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> newsServiceImpl.fetchByTitle(nonExistingTitle));

        verify(newsRepository, times(1)).findByTitle(nonExistingTitle);
    }

    @Test
    void testFetchAll() {
        int pageNumber = 0;
        int pageSize = 5;

        List<News> newsList = new ArrayList<>();
        newsList.add(new News(1L, "News Title 1", "Content 1", 0, LocalDateTime.now(), new User(), new Topic()));
        newsList.add(new News(2L, "News Title 2", "Content 2", 0, LocalDateTime.now(), new User(), new Topic()));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<News> newsPage = new PageImpl<>(newsList, pageable, newsList.size());

        when(newsRepository.findAll(pageable)).thenReturn(newsPage);

        Page<NewsResponse> responsePage = newsServiceImpl.fetchAll();

        assertNotNull(responsePage);
        assertEquals(2, responsePage.getTotalElements());

        verify(newsRepository, times(1)).findAll(pageable);
    }

    @Test
    void testFetchByTopicName() {
        String topicName = "Technology";
        Topic topic = new Topic(1L, topicName, new ArrayList<>());

        int pageNumber = 0;
        int pageSize = 5;

        List<News> newsList = new ArrayList<>();
        newsList.add(new News(1L, "News Title 1", "Content 1", 0, LocalDateTime.now(), new User(), new Topic()));
        newsList.add(new News(2L, "News Title 2", "Content 2", 0, LocalDateTime.now(), new User(), new Topic()));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<News> newsPage = new PageImpl<>(newsList, pageable, newsList.size());

        when(topicService.fetchByTopicName(topicName)).thenReturn(topic);
        when(newsRepository.findAllByTopic(pageable, topic)).thenReturn(newsPage);

        Page<NewsResponse> responsePage = newsServiceImpl.fetchByTopicName(topicName);

        assertNotNull(responsePage);
        assertEquals(2, responsePage.getTotalElements());

        verify(topicService, times(1)).fetchByTopicName(topicName);
        verify(newsRepository, times(1)).findAllByTopic(pageable, topic);
    }

    @Test
    void testFetchByAuthorUsername() {
        String username = "johnDoe";
        User author = new User(1L, username, "John", "Doe", "password", List.of(new Role()), new ArrayList<>());

        int pageNumber = 0;
        int pageSize = 5;

        List<News> newsList = new ArrayList<>();
        newsList.add(new News(1L, "News Title 1", "Content 1", 0, LocalDateTime.now(), new User(), new Topic()));
        newsList.add(new News(2L, "News Title 2", "Content 2", 0, LocalDateTime.now(), new User(), new Topic()));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<News> newsPage = new PageImpl<>(newsList, pageable, newsList.size());

        when(userService.fetchByUsername(username)).thenReturn(author);
        when(newsRepository.findAllByAuthor(pageable, author)).thenReturn(newsPage);

        Page<NewsResponse> responsePage = newsServiceImpl.fetchByAuthorUsername(username);

        assertNotNull(responsePage);
        assertEquals(2, responsePage.getTotalElements());

        verify(userService, times(1)).fetchByUsername(username);
        verify(newsRepository, times(1)).findAllByAuthor(pageable, author);
    }

    @Test
    void testFetchNewsByMostLikes() {
        Long newsIdWithMostLikes = 1L;

        News newsWithMostLikes = new News(newsIdWithMostLikes, "News Title", "Content", 100, LocalDateTime.now(), new User(), new Topic());

        when(newsRepository.findNewsWithMostLikes()).thenReturn(Optional.of(newsWithMostLikes));

        NewsResponse response = newsServiceImpl.fetchNewsByMostLikes();

        assertNotNull(response);
        assertEquals(newsIdWithMostLikes, response.getId());
        assertEquals("News Title", response.getTitle());
        assertEquals("Content", response.getContent());
        assertEquals(100, response.getLikes());

        verify(newsRepository, times(1)).findNewsWithMostLikes();
    }

    @Test
    void testFetchNewsByMostLikes_NoNewsFound() {
        when(newsRepository.findNewsWithMostLikes()).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> newsServiceImpl.fetchNewsByMostLikes());

        verify(newsRepository, times(1)).findNewsWithMostLikes();
    }
}

