package one.lab.firstpractice.mapper;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.service.TopicService;
import one.lab.firstpractice.service.UserService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NewsMapper implements RowMapper<News> {

    private final UserService userService;
    private final TopicService topicService;

    @Override
    public News mapRow(ResultSet rs, int rowNum) throws SQLException {

        User author = userService.fetchById(rs.getLong("author_id"));
        Topic topic = topicService.fetchById(rs.getLong("topic_id"));

        return News.builder()
                .id(rs.getLong("news_id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .likes(rs.getLong("likes"))
                .publishedAt((LocalDateTime) rs.getObject("published_at"))
                .author(author)
                .topic(topic)
                .build();
    }

}
