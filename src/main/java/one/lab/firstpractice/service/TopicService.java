package one.lab.firstpractice.service;

import one.lab.firstpractice.model.entity.Topic;

public interface TopicService {

    Topic fetchByTopicName(String topicName);

}
