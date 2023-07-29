package one.lab.firstpractice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest implements CreateRequest {

    @JsonProperty("username")
    @NotBlank(message = "Username must not be blank.")
    @Size(min = 2, max = 32, message = "Username must be between 2 and 32 characters long.")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Password must not be blank.")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters long.")
    private String password;

    @JsonProperty("password_confirmation")
    @NotBlank(message = "Password confirmation must not be blank.")
    @Size(min = 8, max = 32, message = "Password confirmation must be between 8 and 32 characters long.")
    private String passwordConfirmation;

    @JsonProperty("first_name")
    @NotBlank(message = "First name must not be blank.")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name must not be blank.")
    private String lastName;

}
