package one.lab.firstpractice.service.implementation;

import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

class TopicServiceImplTest {

    @Mock
    TopicRepository topicRepository;

    @InjectMocks
    TopicServiceImpl topicServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchById_TopicFound() {
        // Arrange
        Long topicId = 1L;
        Topic mockTopic = new Topic(1L, "Topic Title", new ArrayList<>());
        Optional<Topic> optionalTopic = Optional.of(mockTopic);

        // Act
        when(topicRepository.findById(topicId)).thenReturn(optionalTopic);
        Topic topic = topicServiceImpl.fetchById(topicId);

        // Assert
        assertAll(
                () -> assertThat(topic).isNotNull(),
                () -> assertThat(topic).isEqualTo(mockTopic)
        );

    }

    @Test
    void testFetchById_TopicNotFound() {
        // Arrange
        Long topicId = 2L;
        Optional<Topic> emptyOptional = Optional.empty();

        // Act
        when(topicRepository.findById(topicId)).thenReturn(emptyOptional);
        Topic topic = topicServiceImpl.fetchById(topicId);

        // Assert
        assertAll(
                () -> assertThat(topic).isNotNull(),
                () -> assertThat(topic).isEqualTo(new Topic())
        );
    }

    @Test
    void testFetchByTopicName_TopicFound() {
        // Arrange
        String topicName = "Java";
        Topic mockTopic = new Topic(1L, topicName, new ArrayList<>());
        Optional<Topic> optionalTopic = Optional.of(mockTopic);

        // Act
        when(topicRepository.findByTopicName(topicName)).thenReturn(optionalTopic);
        Topic topic = topicServiceImpl.fetchByTopicName(topicName);

        // Assert
        assertAll(
                () -> assertThat(topic).isNotNull(),
                () -> assertThat(topic).isEqualTo(mockTopic)
        );
    }

    @Test
    void testFetchByTopicName_TopicNotFound() {
        // Arrange
        String topicName = "NonExistentTopic";
        Optional<Topic> emptyOptional = Optional.empty();

        // Act
        when(topicRepository.findByTopicName(topicName)).thenReturn(emptyOptional);
        Topic topic = topicServiceImpl.fetchByTopicName(topicName);
        // Assert
        assertAll(
                () -> assertThat(topic).isNotNull(),
                () -> assertThat(topic).isEqualTo(new Topic())
        );
    }
}

