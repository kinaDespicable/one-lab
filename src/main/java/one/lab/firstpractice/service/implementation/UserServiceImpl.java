package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.custom.annotation.Loggable;
import one.lab.firstpractice.custom.annotation.Timed;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.UserRepository;
import one.lab.firstpractice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_SERVICE_PREFIX = "[USER SERVICE] --- ";

    private final UserRepository userRepository;

    @Timed
    @Loggable
    @Override
    public List<User> fetchAll() {
        return userRepository.findAll();
    }

    @Override
    public User fetchById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            log.error(USER_SERVICE_PREFIX + "User retrieval failed. User with id: " + id + " not found.");
        }
        return optional.orElse(new User());
    }

    @Override
    public User fetchByUsername(String username) {
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isEmpty()) {
            log.error(USER_SERVICE_PREFIX + "User retrieval failed. User with id: " + username + " not found.");
        }
        return optional.orElse(new User());
    }

}
