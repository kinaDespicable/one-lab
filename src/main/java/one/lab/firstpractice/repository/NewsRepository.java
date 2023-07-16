package one.lab.firstpractice.repository;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.mapper.NewsMapper;
import one.lab.firstpractice.model.entity.News;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsRepository {

    private static final String FIND_ALL = "SELECT * FROM news";
    private static final String FIND_BY_ID = "SELECT * FROM news WHERE news_id=?";
    private static final String FIND_BY_TITLE = "SELECT * FROM news WHERE title=?";
    private static final String FIND_MOST_LIKED_NEWS = "SELECT * FROM news ORDER BY likes DESC LIMIT 1";
    private static final String FIND_BY_TOPIC = "SELECT n.news_id, n.content, n.title, n.likes, n.published_at, n.author_id, t.topic_id FROM topics t LEFT JOIN news n ON t.topic_id= n.topic_id WHERE t.topic_name= ?";
    private static final String FIND_BY_AUTHOR = "SELECT n.news_id, n.content, n.title, n.likes, n.published_at, n.author_id FROM news n LEFT JOIN users u ON n.author_id = u.user_id WHERE u.username = ?";
    private static final String FIND_NEWS_COUNT_BY_TOPIC = "SELECT COUNT(n.topic_id) AS news_count FROM topics t LEFT JOIN news n ON t.topic_id= n.topic_id WHERE t.topic_name= ?";

    private final JdbcTemplate jdbcTemplate;
    private final NewsMapper newsMapper;


    public List<News> findAll() {
        return jdbcTemplate.query(FIND_ALL, newsMapper);
    }

    public Optional<News> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID, newsMapper, id)
                .stream().findFirst();
    }

    public Optional<News> findByTitle(String title) {
        return jdbcTemplate.query(FIND_BY_TITLE, newsMapper, title)
                .stream().findFirst();
    }

    public List<News> findByTopic(String topicName) {
        return jdbcTemplate.query(FIND_BY_TOPIC, newsMapper, topicName);
    }

    public List<News> findByAuthor(String username) {
        return jdbcTemplate.query(FIND_BY_AUTHOR, newsMapper, username);
    }

    public Optional<News> findByMostLikes() {
        return jdbcTemplate.query(FIND_MOST_LIKED_NEWS, newsMapper)
                .stream().findFirst();
    }

    public Integer findCountByTopicName(String topicName) {
        return jdbcTemplate.queryForObject(FIND_NEWS_COUNT_BY_TOPIC, Integer.class, topicName);
    }


}
