package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.repository.TopicRepository;
import one.lab.firstpractice.service.TopicService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private static final String TOPIC_SERVICE_PREFIX = "[TOPIC SERVICE] --- ";

    private final TopicRepository topicRepository;


    @Override
    public Topic fetchById(Long id) {
        log.info(TOPIC_SERVICE_PREFIX + "Fetching topic by id.");
        Optional<Topic> optional = topicRepository.findById(id);
        if (optional.isEmpty()) {
            log.error(TOPIC_SERVICE_PREFIX + "Topic retrieval failed. Topic with id: " + id + " not found.");
        }
        return optional.orElse(new Topic());
    }

    @Override
    public Topic fetchByTopicName(String topicName) {
        log.info(TOPIC_SERVICE_PREFIX + "Fetching topic by topic name.");
        Optional<Topic> optional = topicRepository.findByTopicName(topicName);
        if (optional.isEmpty()) {
            log.error(TOPIC_SERVICE_PREFIX + "Topic retrieval failed. Topic with name: " + topicName + " not found.");
        }
        return optional.orElse(new Topic());
    }

}
