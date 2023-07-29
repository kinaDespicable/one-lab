package one.lab.firstpractice.service.implementation;

import one.lab.firstpractice.exception.exceptions.ResourceNotFoundException;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceImplTest {

    private static final String TOPIC_NAME = "Sport";

    @Mock
    TopicRepository topicRepository;

    @InjectMocks
    TopicServiceImpl topicServiceImpl;

    @Test
    void testFetchByTopicName_Successful() {
        String topicName = "SomeTopic";
        Topic expectedTopic = new Topic(1L, topicName, new ArrayList<>());

        when(topicRepository.findByTopicName(topicName)).thenReturn(Optional.of(expectedTopic));

        Topic result = topicServiceImpl.fetchByTopicName(topicName);

        assertNotNull(result);
        assertEquals(expectedTopic, result);
    }

    @Test
    void testFetchByTopicName_TopicNotFound() {
        when(topicRepository.findByTopicName(TOPIC_NAME)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> topicServiceImpl.fetchByTopicName(TOPIC_NAME));

        verify(topicRepository, times(1)).findByTopicName(TOPIC_NAME);
    }
}
