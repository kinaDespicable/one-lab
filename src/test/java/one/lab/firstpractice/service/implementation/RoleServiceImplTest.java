package one.lab.firstpractice.service.implementation;

import one.lab.firstpractice.exception.exceptions.ResourceNotFoundException;
import one.lab.firstpractice.model.entity.Role;
import one.lab.firstpractice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoleServiceImplTest {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String AUTHOR_ROLE = "ROLE_AUTHOR";

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    @Test
    void testFetchAdminRole_Successful() {
        Role mockAdminRole = new Role(1L, "ROLE_ADMIN", new HashSet<>());
        mockAdminRole.setRoleName(ADMIN_ROLE);

        when(roleRepository.findRoleByRoleName(ADMIN_ROLE)).thenReturn(Optional.of(mockAdminRole));

        Role fetchedRole = roleServiceImpl.fetchAdminRole();

        verify(roleRepository, times(1)).findRoleByRoleName(ADMIN_ROLE);

        assertNotNull(fetchedRole);
        assertEquals(ADMIN_ROLE, fetchedRole.getRoleName());
    }

    @Test
    void testFetchAdminRole_RoleNotFound() {
        when(roleRepository.findRoleByRoleName(ADMIN_ROLE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleServiceImpl.fetchAdminRole());

        verify(roleRepository, times(1)).findRoleByRoleName(ADMIN_ROLE);
    }

    @Test
    void testFetchAuthorRole_Successful() {
        Role mockAuthorRole = new Role(1L, "ROLE_AUTHOR", new HashSet<>());
        mockAuthorRole.setRoleName(AUTHOR_ROLE);

        when(roleRepository.findRoleByRoleName(AUTHOR_ROLE)).thenReturn(Optional.of(mockAuthorRole));

        Role fetchedRole = roleServiceImpl.fetchAuthorRole();

        verify(roleRepository, times(1)).findRoleByRoleName(AUTHOR_ROLE);

        assertNotNull(fetchedRole);
        assertEquals(AUTHOR_ROLE, fetchedRole.getRoleName());
    }

    @Test
    void testFetchAuthorRole_RoleNotFound() {
        when(roleRepository.findRoleByRoleName(AUTHOR_ROLE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleServiceImpl.fetchAuthorRole());

        verify(roleRepository, times(1)).findRoleByRoleName(AUTHOR_ROLE);
    }

    @Test
    void testFetchDefaultRole_Successful() {
        Role mockDefaultRole = new Role(1L, "ROLE_USER", new HashSet<>());

        mockDefaultRole.setRoleName(DEFAULT_ROLE);

        when(roleRepository.findRoleByRoleName(DEFAULT_ROLE)).thenReturn(Optional.of(mockDefaultRole));

        Role fetchedDefaultRole = roleServiceImpl.fetchDefaultRole();

        verify(roleRepository, times(1)).findRoleByRoleName(DEFAULT_ROLE);

        assertNotNull(fetchedDefaultRole);
        assertEquals(DEFAULT_ROLE, fetchedDefaultRole.getRoleName());
    }

    @Test
    void testFetchDefaultRole_RoleNotFound() {
        when(roleRepository.findRoleByRoleName(DEFAULT_ROLE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleServiceImpl.fetchDefaultRole());

        verify(roleRepository, times(1)).findRoleByRoleName(DEFAULT_ROLE);
    }


}

