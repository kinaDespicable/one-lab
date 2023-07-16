package one.lab.firstpractice.repository;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.mapper.UserMapper;
import one.lab.firstpractice.model.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private static final String FIND_ALL = "SELECT * FROM users";
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE user_id=?";
    private static final String FIND_BY_USERNAME = "SELECT * FROM users WHERE username=?";

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;


    public Optional<User> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID, userMapper, id)
                .stream()
                .findFirst();
    }

    public boolean existsByUsername(String username) {
        Integer count = jdbcTemplate.queryForObject(FIND_BY_USERNAME, Integer.class, username);
        return count != null && count > 0;
    }

    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL, userMapper);
    }

    public Optional<User> findByUsername(String username) {
        return jdbcTemplate.query(FIND_BY_USERNAME, userMapper, username)
                .stream()
                .findFirst();
    }

}
