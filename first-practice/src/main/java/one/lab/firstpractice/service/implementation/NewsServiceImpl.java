package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.exception.ResourceNotFoundException;
import one.lab.firstpractice.model.dto.NewsDTO;
import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.NewsRepository;
import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.TopicService;
import one.lab.firstpractice.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static one.lab.firstpractice.storage.NewsStorage.NEWS_SEQUENCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private static final String NEWS_SERVICE_PREFIX = "[NEWS SERVICE] --- ";
    private static final Long AUTHOR_WITH_ID_2 = 2L;
    private static final Long AUTHOR_WITH_ID_4 = 4L;
    private static final Long TOPIC_WITH_ID_1 = 1L;
    private static final Long TOPIC_WITH_ID_2 = 2L;

    private final NewsRepository newsRepository;
    private final UserService userService;
    private final TopicService topicService;

    @Override
    public News createNews(NewsDTO dto) {

        User author = userService.fetchByUsername(dto.getAuthor());
        Topic topic = topicService.fetchByTopicName(dto.getTopic());

        News news = News.builder()
                .id(NEWS_SEQUENCE)
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author)
                .topic(topic)
                .likes(new Random().nextInt(1, 15))
                .publishedAt(LocalDate.now())
                .build();


        log.info(NEWS_SERVICE_PREFIX + "News with id: [" + NEWS_SEQUENCE + "] was created.");
        NEWS_SEQUENCE++;

        return newsRepository.save(news);
    }

    @Override
    public News fetchById(Long id) {
        log.info(NEWS_SERVICE_PREFIX + "Fetching news by ID.");
        return newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News with id: [" + id + "] not found."));
    }

    @Override
    public News fetchByTitle(String title) {
        log.info(NEWS_SERVICE_PREFIX + "Fetching news by title.");
        return newsRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("News with title: [" + title + "] not found."));
    }

    @Override
    public List<News> fetchAll() {
        log.info(NEWS_SERVICE_PREFIX + "Fetching all news.");
        return newsRepository.findAll();
    }

    @Override
    public List<News> fetchByTopic(Topic topic) {
        log.info(NEWS_SERVICE_PREFIX + "Fetching news by topic.");
        return newsRepository.findNewsByTopic(topic);
    }

    @Override
    public List<News> fetchByAuthor(User author) {
        log.info(NEWS_SERVICE_PREFIX + "Fetching news by author.");
        return newsRepository.findNewsByAuthor(author);
    }

    @Override
    public News fetchNewsByMostLikes() {
        log.info(NEWS_SERVICE_PREFIX + "Fetching the most liked news.");
        return newsRepository.findNewsByMostLikes()
                .orElseThrow(() -> new ResourceNotFoundException("Not found."));
    }

    @Override
    public void initNews() {
        for (int i = 1; i <= 5; i++) {

            Long authorId = i % 2 == 0 ? AUTHOR_WITH_ID_2 : AUTHOR_WITH_ID_4;
            Long topicId = i % 2 == 0 ? TOPIC_WITH_ID_1 : TOPIC_WITH_ID_2;

            NewsDTO dto = NewsDTO.builder()
                    .title("Title " + i)
                    .content("Content of the news #" + i)
                    .author(userService.fetchById(authorId).getUsername())
                    .topic(topicService.fetchById(topicId).getTopicName())
                    .build();
            createNews(dto);
        }
        NewsDTO dto = NewsDTO.builder()
                .title("Title " + 6)
                .content("Content of the news #" + 6)
                .author(userService.fetchById(2L).getUsername())
                .topic(topicService.fetchById(1L).getTopicName())
                .build();
        createNews(dto);
    }


}
