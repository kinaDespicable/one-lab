package one.lab.firstpractice.service.implementation;

import one.lab.firstpractice.config.jwt.JwtService;
import one.lab.firstpractice.config.security.SecurityUserAdapter;
import one.lab.firstpractice.model.dto.request.LoginRequest;
import one.lab.firstpractice.model.dto.request.RegistrationRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.LoginResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.model.entity.Role;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.UserRepository;
import one.lab.firstpractice.service.RoleService;
import one.lab.firstpractice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @Test
    void testRegister_Successful() {
        RegistrationRequest request = RegistrationRequest.builder()
                .username("johnDoe")
                .password("password")
                .passwordConfirmation("password")
                .firstName("John")
                .lastName("Doe")
                .build();
        Role defaultRole = new Role(1L, "ROLE_USER", new HashSet<>());
        User savedUser = new User(
                1L,
                "johnDoe",
                "John",
                "Doe",
                "encodedPassword",
                Collections.singletonList(defaultRole),
                new ArrayList<>());
        UserResponse expectedResponse = UserResponse.builder()
                .id(1L)
                .username("johnDoe")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(roleService.fetchDefaultRole()).thenReturn(defaultRole);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userService.fetchByUsername("johnDoe")).thenReturn(savedUser);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);


        CreatedResponse response = authServiceImpl.register(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertNotNull(response.getTimestamp());
        assertEquals(expectedResponse, response.getCreatedObject());

        verify(roleService, times(1)).fetchDefaultRole();
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticate_Successful() {
        String username = "johnDoe";
        String password = "somePassword";
        User user = new User(
                1L,
                "johnDoe",
                "John",
                "Doe",
                "encodedPassword",
                Collections.singletonList(new Role(1L, "ROLE_ADMIN", new HashSet<>())),
                new ArrayList<>());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        UserDetails userDetails = new SecurityUserAdapter(user);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.generateToken(any(Map.class), eq(userDetails))).thenReturn("sampleJwtToken");

        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginResponse response = authServiceImpl.authenticate(loginRequest);

        assertNotNull(response);
        assertEquals("sampleJwtToken", response.getJwtToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtService, times(1)).generateToken(any(Map.class), eq(userDetails));
    }

}

