package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.exception.ResourceAlreadyExistsException;
import one.lab.firstpractice.exception.ResourceNotFoundException;
import one.lab.firstpractice.model.dto.UserDTO;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.UserRepository;
import one.lab.firstpractice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static one.lab.firstpractice.storage.UserStorage.USER_SEQUENCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_SERVICE_PREFIX = "[USER SERVICE] --- ";

    private final UserRepository userRepository;

    @Override
    public User createNewUser(UserDTO dto) {
        final String username = dto.getUsername().toLowerCase();
        checkUserExistence(username);

        User entity = User.builder()
                .id(USER_SEQUENCE)
                .username(username)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .admin(dto.getAdmin())
                .author(dto.getAuthor())
                .build();

        log.info(USER_SERVICE_PREFIX + "User with id: [" + USER_SEQUENCE + "] was created.");
        USER_SEQUENCE++;

        return userRepository.save(entity);
    }

    @Override
    public List<User> fetchAll() {
        log.info(USER_SERVICE_PREFIX + "Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User fetchById(Long id) {
        log.info(USER_SERVICE_PREFIX + "Fetching by ID.");
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(USER_SERVICE_PREFIX + "Fetching by ID process has been failed.");
                    return new ResourceNotFoundException("User with id:[" + id + "] not found.");
                });
    }

    private void checkUserExistence(String username) {
        if (userRepository.existsByUsername(username)) {
            log.error("[USER SERVICE] User creation was failed. Resource already exists.");
            throw new ResourceAlreadyExistsException("Username has already been taken.");
        }
    }

    public void initUsers() {
        for (int i = 0; i < 5; i++) {

            boolean admin = i % 5 == 0;
            boolean author = i % 2 == 0;

            UserDTO dto = UserDTO.builder()
                    .username("Username " + i)
                    .firstName("Name " + i)
                    .lastName("Surname " + i)
                    .admin(admin)
                    .author(author)
                    .build();
            createNewUser(dto);
        }
    }

}
