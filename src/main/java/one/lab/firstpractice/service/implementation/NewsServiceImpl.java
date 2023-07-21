package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.custom.annotation.Loggable;
import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.NewsRepository;
import one.lab.firstpractice.service.NewsService;
import one.lab.firstpractice.service.TopicService;
import one.lab.firstpractice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static one.lab.firstpractice.utils.Page.getPageable;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final TopicService topicService;
    private final UserService userService;

    @Override
    @Loggable
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public News create(News news) {
        User author = userService.fetchById(4L);
        Topic topic = topicService.fetchById(2L);

        News news1 = News.builder()
                .title("Example title")
                .content("Lorem ipsum dolores sit amin le quote")
                .author(author)
                .likes(0)
                .publishedAt(LocalDateTime.now())
                .topic(topic)
                .build();

        return newsRepository.save(news1);
    }

    @Override
    @Loggable
    @Transactional(propagation = Propagation.SUPPORTS)
    public News fetchById(Long id) {
        return newsRepository.findById(id).orElse(new News());
    }

    @Override
    @Loggable
    @Transactional(propagation = Propagation.SUPPORTS)
    public News fetchByTitle(String title) {
        return newsRepository.findByTitle(title).orElse(new News());
    }

    @Override
    public Page<News> fetchAll() {
        Pageable pageable = getPageable();
        return newsRepository.findAll(pageable);
    }

    @Override
    public Page<News> fetchByTopicName(String topicName) {
        Topic topic = topicService.fetchByTopicName(topicName);
        Pageable pageable = getPageable();
        return newsRepository.findAllByTopic(pageable, topic);
    }

    @Override
    @Loggable
    public Page<News> fetchByAuthorUsername(String username) {
        User author = userService.fetchByUsername(username);
        Pageable pageable = getPageable();
        return newsRepository.findAllByAuthor(pageable, author);
    }

    @Override
    public News fetchNewsByMostLikes() {
        return newsRepository.findNewsWithMostLikes().orElse(new News());
    }

    @Override
    public Integer fetchCountByTopic(String topicName) {
        Topic topic = topicService.fetchByTopicName(topicName);
        return newsRepository.countNewsByTopic(topic);
    }

}
