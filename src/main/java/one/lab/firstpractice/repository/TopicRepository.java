package one.lab.firstpractice.repository;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.mapper.TopicMapper;
import one.lab.firstpractice.model.entity.Topic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TopicRepository {

    private static final String FIND_BY_ID = "SELECT * FROM topics WHERE topic_id = ?";
    private static final String FIND_BY_TOPIC_NAME = "SELECT * FROM topics WHERE topic_name = ?";

    private final JdbcTemplate jdbcTemplate;
    private final TopicMapper topicMapper;


    public Optional<Topic> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID, topicMapper, id)
                .stream().findFirst();
    }

    public Optional<Topic> findByTopicName(String topicName) {
        return jdbcTemplate.query(FIND_BY_TOPIC_NAME, topicMapper, topicName)
                .stream().findFirst();
    }


}
