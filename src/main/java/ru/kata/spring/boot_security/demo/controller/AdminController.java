package ru.kata.spring.boot_security.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.List;

@RestController
@Slf4j

public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping(value = "admin/getUsers")
    public List<User> getUsers() {
        return userService.getUsers();
    }


    @GetMapping(value = "/admin/getRoles")
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @PostMapping(value = "/admin/addUser")
    public void addUser(@RequestBody User user) {
        System.out.println("----------------");
        log.info(user.toString());
        userService.addUser(user);
    }

    @GetMapping(value = "/admin/getUser/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping(value = "/admin/deleteUser/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping(value = "/admin/updateUser/{id}")
    @ResponseBody
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

}
