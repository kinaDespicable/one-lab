package one.lab.firstpractice.service;

import one.lab.firstpractice.model.entity.User;

import java.util.List;

public interface UserService {

    List<User> fetchAll();

    User fetchById(Long id);

    User fetchByUsername(String username);

}
