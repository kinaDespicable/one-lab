package one.lab.firstpractice.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NEWS")
public class News implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "LIKES")
    private long likes;

    @Column(name = "PUBLISHED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING ,
            pattern = "dd-MM-YYYY hh:mm:ss" ,
            timezone="UTC")
    private LocalDateTime publishedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    private Topic topic;

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
