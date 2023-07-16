package one.lab.firstpractice;

import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.TopicService;
import one.lab.firstpractice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService,
                                               TopicService topicService,
                                               NewsService newsService) {
        return args -> {
            userService.fetchById(1L);
            userService.fetchByUsername("johnDoe");
            userService.fetchAll();

            topicService.fetchById(1L);
            topicService.fetchByTopicName("Technologies");

            newsService.fetchByAuthorUsername("johnDoe");
            newsService.fetchByTopicName("Sport");
            newsService.fetchNewsByMostLikes();
            newsService.fetchCountByTopic("War");
        };
    }

}
