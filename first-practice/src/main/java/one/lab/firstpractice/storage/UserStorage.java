package one.lab.firstpractice.storage;

import one.lab.firstpractice.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public final class UserStorage {

    public static Long USER_SEQUENCE = 1L;

    private final List<User> storageUsers = new ArrayList<>();

    public void addUserToStorage(User user) {
        storageUsers.add(user);
    }

    public Optional<User> getById(Long id) {
        return storageUsers.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> getByEntity(User entity) {
        return storageUsers.stream()
                .filter(user -> user.equals(entity))
                .findFirst();
    }

    public List<User> getStorageUsers() {
        return storageUsers;
    }

    public boolean existsByUsername(String username) {
        return storageUsers.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
