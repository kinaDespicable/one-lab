package one.lab.firstpractice;

import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.TopicService;
import one.lab.firstpractice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.CommandLineRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ApplicationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TopicService topicService;

    @Mock
    private NewsService newsService;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void testCommandLineRunner() throws Exception {
        // Arrange - Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Act - Execute the CommandLineRunner
        CommandLineRunner commandLineRunner = applicationService.commandLineRunner(userService, topicService, newsService);
        commandLineRunner.run();

        // Assert - Verify that the respective methods are called as expected
        verify(userService, times(1)).fetchByUsername("johnDoe");
        verify(userService, times(1)).fetchAll();
        verify(topicService, times(1)).fetchById(1L);
        verify(topicService, times(1)).fetchByTopicName("Technologies");
        verify(newsService, times(1)).fetchById(1L);
        verify(newsService, times(1)).create(any(News.class));
        verify(newsService, times(1)).fetchByTitle("Revolutionary AI System Enhances Medical Diagnoses, Saving Lives");
        verify(newsService, times(1)).fetchNewsByMostLikes();
    }
}
