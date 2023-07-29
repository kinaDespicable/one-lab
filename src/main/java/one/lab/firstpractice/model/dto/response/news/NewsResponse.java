package one.lab.firstpractice.model.dto.response.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.model.entity.News;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("likes")
    private long likes;

    @JsonProperty("published_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING ,
            pattern = "dd-MM-YYYY hh:mm:ss" ,
            timezone="UTC")
    private LocalDateTime publishedAt;

    @JsonProperty("author")
    private UserResponse author;

    @JsonProperty("topic")
    private String topic;

    public static NewsResponse mapToResponse(News entity) {
        return NewsResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .likes(entity.getLikes())
                .publishedAt(entity.getPublishedAt())
                .author(UserResponse.mapToResponse(entity.getAuthor()))
                .topic(entity.getTopic().getTopicName())
                .build();
    }

}
