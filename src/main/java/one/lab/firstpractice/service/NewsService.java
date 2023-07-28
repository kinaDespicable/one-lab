package one.lab.firstpractice.service;

import one.lab.firstpractice.model.dto.request.NewsRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.news.NewsResponse;
import one.lab.firstpractice.model.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface NewsService {

    CreatedResponse create(NewsRequest newsRequest, Authentication authentication);

    NewsResponse fetchById(Long id);

    NewsResponse fetchByTitle(String title);

    Page<NewsResponse> fetchAll();

    Page<NewsResponse> fetchByTopicName(String topicName);

    Page<NewsResponse> fetchByAuthorUsername(String username);

    NewsResponse fetchNewsByMostLikes();

}
