package one.lab.firstpractice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("status_code")
    private int statusCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING ,
            pattern = "dd-MM-YYYY hh:mm:ss" ,
            timezone="UTC")
    private LocalDateTime timestamp;

}
