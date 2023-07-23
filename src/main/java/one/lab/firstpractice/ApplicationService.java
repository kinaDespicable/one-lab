package one.lab.firstpractice;

import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.service.NewsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Bean
    public CommandLineRunner commandLineRunner(NewsService newsService) {
        return args -> {
            newsService.create(new News());
            newsService.fetchNewsByMostLikes();
        };
    }

}
