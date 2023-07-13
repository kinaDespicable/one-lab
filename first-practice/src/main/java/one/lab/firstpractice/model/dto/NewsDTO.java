package one.lab.firstpractice.model.dto;

import lombok.Builder;
import lombok.Data;
import one.lab.firstpractice.model.entity.Topic;
import one.lab.firstpractice.model.entity.User;

import java.time.LocalDate;

@Data
@Builder
public class NewsDTO {

    private String title;
    private String content;
    private String topic;
    private String author;

}
