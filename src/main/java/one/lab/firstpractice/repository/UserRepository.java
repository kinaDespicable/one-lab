package one.lab.firstpractice.repository;

import one.lab.firstpractice.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE USERNAME = ?", nativeQuery = true)
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE admin = true", nativeQuery = true)
    Page<User> findUsersByAdminIsTrue(Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE author = true", nativeQuery = true)
    Page<User> findUsersByAuthorIsTrue(Pageable pageable);

}
