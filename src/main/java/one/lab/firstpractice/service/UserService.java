package one.lab.firstpractice.service;

import one.lab.firstpractice.model.dto.UserDTO;
import one.lab.firstpractice.model.entity.User;

import java.util.List;

public interface UserService {

    User createNewUser(UserDTO dto);

    List<User> fetchAll();

    User fetchById(Long id);

    void initUsers();

    User fetchByUsername(String username);

}
