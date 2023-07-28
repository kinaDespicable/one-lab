package one.lab.firstpractice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    @JsonProperty("jwt_token")
    private String jwtToken;

}
