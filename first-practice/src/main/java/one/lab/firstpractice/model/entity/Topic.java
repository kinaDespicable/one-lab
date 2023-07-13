package one.lab.firstpractice.model.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    private Long id;
    private String topicName;
    private List<News> newsList = new ArrayList<>(); // One-to-Many

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic topic)) return false;
        return Objects.equals(id, topic.id) && Objects.equals(topicName, topic.topicName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topicName);
    }
}
