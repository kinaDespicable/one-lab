package one.lab.firstpractice.service;

import one.lab.firstpractice.model.entity.Role;

import java.util.List;

public interface RoleService {

    Role fetchAdminRole();

    Role fetchAuthorRole();

    Role fetchDefaultRole();

    Role fetchByRoleName(String roleName);

}
