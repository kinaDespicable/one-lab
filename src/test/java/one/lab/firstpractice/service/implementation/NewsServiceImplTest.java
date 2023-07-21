package one.lab.firstpractice.service.implementation;

import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.NewsRepository;
import one.lab.firstpractice.service.TopicService;
import one.lab.firstpractice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

class NewsServiceImplTest {

    @Mock
    NewsRepository newsRepository;

    @Mock
    TopicService topicService;

    @Mock
    UserService userService;

    @InjectMocks
    NewsServiceImpl newsServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        // Arrange
        User mockUser = new User(1L, "johnDoe", "John", "Doe", false, true, new ArrayList<>());
        Topic mockTopic = new Topic(1L, "Topic Title", new ArrayList<>());
        News newsToCreate = new News();

        // Act
        when(userService.fetchById(anyLong())).thenReturn(mockUser);
        when(topicService.fetchById(anyLong())).thenReturn(mockTopic);
        when(newsRepository.save(any(News.class))).thenAnswer(invocation -> {
            News savedNews = invocation.getArgument(0);
            savedNews.setId(1L);
            return savedNews;
        });
        News createdNews = newsServiceImpl.create(newsToCreate);

        // Assert
        assertThat(createdNews).isNotNull();
        assertThat(createdNews.getAuthor()).isEqualTo(mockUser);
        assertThat(createdNews.getTopic()).isEqualTo(mockTopic);
        verify(newsRepository, times(1)).save(createdNews);
    }

    @Test
    void testFetchById() {
        // Arrange
        Long newsId = 1L;
        User mockUser = new User(1L, "johnDoe", "John", "Doe", true, true, new ArrayList<>());
        Topic mockTopic = new Topic(1L, "Topic Title", new ArrayList<>());
        News mockNews = new News(newsId, "Example title", "Content", 12, LocalDateTime.now(), mockUser, mockTopic);

        // Act
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(mockNews));
        News news = newsServiceImpl.fetchById(newsId);

        // Assert
        assertThat(news).isNotNull();
        assertThat(news.getId()).isEqualTo(newsId);
        assertThat(news.getTitle()).isEqualTo("Example title");
        assertThat(news.getContent()).isEqualTo("Content");
    }

    @Test
    void testFetchByTitle_TitleFound() {
        // Arrange
        String title = "Example title";
        News mockNews = new News(1L, title, "Content", 2, LocalDateTime.now(), null, null);

        // Act
        when(newsRepository.findByTitle(title)).thenReturn(Optional.of(mockNews));
        News foundNews = newsServiceImpl.fetchByTitle(title);

        // Assert
        assertThat(foundNews).isNotNull();
        assertThat(foundNews.getTitle()).isEqualTo(title);
    }

    @Test
    void testFetchByTitle_TitleNotFound() {
        // Arrange
        String title = "Non-existing title";
        Optional<News> emptyOptional = Optional.empty();

        // Act
        when(newsRepository.findByTitle(title)).thenReturn(emptyOptional);
        News foundNews = newsServiceImpl.fetchByTitle(title);

        // Assert
        assertThat(foundNews).isNotNull();
        assertThat(foundNews.getId()).isNull();
    }

    @Test
    void testFetchAll() {
        // Arrange
        Page<News> mockPage = Page.empty();
        Pageable pageable = one.lab.firstpractice.utils.Page.getPageable();

        // Act
        when(newsRepository.findAll(pageable)).thenReturn(mockPage);
        Page<News> resultPage = newsServiceImpl.fetchAll();

        // Assert
        assertAll(
                () -> assertThat(resultPage).isNotNull(),
                () -> assertThat(resultPage).isEmpty()
        );
    }

    @Test
    void testFetchByTopicName() {
        //Arrange
        String topicName = "Example Topic";
        Topic mockTopic = new Topic(1L, topicName, new ArrayList<>());

        // Act
        when(topicService.fetchByTopicName(topicName)).thenReturn(mockTopic);
        Page<News> mockPage = Page.empty();
        Pageable pageable = one.lab.firstpractice.utils.Page.getPageable();
        when(newsRepository.findAllByTopic(pageable, mockTopic)).thenReturn(mockPage);
        Page<News> resultPage = newsServiceImpl.fetchByTopicName(topicName);

        // Assert
        assertAll(
                () -> assertThat(resultPage).isNotNull(),
                () -> assertThat(resultPage).isEmpty()
        );
    }

    @Test
    void testFetchByAuthorUsername() {
        // Arrange
        String username = "example_user";
        User mockUser = new User(1L, username, "John", "Doe", false, true, new ArrayList<>());

        // Act
        when(userService.fetchByUsername(username)).thenReturn(mockUser);
        Page<News> mockPage = Page.empty();
        Pageable pageable = one.lab.firstpractice.utils.Page.getPageable();
        when(newsRepository.findAllByAuthor(pageable, mockUser)).thenReturn(mockPage);
        Page<News> resultPage = newsServiceImpl.fetchByAuthorUsername(username);

        // Assert
        assertAll(
                () -> assertThat(resultPage).isNotNull(),
                () -> assertThat(resultPage).isEmpty()
        );
    }

    @Test
    void testFetchNewsByMostLikes_NoNewsFound() {
        // Arrange
        Optional<News> emptyOptional = Optional.empty();

        // Act
        when(newsRepository.findNewsWithMostLikes()).thenReturn(emptyOptional);
        News resultNews = newsServiceImpl.fetchNewsByMostLikes();

        // Assert
        assertThat(resultNews).isNotNull();
    }

    @Test
    void testFetchNewsByMostLikes_NewsFound() {
        // Arrange
        News mockNews = new News(1L, "Example title", "Content", 51, LocalDateTime.now(), null, null);

        // Act
        when(newsRepository.findNewsWithMostLikes()).thenReturn(Optional.of(mockNews));
        News resultNews = newsServiceImpl.fetchNewsByMostLikes();

        // Assertion
        assertAll(
                () -> assertThat(resultNews).isNotNull(),
                () -> assertThat(resultNews).isEqualTo(mockNews)
        );
    }

    @Test
    void testFetchCountByTopic() {
        // Arrange
        int mockCount = 10;
        String topicName = "Example Topic";
        Topic mockTopic = new Topic(1L, topicName, new ArrayList<>());

        // Act
        when(topicService.fetchByTopicName(topicName)).thenReturn(mockTopic);
        when(newsRepository.countNewsByTopic(mockTopic)).thenReturn(mockCount);
        Integer resultCount = newsServiceImpl.fetchCountByTopic(topicName);

        // Assertions
        assertAll(
                () -> assertThat(resultCount).isNotNull(),
                () -> assertThat(resultCount).isEqualTo(mockCount)
        );
    }
}

