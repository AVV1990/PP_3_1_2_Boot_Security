package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {

    User findByUsername(String username);

    List<User> getUsers();

    void addUser(User user);

    User getUserById(Long id);

    void updateUser(Long id, User user);


    void deleteUserById(Long id);

}
