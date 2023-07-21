package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImp implements UserDetailsService, UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            throw new RuntimeException("ID can not be null");
        } else {
            return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User isn't found"));
        }
    }

    @Override
    public void updateUser(Long id, User user) {

        User userAfterUpdate = getUserById(id);
        if (userAfterUpdate != null) {
            userAfterUpdate.setUsername(user.getUsername());
            userAfterUpdate.setPassword(user.getPassword());
            userAfterUpdate.setAge(user.getAge());
            userAfterUpdate.setFirstName(user.getFirstName());
            userAfterUpdate.setLastName(user.getLastName());

            userRepository.save(userAfterUpdate);
        } else {
            throw new UsernameNotFoundException("User isn't found");
        }

    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User isn't found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }


}
