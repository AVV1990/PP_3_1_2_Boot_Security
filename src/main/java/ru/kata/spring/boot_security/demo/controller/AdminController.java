package ru.kata.spring.boot_security.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@Slf4j
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/admin/getUsers")
    public String getUsers(ModelMap model, Principal principal) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", userService.findByMail(principal.getName()));
        return "users";
    }

    @GetMapping(value = "/admin/show_formToAdd")
    public String showPageToAddUser(Model model, Principal principal) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", userService.findByMail(principal.getName()));
        return "newUser";
    }

    @GetMapping(value = "/admin/show_formToFindUserById")
    public String showPageToFindUserById() {
        return "formToFindUserById";
    }

    @PostMapping(value = "/admin/addUser")
    public String addUser(User user) {
        userService.addUser(user);
        return "redirect:/admin/getUsers";
    }


    @GetMapping(value = "/admin/getUser")
    public String getUserById(ModelMap model, @RequestParam Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "findUserById";
    }


    @GetMapping(value = "/admin/{id}/deleteUser")
    public String deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/getUsers";
    }

    @GetMapping(value = "/admin/{id}/updateUser")
    public String showPageToUpdateUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "edit_user";
    }

    @PostMapping(value = "/admin/updateUser/{id}")
    public String updateUser(@PathVariable Long id, User user) {
        log.info(user.toString());
        userService.updateUser(id, user);
        return "redirect:/admin/getUsers";
    }


}
