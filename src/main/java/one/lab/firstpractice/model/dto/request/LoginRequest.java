package one.lab.firstpractice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @JsonProperty("username")
    @NotBlank(message = "Username must not be blank")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Password must not be blank")
    private String password;
}
