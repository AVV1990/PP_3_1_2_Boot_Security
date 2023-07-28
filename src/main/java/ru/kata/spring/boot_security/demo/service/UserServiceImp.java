package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new RuntimeException("ID can not be null");
        } else {
            return userRepository.findById(id).get();
        }
    }

    @Override
    public void updateUser(Long id, User user) {

        User userAfterUpdate = getUserById(id);
        if (userAfterUpdate != null) {
            userAfterUpdate.setMail(user.getMail());
            userAfterUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            userAfterUpdate.setAge(user.getAge());
            userAfterUpdate.setFirstName(user.getFirstName());
            userAfterUpdate.setLastName(user.getLastName());
            userAfterUpdate.setRoles(user.getRoles());

            userRepository.save(userAfterUpdate);
        } else {
            throw new UsernameNotFoundException("User isn't found");
        }

    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
