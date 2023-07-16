package one.lab.firstpractice;

import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.TopicService;
import one.lab.firstpractice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private static final String TOPIC_NAME = "Topic name 2";

    @Bean
    CommandLineRunner commandLineRunner(UserService userService,
                                        TopicService topicService,
                                        NewsService newsService) {
        return args -> {
            userService.initUsers();
            userService.fetchAll();
            userService.fetchById(2L);

            topicService.initTopics();
            topicService.fetchById(1L);
            topicService.fetchByTopicName(TOPIC_NAME);

            newsService.initNews();
            newsService.fetchById(1L);
            newsService.fetchAll();
            newsService.fetchNewsByMostLikes();
        };
    }
}
