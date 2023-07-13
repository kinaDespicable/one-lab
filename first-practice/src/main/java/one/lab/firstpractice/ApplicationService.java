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
            System.out.println("------------------------ USER SERVICE ------------------------");
            userService.initUsers();
            System.out.println(userService.fetchAll());
            System.out.println(userService.fetchById(2L));

            System.out.println("------------------------ TOPIC SERVICE ------------------------");
            topicService.initTopics();
            System.out.println(topicService.fetchById(1L));
            System.out.println(topicService.fetchByTopicName(TOPIC_NAME));

            System.out.println("------------------------ NEWS SERVICE ------------------------");
            newsService.initNews();
            System.out.println(newsService.fetchById(1L));
            System.out.println(newsService.fetchAll());
            System.out.println(newsService.fetchNewsByMostLikes());
        };
    }
}
