package one.lab.firstpractice.service;

import one.lab.firstpractice.model.entity.News;

import java.util.List;

public interface NewsService {

    News fetchById(Long id);

    News fetchByTitle(String title);

    List<News> fetchAll();

    List<News> fetchByTopicName(String topicName);

    List<News> fetchByAuthorUsername(String username);

    News fetchNewsByMostLikes();

    Integer fetchCountByTopic(String topicName);

}
