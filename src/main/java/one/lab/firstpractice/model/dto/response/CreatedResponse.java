package one.lab.firstpractice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreatedResponse extends BaseResponse {

    @JsonProperty("created_object")
    private Object createdObject;

    public CreatedResponse(HttpStatus httpStatus, int value, LocalDateTime now, Object response) {
        super(httpStatus, value, now);
        this.createdObject = response;
    }
}
