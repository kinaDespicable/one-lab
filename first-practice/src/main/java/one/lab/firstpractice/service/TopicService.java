package one.lab.firstpractice.service;

import one.lab.firstpractice.model.dto.TopicDTO;
import one.lab.firstpractice.model.entity.Topic;

public interface TopicService {

    Topic createTopic(TopicDTO dto);

    Topic fetchById(Long id);

    Topic fetchByTopicName(String topicName);

    void initTopics();
}
