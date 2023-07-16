package one.lab.firstpractice.mapper;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.model.entity.News;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.service.UserService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class NewsMapper implements RowMapper<News> {

    private final UserService userService;

    @Override
    public News mapRow(ResultSet rs, int rowNum) throws SQLException {

        User author = userService.fetchById(rs.getLong("author_id"));
        Timestamp timestamp = (Timestamp) rs.getObject("published_at");

        return News.builder()
                .id(rs.getLong("news_id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .likes(rs.getLong("likes"))
                .publishedAt(timestamp.toLocalDateTime())
                .author(author)
                .build();
    }

}
