package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.custom.annotation.Loggable;
import one.lab.firstpractice.custom.annotation.Timed;
import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.repository.NewsRepository;
import one.lab.firstpractice.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private static final String NEWS_SERVICE_PREFIX = "[NEWS SERVICE] --- ";

    private final NewsRepository newsRepository;

    @Override
    public News fetchById(Long id) {
        log.info(NEWS_SERVICE_PREFIX + "Fetching news by id.");
        Optional<News> optional = newsRepository.findById(id);
        if (optional.isEmpty()) {
            log.error(NEWS_SERVICE_PREFIX + "News retrieval failed. News with id: " + id + " not found.");
        }
        return optional.orElse(new News());
    }

    @Override
    public News fetchByTitle(String title) {
        log.info(NEWS_SERVICE_PREFIX + "Fetching news by title.");
        Optional<News> optional = newsRepository.findByTitle(title);
        if (optional.isEmpty()) {
            log.error(NEWS_SERVICE_PREFIX + "News retrieval failed. News with title: " + title + " not found.");
        }
        return optional.orElse(new News());
    }

    @Override
    public List<News> fetchAll() {
        log.info(NEWS_SERVICE_PREFIX + "Fetching all news.");
        return newsRepository.findAll();
    }

    @Timed
    @Loggable
    @Override
    public List<News> fetchByTopicName(String topicName) {
        return newsRepository.findByTopic(topicName);
    }

    @Timed
    @Loggable
    @Override
    public List<News> fetchByAuthorUsername(String username) {
        return newsRepository.findByAuthor(username);
    }

    @Timed
    @Loggable
    @Override
    public News fetchNewsByMostLikes() {
        Optional<News> optional = newsRepository.findByMostLikes();
        if (optional.isEmpty()) {
            log.error(NEWS_SERVICE_PREFIX + "No news found.");
        }
        return optional.orElse(new News());
    }

    @Timed
    @Loggable
    @Override
    public Integer fetchCountByTopic(String topicName) {
        return newsRepository.findCountByTopicName(topicName);
    }


}
