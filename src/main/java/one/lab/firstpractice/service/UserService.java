package one.lab.firstpractice.service;

import one.lab.firstpractice.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<User> fetchAll();

    User fetchById(Long id);

    User fetchByUsername(String username);

    Page<User> fetchAllAdmins();

    Page<User> fetchAllAuthors();

}
