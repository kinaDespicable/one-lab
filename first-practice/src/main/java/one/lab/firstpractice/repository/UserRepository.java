package one.lab.firstpractice.repository;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.model.entity.User;
import one.lab.firstpractice.storage.UserStorage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserStorage userStorage;

    public User save(User entity) {
        userStorage.addUserToStorage(entity);
        return userStorage.getByEntity(entity)
                .orElseThrow(()-> new RuntimeException("Something went wrong on saving stage"));
    }

    public Optional<User> findById(Long id) {
        return userStorage.getById(id);
    }

    public boolean existsByUsername(String username) {
        return userStorage.existsByUsername(username);
    }

    public List<User> findAll() {
        return userStorage.getStorageUsers();
    }

}
