package one.lab.firstpractice.service.implementation;

import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAll() {
        // Arrange
        User user1 = new User(1L, "johnDoe", "John", "Doe", true, true, new ArrayList<>());
        User user2 = new User(2L, "janeDoe", "Jane", "Doe", false, true, new ArrayList<>());
        List<User> mockUsers = Arrays.asList(user1, user2);

        // Act
        when(userRepository.findAll()).thenReturn(mockUsers);
        List<User> users = userServiceImpl.fetchAll();

        // Assert
        assertAll(
                () -> assertThat(users).isNotNull(),
                () -> assertThat(users).hasSize(2),
                () -> assertThat(users).containsExactly(user1, user2)
        );
    }

    @Test
    void testFetchById_UserFound() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User(userId, "johnDoe", "John", "Doe", true, true, new ArrayList<>());
        Optional<User> optionalUser = Optional.of(mockUser);

        // Act
        when(userRepository.findById(userId)).thenReturn(optionalUser);
        User user = userServiceImpl.fetchById(userId);

        // Assert
        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isEqualTo(mockUser)
        );
    }

    @Test
    void testFetchById_UserNotFound() {
        // Arrange
        Long userId = 2L;
        Optional<User> emptyOptional = Optional.empty();

        // Act
        when(userRepository.findById(userId)).thenReturn(emptyOptional);
        User user = userServiceImpl.fetchById(userId);

        // Assert
        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isEqualTo(new User())
        );
    }

    @Test
    void testFetchByUsername_UserFound() {
        // Arrange
        final String username = "johnDoe";
        User mockUser = new User(1L, username, "John", "Doe", true, true, new ArrayList<>());
        Optional<User> optionalUser = Optional.of(new User(1L, username, "John", "Doe", true, true, new ArrayList<>()));

        // Mocking userRepository.findOne() behavior
        when(userRepository.findOne(any(Example.class))).thenReturn(optionalUser);

        // Call the service method
        User user = userServiceImpl.fetchByUsername(username);

        // Assertions
        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isEqualTo(mockUser)
        );
    }

    @Test
    void testFetchByUsername_UserNotFound() {
        // Assert
        String username = "user_not_found";
        Optional<User> emptyOptional = Optional.empty();
        Example<User> example = Example.of(new User(1L, username, "John", "Doe", true, true, new ArrayList<>()));

        // Act
        when(userRepository.findOne(any(Example.class))).thenReturn(emptyOptional);
        User user = userServiceImpl.fetchByUsername(username);

        // Assert
        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user).isEqualTo(new User())
        );


    }

    @Test
    void testFetchAllAdmins() {
        // Arrange
        List<User> mockAdminUsers = List.of(
                new User(1L, "Admin1", "John", "Doe", true, false, new ArrayList<>()),
                new User(2L, "Admin2", "Jane", "Doe", true, false, new ArrayList<>()),
                new User(3L, "Admin3", "Adam", "Smith", true, false, new ArrayList<>())
        );
        Page<User> mockedPage = new PageImpl<>(mockAdminUsers);

        // Act
        when(userRepository.findUsersByAdminIsTrue(any(Pageable.class))).thenReturn(mockedPage);
        Page<User> adminsPage = userServiceImpl.fetchAllAdmins();

        // Assertions
        assertThat(adminsPage).isNotNull();
        assertThat(adminsPage.getContent()).hasSize(3);
        assertThat(adminsPage.getContent()).containsExactlyElementsOf(mockAdminUsers);
    }

    @Test
    void testFetchAllAuthors() {
        // Arrange
        List<User> mockAuthorUsers = List.of(
                new User(1L, "Admin1", "John", "Doe", false, true, new ArrayList<>()),
                new User(2L, "Admin2", "Jane", "Doe", false, true, new ArrayList<>()),
                new User(3L, "Admin3", "Adam", "Smith", false, true, new ArrayList<>())
        );
        Page<User> mockedPage = new PageImpl<>(mockAuthorUsers);

        // Act
        when(userRepository.findUsersByAuthorIsTrue(any(Pageable.class))).thenReturn(mockedPage);
        Page<User> authorsPage = userServiceImpl.fetchAllAuthors();

        // Assert
        assertThat(authorsPage).isNotNull();
        assertThat(authorsPage.getContent()).hasSize(3);
        assertThat(authorsPage.getContent()).containsExactlyElementsOf(mockAuthorUsers);
    }
}
