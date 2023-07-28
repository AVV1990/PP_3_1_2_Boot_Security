package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepository.findByMail(mail);
        if (user == null) {
            throw new UsernameNotFoundException("User isn't found");
        }
        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), user.getAuthorities());
    }
}
