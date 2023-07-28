package one.lab.firstpractice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ErrorResponse extends BaseResponse{

    @JsonProperty("error_message")
    private String message;

}
