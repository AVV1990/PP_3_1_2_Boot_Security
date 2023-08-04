package ru.kata.spring.boot_security.demo.fillDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;


@Component
public class Init {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public Init(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initTables() {
        // пароль у adminUser: 123
        // пароль у normalUser: 321

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        User adminUser = new User("admin@mail.ru", "$2a$12$RRdlxgGzx7YoFpIWbYA4PekNBUWMecLSouYvy.kGPOE.IzXCCHO2q", "Alex", "MegaAlex", 30);
        User normalUser = new User("user@mail.ru", "$2a$12$N5NlnhKfckrNs5EPsE52purCDNHPX05wADoEqxvRoEKWu9CpnoyuS", "Jack", "MegaJack", 35);


        adminUser.setRoles(Set.of(adminRole, userRole));
        normalUser.setRoles(Set.of(userRole));

        userRepository.saveAll(List.of(adminUser, normalUser));

    }
}
