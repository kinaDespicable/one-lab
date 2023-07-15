package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.exception.ResourceNotFoundException;
import one.lab.firstpractice.model.dto.TopicDTO;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.repository.TopicRepository;
import one.lab.firstpractice.service.TopicService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static one.lab.firstpractice.storage.TopicStorage.TOPIC_SEQUENCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private static final String TOPIC_SERVICE_PREFIX = "[TOPIC SERVICE] --- ";

    private final TopicRepository topicRepository;

    @Override
    public Topic createTopic(TopicDTO dto) {
        Topic topic = Topic.builder()
                .id(TOPIC_SEQUENCE)
                .topicName(dto.getTopicName())
                .newsList(new ArrayList<>())
                .build();
        log.info(TOPIC_SERVICE_PREFIX + "Topic with id: [" + TOPIC_SEQUENCE + "] was created.");
        TOPIC_SEQUENCE++;
        return topicRepository.save(topic);
    }

    @Override
    public Topic fetchById(Long id) {
        log.info(TOPIC_SERVICE_PREFIX + "Fetching topic by id.");
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id: [" + id + "]not found."));
    }

    @Override
    public Topic fetchByTopicName(String topicName) {
        log.info(TOPIC_SERVICE_PREFIX + "Fetching topic by topic name.");
        return topicRepository.findByTopicName(topicName)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with name: " + topicName + " not found."));
    }

    @Override
    public void initTopics() {
        for (int i = 1; i <= 3; i++) {
            TopicDTO dto = TopicDTO.builder()
                    .topicName("Topic name " + i)
                    .build();
            createTopic(dto);
        }
    }
}
