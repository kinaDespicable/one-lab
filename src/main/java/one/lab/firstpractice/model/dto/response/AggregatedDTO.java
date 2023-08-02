package one.lab.firstpractice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedDTO {

    private UserResponse userByIdResponse;
    private Page<UserResponse> adminsResponse;
    private Page<UserResponse> authorsIdResponse;

}