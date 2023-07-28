package one.lab.firstpractice.service.implementation;

import lombok.RequiredArgsConstructor;
import one.lab.firstpractice.exception.exceptions.ResourceNotFoundException;
import one.lab.firstpractice.model.entity.Role;
import one.lab.firstpractice.repository.RoleRepository;
import one.lab.firstpractice.service.RoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String AUTHOR_ROLE = "ROLE_AUTHOR";

    private final RoleRepository roleRepository;

    @Override
    public Role fetchAdminRole() {
        return roleRepository.findRoleByRoleName(ADMIN_ROLE)
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage(ADMIN_ROLE)));
    }

    @Override
    public Role fetchAuthorRole() {
        return roleRepository.findRoleByRoleName(AUTHOR_ROLE)
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage(AUTHOR_ROLE)));
    }

    @Override
    public Role fetchDefaultRole() {
        return roleRepository.findRoleByRoleName(DEFAULT_ROLE)
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage(DEFAULT_ROLE)));
    }

    @Override
    public Role fetchByRoleName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName.trim())
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage(roleName.trim())));
    }

    private String notFoundMessage(String role) {
        return "Role [" + role + "] not found.";
    }

}
