package one.lab.firstpractice.service.implementation;

import one.lab.firstpractice.exception.exceptions.NoAuthorityException;
import one.lab.firstpractice.exception.exceptions.PasswordMismatchException;
import one.lab.firstpractice.exception.exceptions.ResourceNotFoundException;
import one.lab.firstpractice.model.dto.request.CreateUserRequest;
import one.lab.firstpractice.model.dto.response.CreatedResponse;
import one.lab.firstpractice.model.dto.response.user.UserResponse;
import one.lab.firstpractice.model.entity.Role;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.UserRepository;
import one.lab.firstpractice.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser_SuccessfulCreation() {
        String adminUsername = "adminuser";
        CreateUserRequest createUserRequest = new CreateUserRequest("johndoe", "password123", "password123", "John", "Doe",
                List.of("ROLE_USER"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(adminUsername, null);

        Role roleUser = new Role(1L, "ROLE_USER", new HashSet<>());
        User user = new User(1L, "John", "Doe", "johndoe", "password", List.of(roleUser), new ArrayList<>());

        when(userRepository.hasRole(adminUsername, "ROLE_ADMIN")).thenReturn(true);
        when(roleService.fetchByRoleName("ROLE_USER")).thenReturn(roleUser);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        CreatedResponse result = userService.createUser(authentication, createUserRequest);

        verify(userRepository).hasRole(adminUsername, "ROLE_ADMIN");
        verify(roleService).fetchByRoleName("ROLE_USER");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatus());
        assertEquals(HttpStatus.CREATED.value(), result.getStatusCode());
        assertNotNull(result.getCreatedObject());
    }

    @Test
    void testCreateUser_UnauthorizedAccess() {
        String adminUsername = "adminuser";
        CreateUserRequest createUserRequest = new CreateUserRequest("johndoe", "password123", "password123", "John", "Doe",
                List.of("ROLE_USER"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(adminUsername, null);

        when(userRepository.hasRole(adminUsername, "ROLE_ADMIN")).thenReturn(false);

        NoAuthorityException exception = assertThrows(NoAuthorityException.class, () -> {
            userService.createUser(authentication, createUserRequest);
        });

        assertEquals("Unauthorized access to the resource.", exception.getMessage());

        verify(userRepository).hasRole(adminUsername, "ROLE_ADMIN");

        verifyNoMoreInteractions(roleService, userRepository);
    }

    @Test
    void testCreateUser_PasswordMismatch() {
        String adminUsername = "adminuser";
        CreateUserRequest createUserRequest = new CreateUserRequest("johndoe", "password123", "password456", "John", "Doe",
                List.of("ROLE_USER"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(adminUsername, null);

        when(userRepository.hasRole(adminUsername, "ROLE_ADMIN")).thenReturn(true);

        PasswordMismatchException exception = assertThrows(PasswordMismatchException.class, () -> {
            userService.createUser(authentication, createUserRequest);
        });

        assertEquals("Passwords don't match.", exception.getMessage());

        verify(userRepository).hasRole(adminUsername, "ROLE_ADMIN");
    }

    @Test
    void testFetchAll() {
        List<User> users = List.of(
                new User(1L, "John", "Doe", "johndoe", "password", new ArrayList<>(), new ArrayList<>()),
                new User(2L, "Jane", "Smith", "janesmith", "password", new ArrayList<>(), new ArrayList<>())
        );
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id"));
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        Page<UserResponse> result = userService.fetchAll(
                Optional.of(0),
                Optional.of(5),
                Optional.of("id")
        );

        verify(userRepository).findAll(pageable);

        assertEquals(userPage.getTotalElements(), result.getTotalElements());
        assertEquals(users.size(), result.getContent().size());
    }

    @Test
    void testFetchById_UserFound() {
        Long userId = 1L;
        User user = new User(1L, "John", "Doe", "johndoe", "password", new ArrayList<>(), new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse result = userService.fetchById(userId);

        verify(userRepository).findById(userId);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void testFetchById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.fetchById(userId);
        });

        assertEquals("User with id: " + userId + " not found", exception.getMessage());
    }

    @Test
    void testFetchByUsername_UserFound() {
        String username = "johndoe";
        User user = new User(1L, "John", "Doe", username, "password", new ArrayList<>(), new ArrayList<>());

        when(userRepository.findByUsername(username.trim())).thenReturn(Optional.of(user));

        User result = userService.fetchByUsername(username);

        verify(userRepository).findByUsername(username.trim());

        assertEquals(user, result);
    }

    @Test
    void testFetchByUsername_UserNotFound() {
        String username = "unknownuser";

        when(userRepository.findByUsername(username.trim())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.fetchByUsername(username);
        });

        assertEquals("Username [" + username + "] not found.", exception.getMessage());
    }

    @Test
    void testFetchCurrentUser_UserFound() {
        String username = "johndoe";
        User user = new User(1L, "John", "Doe", username, "password", new ArrayList<>(), new ArrayList<>());

        when(userRepository.findByUsername(username.trim())).thenReturn(Optional.of(user));

        UserResponse result = userService.fetchCurrentUser(username);

        verify(userRepository).findByUsername(username.trim());

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void testFetchCurrentUser_UserNotFound() {
        String username = "unknownuser";

        when(userRepository.findByUsername(username.trim())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.fetchCurrentUser(username);
        });

        assertEquals("Could not find username: " + username, exception.getMessage());
    }

    @Test
    void testFetchAllAdmins() {
        Role roleAdmin = new Role(1L, "ADMIN", new HashSet<>());
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id"));

        User user1 = new User(1L, "John", "Doe", "johndoe", "password", new ArrayList<>(), new ArrayList<>());
        User user2 = new User(1L, "Jane", "Smith", "janesmith", "password", new ArrayList<>(), new ArrayList<>());
        List<User> userList = List.of(user1, user2);
        Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());

        when(roleService.fetchAdminRole()).thenReturn(roleAdmin);

        when(userRepository.findAllByRolesIn(pageable, roleAdmin)).thenReturn(userPage);

        Page<UserResponse> result = userService.fetchAllAdmins();

        verify(roleService).fetchAdminRole();
        verify(userRepository).findAllByRolesIn(pageable, roleAdmin);

        assertEquals(userList.size(), result.getContent().size());
    }

    @Test
    void testFetchAllAuthors() {
        Role roleAuthor = new Role(1L, "AUTHOR", new HashSet<>());
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id"));

        User user1 = new User(1L, "John", "Doe", "johndoe", "password", new ArrayList<>(), new ArrayList<>());
        User user2 = new User(1L, "Jane", "Smith", "janesmith", "password", new ArrayList<>(), new ArrayList<>());
        List<User> userList = List.of(user1, user2);
        Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());

        when(roleService.fetchAuthorRole()).thenReturn(roleAuthor);

        when(userRepository.findAllByRolesIn(pageable, roleAuthor)).thenReturn(userPage);

        Page<UserResponse> result = userService.fetchAllAuthors();

        verify(roleService).fetchAuthorRole();

        verify(userRepository).findAllByRolesIn(pageable, roleAuthor);

        assertEquals(userList.size(), result.getContent().size());
    }

    @Test
    void testIsAuthor_UserIsAuthor() {
        String username = "johndoe";

        when(userRepository.hasRole(username.trim(), "ROLE_AUTHOR")).thenReturn(true);

        boolean result = userService.isAuthor(username);

        verify(userRepository).hasRole(username.trim(), "ROLE_AUTHOR");

        assertTrue(result);
    }

    @Test
    void testIsAuthor_UserIsNotAuthor() {
        String username = "janedoe";

        when(userRepository.hasRole(username.trim(), "ROLE_AUTHOR")).thenReturn(false);

        boolean result = userService.isAuthor(username);

        verify(userRepository).hasRole(username.trim(), "ROLE_AUTHOR");

        assertFalse(result);
    }

}

