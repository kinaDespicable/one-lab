package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.custom.annotation.Loggable;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.repository.TopicRepository;
import one.lab.firstpractice.service.TopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private static final String TOPIC_SERVICE_PREFIX = "[TOPIC SERVICE] --- ";

    private final TopicRepository topicRepository;


    @Override
    @Loggable
    @Transactional(readOnly = true)
    public Topic fetchById(Long id) {
        return topicRepository.findById(id).orElse(new Topic());
    }

    @Override
    @Loggable
    @Transactional(readOnly = true)
    public Topic fetchByTopicName(String topicName) {
        return topicRepository.findByTopicName(topicName).orElse(new Topic());
    }

}
