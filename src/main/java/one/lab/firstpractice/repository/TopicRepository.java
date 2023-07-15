package one.lab.firstpractice.repository;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.exception.ResourceNotFoundException;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.storage.TopicStorage;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TopicRepository {

    private final TopicStorage topicStorage;

    public Topic save(Topic entity) {
        topicStorage.addTopicToStorage(entity);
        return topicStorage.getByEntity(entity)
                .orElseThrow(() -> new ResourceNotFoundException("No topic found."));
    }

    public Optional<Topic> findById(Long id) {
        return topicStorage.getById(id);
    }

    public Optional<Topic> findByTopicName(String topicName) {
        return topicStorage.getByTopicName(topicName);
    }


}
