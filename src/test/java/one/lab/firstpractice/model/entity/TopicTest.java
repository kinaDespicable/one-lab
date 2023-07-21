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
class TopicTest {

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Topic topic1 = Topic.builder().id(1L).topicName("Topic 1").build();
        Topic topic2 = Topic.builder().id(1L).topicName("Topic 1").build();
        Topic topic3 = Topic.builder().id(2L).topicName("Topic 2").build();

        // Then
        assertEquals(topic1, topic2, "Topics with the same id and topicName should be equal");
        assertNotEquals(topic1, topic3, "Topics with different ids should not be equal");
        assertNotEquals(topic2, topic3, "Topics with different ids should not be equal");

        // HashCode consistency
        assertEquals(topic1.hashCode(), topic2.hashCode(), "HashCodes of topics with the same id and topicName should be equal");
    }

    @Test
    void testNewsListOneToMany() {
        Topic topic = Topic.builder().topicName("Topic 1").build();
        News news1 = News.builder().title("News 1").content("Content 1").topic(topic).build();
        News news2 = News.builder().title("News 2").content("Content 2").topic(topic).build();

        entityManager.persist(topic);
        entityManager.persist(news1);
        entityManager.persist(news2);
        entityManager.flush();
        entityManager.clear();

        Topic retrievedTopic = entityManager.find(Topic.class, topic.getId());
        List<News> newsList = retrievedTopic.getNewsList();
        assertNotNull(newsList, "The newsList should not be null");
        assertEquals(2, newsList.size(), "The topic should have 2 news");

        assertEquals(topic, newsList.get(0).getTopic(), "The topic of news1 should be the topic");
        assertEquals(topic, newsList.get(1).getTopic(), "The topic of news2 should be the topic");

    }
}

