package one.lab.firstpractice.service;

import one.lab.firstpractice.model.entity.News;
import org.springframework.data.domain.Page;

public interface NewsService {

    News create(News news);

    News fetchById(Long id);

    News fetchByTitle(String title);

    Page<News> fetchAll();

    Page<News> fetchByTopicName(String topicName);

    Page<News> fetchByAuthorUsername(String username);

    News fetchNewsByMostLikes();

    Integer fetchCountByTopic(String topicName);

}
