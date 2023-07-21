package one.lab.firstpractice.model.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@Sql("/data.sql")
class UserTest {

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        User user1 = User.builder().id(1L).username("user1").build();
        User user2 = User.builder().id(1L).username("user1").build();
        User user3 = User.builder().id(2L).username("user2").build();

        // Then
        assertEquals(user1, user2, "Users with the same id and username should be equal");
        assertNotEquals(user1, user3, "Users with different ids should not be equal");
        assertNotEquals(user2, user3, "Users with different ids should not be equal");

        // HashCode consistency
        assertEquals(user1.hashCode(), user2.hashCode(), "HashCodes of users with the same id and username should be equal");
    }

    @Test
    void testWrittenNewsOneToMany() {
        // Given
        User user = User.builder()
                .username("user1")
                .firstName("User")
                .lastName("First")
                .author(true)
                .admin(true)
                .build();
        News news1 = News.builder()
                .title("News 1")
                .content("Content 1")
                .author(user)
                .likes(14)
                .publishedAt(LocalDateTime.now())
                .build();
        News news2 = News.builder()
                .title("News 2")
                .content("Content 2")
                .author(user)
                .likes(14)
                .publishedAt(LocalDateTime.now())
                .build();

        // When
        entityManager.persist(user);
        entityManager.persist(news1);
        entityManager.persist(news2);
        entityManager.flush();
        entityManager.clear();

        // Then
        User retrievedUser = entityManager.find(User.class, user.getId());
        List<News> writtenNews = retrievedUser.getWrittenNews();
        assertNotNull(writtenNews, "The writtenNews list should not be null");
        assertEquals(2, writtenNews.size(), "The user should have 2 written news");

        // Make sure the relationship is set up correctly
        assertEquals(user, writtenNews.get(0).getAuthor(), "The author of news1 should be the user");
        assertEquals(user, writtenNews.get(1).getAuthor(), "The author of news2 should be the user");


    }
}

