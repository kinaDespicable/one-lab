package one.lab.firstpractice.service;

import one.lab.firstpractice.model.entity.Topic;

public interface TopicService {


    Topic fetchById(Long id);

    Topic fetchByTopicName(String topicName);

}
