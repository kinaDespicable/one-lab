package one.lab.firstpractice.mapper;

import one.lab.firstpractice.model.entity.Topic;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TopicMapper implements RowMapper<Topic> {


    @Override
    public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Topic.builder()
                .id(rs.getLong("topic_id"))
                .topicName(rs.getString("topic_name"))
                .build();
    }
}
