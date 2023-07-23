package one.lab.firstpractice;

import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.service.NewsService;
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
    private NewsService newsService;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void testCommandLineRunner() throws Exception {
        // Arrange - Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Act - Execute the CommandLineRunner
        CommandLineRunner commandLineRunner = applicationService.commandLineRunner(newsService);
        commandLineRunner.run();

        // Assert - Verify that the respective methods are called as expected
        verify(newsService, times(1)).create(new News());
        verify(newsService, times(1)).fetchNewsByMostLikes();
    }
}
