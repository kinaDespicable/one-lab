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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
@Sql("/data.sql")
class NewsTest {

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        // Perform any additional setup or initialization here
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        User user1 = User.builder().id(1L).username("user1").build();
        User user2 = User.builder().id(2L).username("user2").build();
        Topic topic1 = Topic.builder().id(1L).topicName("Topic 1").build();
        Topic topic2 = Topic.builder().id(2L).topicName("Topic 2").build();

        News news1 = News.builder()
                .id(1L)
                .title("News Title 1")
                .content("News Content 1")
                .publishedAt(LocalDateTime.now())
                .author(user1)
                .topic(topic1)
                .build();

        News news2 = News.builder()
                .id(1L)
                .title("News Title 1")
                .content("News Content 1")
                .publishedAt(LocalDateTime.now())
                .author(user1)
                .topic(topic1)
                .build();

        News news3 = News.builder()
                .id(2L)
                .title("News Title 2")
                .content("News Content 2")
                .publishedAt(LocalDateTime.now())
                .author(user2)
                .topic(topic2)
                .build();

        // Then
        assertEquals(news1, news2, "News with the same id, title, content, publishedAt, author, and topic should be equal");
        assertNotEquals(news1, news3, "News with different ids should not be equal");
        assertNotEquals(news2, news3, "News with different ids should not be equal");

        // HashCode consistency
        assertEquals(news1.hashCode(), news2.hashCode(), "HashCodes of news with the same attributes should be equal");
    }
}

