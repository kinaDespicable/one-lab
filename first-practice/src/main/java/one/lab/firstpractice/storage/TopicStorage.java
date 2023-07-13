package one.lab.firstpractice.storage;

import one.lab.firstpractice.model.entity.Topic;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TopicStorage {

    public static Long TOPIC_SEQUENCE = 1L;

    private final List<Topic> storage = new ArrayList<>();

    public void addTopicToStorage(Topic topic){
        storage.add(topic);
    }

    public Optional<Topic> getById(Long id){
        return storage.stream()
                .filter(topic -> topic.getId().equals(id))
                .findFirst();
    }

    public Optional<Topic> getByTopicName(String topicName){
        return storage.stream()
                .filter(topic -> topic.getTopicName().equals(topicName))
                .findFirst();
    }

    public Optional<Topic> getByEntity(Topic entity) {
        return storage.stream()
                .filter(topic -> topic.equals(entity))
                .findAny();
    }
}
