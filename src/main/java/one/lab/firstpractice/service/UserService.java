package one.lab.firstpractice.service;

import one.lab.firstpractice.model.dto.request.CreateUserRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {

    Page<UserResponse> fetchAll(Optional<Integer> pageOptional,
                                Optional<Integer> sizeOptional,
                                Optional<String> sortOptional);

    UserResponse fetchById(Long id);

    User fetchByUsername(String username);

    UserResponse fetchCurrentUser(String username);

    Page<UserResponse> fetchAllAdmins();

    Page<UserResponse> fetchAllAuthors();

    Boolean isAuthor(String username);

    CreatedResponse createUser(Authentication authentication, CreateUserRequest createUserRequest);
}
