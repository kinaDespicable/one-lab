package one.lab.firstpractice.service;

import one.lab.firstpractice.model.dto.NewsDTO;
import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;

import java.util.List;

public interface NewsService {

    News createNews(NewsDTO dto);

    News fetchById(Long id);

    News fetchByTitle(String title);

    List<News> fetchAll();

    List<News> fetchByTopic(Topic topic);

    List<News> fetchByAuthor(User author);

    News fetchNewsByMostLikes();

    void initNews();
}
