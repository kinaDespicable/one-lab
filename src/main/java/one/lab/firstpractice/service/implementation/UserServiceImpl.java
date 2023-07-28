package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.exception.exceptions.NoAuthorityException;
import one.lab.firstpractice.exception.exceptions.PasswordMismatchException;
import one.lab.firstpractice.exception.exceptions.ResourceAlreadyExistException;
import one.lab.firstpractice.exception.exceptions.ResourceNotFoundException;
import one.lab.firstpractice.model.dto.request.CreateUserRequest;
import one.lab.firstpractice.model.dto.request.Validatable;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.model.entity.Role;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.UserRepository;
import one.lab.firstpractice.service.RoleService;
import one.lab.firstpractice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static one.lab.firstpractice.utils.Page.getPageable;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, Validatable<CreateUserRequest> {

    private static final String ROLE_AUTHOR = "ROLE_AUTHOR";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreatedResponse createUser(Authentication authentication, CreateUserRequest createUserRequest) {
        validateRegistrationRequest(authentication, createUserRequest);

        List<Role> roles = createUserRequest.getRoles()
                .stream()
                .map(roleService::fetchByRoleName)
                .toList();

        User user = User.builder()
                .username(createUserRequest.getUsername().trim())
                .firstName(createUserRequest.getFirstName().trim())
                .lastName(createUserRequest.getLastName().trim())
                .roles(roles)
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        return CreatedResponse.builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .data(UserResponse.mapToResponse(savedUser))
                .build();
    }

    @Override
    public Page<UserResponse> fetchAll(Optional<Integer> pageOptional,
                                       Optional<Integer> sizeOptional,
                                       Optional<String> sortOptional) {
        Page<User> userPage = userRepository.findAll(getPageable(pageOptional, sizeOptional, sortOptional));

        List<UserResponse> responseList = userPage.getContent()
                .stream()
                .map(UserResponse::mapToResponse)
                .toList();

        return new PageImpl<>(responseList, userPage.getPageable(), userPage.getTotalPages());
    }

    @Override
    public UserResponse fetchById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + "not found"));
        return UserResponse.mapToResponse(user);
    }

    @Override
    public User fetchByUsername(String username) {
        return userRepository.findByUsername(username.trim())
                .orElseThrow(() -> new ResourceNotFoundException("Username [" + username.trim() + "] not found."));
    }

    @Override
    public UserResponse fetchCurrentUser(String username) {
        User currentUser = userRepository.findByUsername(username.trim())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find username: " + username));
        return UserResponse.mapToResponse(currentUser);
    }

    @Override
    public Page<UserResponse> fetchAllAdmins() {
        Role roleAdmin = roleService.fetchAdminRole();
        Page<User> userPage = userRepository.findAllByRolesIn(getPageable(), roleAdmin);
        return mapToUserResponse(userPage);
    }

    @Override
    public Page<UserResponse> fetchAllAuthors() {
        Role roleAuthor = roleService.fetchAuthorRole();
        Page<User> userPage = userRepository.findAllByRolesIn(getPageable(), roleAuthor);
        return mapToUserResponse(userPage);
    }

    @Override
    public Boolean isAuthor(String username) {
        return userRepository.hasRole(username.trim(), ROLE_AUTHOR);
    }


    private Page<UserResponse> mapToUserResponse(Page<User> userPage) {
        List<UserResponse> responseList = userPage.getContent()
                .stream()
                .map(UserResponse::mapToResponse)
                .toList();
        return new PageImpl<>(responseList, getPageable(), userPage.getTotalPages());
    }

    @Override
    public void checkExistenceForCreation(CreateUserRequest request) throws ResourceAlreadyExistException {
        if (userRepository.existsByUsernameEqualsIgnoreCase(request.getUsername())) {
            throw new ResourceAlreadyExistException("Username already taken.");
        }
    }

    private void validateRegistrationRequest(Authentication authentication, CreateUserRequest request) {
        String username = (String) authentication.getPrincipal();

        if (!userRepository.hasRole(username, ROLE_ADMIN)) {
            throw new NoAuthorityException("Unauthorized access to the resource.");
        }

        checkExistenceForCreation(request);

        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw new PasswordMismatchException("Passwords don't match.");
        }
    }
}
