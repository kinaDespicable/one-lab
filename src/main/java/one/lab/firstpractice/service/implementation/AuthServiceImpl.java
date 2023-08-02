package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.config.jwt.JwtService;
import one.lab.firstpractice.exception.exceptions.EmptyAttributeValueException;
import one.lab.firstpractice.exception.exceptions.PasswordMismatchException;
import one.lab.firstpractice.exception.exceptions.ResourceAlreadyExistException;
import one.lab.firstpractice.model.dto.request.LoginRequest;
import one.lab.firstpractice.model.dto.request.RegistrationRequest;
import one.lab.firstpractice.model.dto.request.Validatable;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.auth.LoginResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.UserRepository;
import one.lab.firstpractice.service.AuthService;
import one.lab.firstpractice.service.RoleService;
import one.lab.firstpractice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, Validatable<RegistrationRequest> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public CreatedResponse register(RegistrationRequest request) {
        validate(request);

        var entity = User.builder()
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .username(request.getUsername().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roleService.fetchDefaultRole()))
                .build();
        userRepository.save(entity);

        User savedUser = userService.fetchByUsername(request.getUsername().trim());
        UserResponse userResponse = UserResponse.mapToResponse(savedUser);

        return CreatedResponse.builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .createdObject(userResponse)
                .build();
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = jwtService.generateToken(Map.of("roles", roles), user);

        return LoginResponse.builder()
                .jwtToken(token)
                .build();
    }


    @Override
    public void checkExistenceForCreation(RegistrationRequest request) throws ResourceAlreadyExistException {
        var username = request.getUsername().trim();

        if (username.equals(EMPTY_STRING)) {
            throw new EmptyAttributeValueException("Username can not be empty.");
        }

        if (userRepository.existsByUsernameEqualsIgnoreCase(username)) {
            throw new ResourceAlreadyExistException("Username already taken.");
        }
    }

    private void validate(RegistrationRequest request) {
        checkExistenceForCreation(request);
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw new PasswordMismatchException("Password does not match.");
        }

    }
}

