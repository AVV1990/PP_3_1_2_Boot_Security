package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin/getUsers")
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping(value = "/admin/show_formToAdd")
    public String showPageToAddUser() {
        return "formToAdd";
    }

    @GetMapping(value = "/admin/show_formToFindUserById")
    public String showPageToFindUserById() {
        return "formToFindUserById";
    }

    @PostMapping(value = "/admin/addUser")
    public String addUser(User user) {
        userService.addUser(user);
        return "users";
    }


    @GetMapping(value = "/admin/getUserById")
    public String getUserById(ModelMap model, @RequestParam Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "findUserById";
    }


    @GetMapping(value = "/admin/deleteUserById")
    public String deleteUserById(@RequestParam Long id) {
        userService.deleteUserById(id);
        return "users";
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUserById(@RequestParam Long id, User user) {
        userService.updateUser(id, user);
        return "users";
    }

}
