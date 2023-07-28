package one.lab.firstpractice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsRequest {

    @JsonProperty("title")
    @NotBlank(message = "Title can not be blank.")
    private String title;

    @JsonProperty("content")
    @NotBlank(message = "Title can not be blank.")
    private String content;

    @JsonProperty("topic")
    @NotBlank(message = "Title can not be blank.")
    private String topic;

}
