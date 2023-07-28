package one.lab.firstpractice.repository;

import one.lab.firstpractice.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByRoleName(String roleName);

}
