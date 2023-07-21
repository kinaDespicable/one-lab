package one.lab.firstpractice.repository;

import one.lab.firstpractice.model.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query(value = "SELECT * FROM topics WHERE topic_name = ?", nativeQuery = true)
    Optional<Topic> findByTopicName(String topicName);

}
