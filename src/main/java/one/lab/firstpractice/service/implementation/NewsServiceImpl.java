package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.exception.exceptions.NoAuthorityException;
import one.lab.firstpractice.exception.exceptions.ResourceNotFoundException;
import one.lab.firstpractice.model.dto.request.NewsRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.news.NewsResponse;
import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.NewsRepository;
import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.TopicService;
import one.lab.firstpractice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static one.lab.firstpractice.utils.Page.getPageable;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private static final int DEFAULT_LIKES = 0;

    private final NewsRepository newsRepository;
    private final UserService userService;
    private final TopicService topicService;


    @Override
    public CreatedResponse create(NewsRequest newsRequest, Authentication authentication) {
        isAuthor(authentication);

        User author = userService.fetchByUsername((String) authentication.getPrincipal());
        Topic topic = topicService.fetchByTopicName(newsRequest.getTopic().trim());
        News news = News.builder()
                .title(newsRequest.getTitle().trim())
                .content(newsRequest.getContent().trim())
                .likes(DEFAULT_LIKES)
                .publishedAt(LocalDateTime.now())
                .author(author)
                .topic(topic)
                .build();

        NewsResponse response = NewsResponse.mapToResponse(newsRepository.save(news));

        return CreatedResponse.builder()
                .status(CREATED)
                .statusCode(CREATED.value())
                .timestamp(LocalDateTime.now())
                .createdObject(response)
                .build();
    }

    @Override
    public NewsResponse fetchById(Long id) {
        News fetchedNews = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not found requested news."));
        return NewsResponse.mapToResponse(fetchedNews);
    }

    @Override
    public NewsResponse fetchByTitle(String title) {
        News news = newsRepository.findByTitle(title.trim())
                .orElseThrow(() -> new ResourceNotFoundException("News with title:" + title + " does not exist"));
        return NewsResponse.mapToResponse(news);
    }

    @Override
    public Page<NewsResponse> fetchAll() {
        Page<News> newsPage = newsRepository.findAll(getPageable());
        return mapToNewsResponsePage(newsPage);
    }

    @Override
    public Page<NewsResponse> fetchByTopicName(String topicName) {
        Topic topic = topicService.fetchByTopicName(topicName.trim());
        Page<News> newsPage = newsRepository.findAllByTopic(getPageable(), topic);
        return mapToNewsResponsePage(newsPage);
    }

    @Override
    public Page<NewsResponse> fetchByAuthorUsername(String username) {
        User author = userService.fetchByUsername(username.trim());
        Page<News> newsPage = newsRepository.findAllByAuthor(getPageable(), author);
        return mapToNewsResponsePage(newsPage);
    }

    @Override
    public NewsResponse fetchNewsByMostLikes() {
        News news = newsRepository.findNewsWithMostLikes()
                .orElseThrow(() -> new ResourceNotFoundException("No news was found"));
        return NewsResponse.mapToResponse(news);
    }


    private Page<NewsResponse> mapToNewsResponsePage(Page<News> newsPage) {
        List<NewsResponse> responseList = newsPage.getContent()
                .stream()
                .map(NewsResponse::mapToResponse)
                .toList();
        return new PageImpl<>(responseList, getPageable(), newsPage.getTotalPages());
    }

    private void isAuthor(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        if (!userService.isAuthor(username)) {
            throw new NoAuthorityException("User has no authority to perform actions on this route.");
        }
    }

}
