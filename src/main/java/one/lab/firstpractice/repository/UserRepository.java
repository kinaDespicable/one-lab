package one.lab.firstpractice.repository;

import one.lab.firstpractice.model.entity.Role;
import one.lab.firstpractice.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE USERNAME = ?", nativeQuery = true)
    Optional<User> findByUsername(String username);


    Page<User> findAllByRolesIn(Pageable pageable, Role... roles);

    boolean existsByUsernameEqualsIgnoreCase(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM User u JOIN u.roles r " +
            "WHERE r.roleName = :roleName AND u.username = :username")
    boolean hasRole(@Param("username") String username, @Param("roleName") String roleName);


}
