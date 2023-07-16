package one.lab.firstpractice.mapper;

import one.lab.firstpractice.model.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("user_id"))
                .username(resultSet.getString("username"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .admin(resultSet.getBoolean("admin"))
                .author(resultSet.getBoolean("author"))
                .build();
    }
}
