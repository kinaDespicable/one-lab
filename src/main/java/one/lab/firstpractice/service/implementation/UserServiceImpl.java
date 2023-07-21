package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.lab.firstpractice.custom.annotation.Loggable;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.repository.UserRepository;
import one.lab.firstpractice.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static one.lab.firstpractice.utils.Page.getPageable;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final String USER_SERVICE_PREFIX = "[USER SERVICE] --- ";

    private final UserRepository userRepository;

    @Override
    @Loggable
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
    @Loggable
    @Transactional(propagation = Propagation.NEVER)
    public User fetchByUsername(String username) {
        User userExample = new User();
        userExample.setUsername(username);

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "firstName", "lastName", "admin", "author");
        Example<User> example = Example.of(userExample, exampleMatcher);

        Optional<User> optional = userRepository.findOne(example);
        log.warn("QueryByExampleExecutor is has been used '{}.fetchByUsername'", getClass().getSimpleName());
        if (optional.isEmpty()) {
            log.error(USER_SERVICE_PREFIX + "User retrieval failed. User with username: " + username + " not found.");
        }
        return optional.orElse(new User());
    }

    @Override
    public Page<User> fetchAllAdmins() {
        Pageable pageable = getPageable();
        return userRepository.findUsersByAdminIsTrue(pageable);
    }

    @Override
    public Page<User> fetchAllAuthors() {
        Pageable pageable = getPageable();
        return userRepository.findUsersByAuthorIsTrue(pageable);
    }

}
