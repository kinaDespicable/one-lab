package one.lab.firstpractice.model.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class News {

    private Long id;
    private String title;
    private String content;
    private long likes;
    private LocalDate publishedAt;
    private User author; // Many-to-one
    private Topic topic; // Many-to-one

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News news)) return false;
        return Objects.equals(id, news.id) &&
                Objects.equals(title, news.title) &&
                Objects.equals(content, news.content) &&
                Objects.equals(publishedAt, news.publishedAt) &&
                Objects.equals(author, news.author) &&
                Objects.equals(topic, news.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, publishedAt, author, topic);
    }
}
