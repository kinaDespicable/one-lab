package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.exception.exceptions.ResourceNotFoundException;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.repository.TopicRepository;
import one.lab.firstpractice.service.TopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    @Transactional(readOnly = true)
    public Topic fetchByTopicName(String topicName) {
        return topicRepository.findByTopicName(topicName)
                .orElseThrow(() -> new ResourceNotFoundException("Topic is not found: " + topicName.trim()));
    }

}
