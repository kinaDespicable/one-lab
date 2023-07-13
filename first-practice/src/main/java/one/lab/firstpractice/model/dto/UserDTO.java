package one.lab.firstpractice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private Boolean admin;
    private Boolean author;

}
