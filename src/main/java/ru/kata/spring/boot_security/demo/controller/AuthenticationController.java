package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String get() {
        return "login";
    }


    @GetMapping(value = "/logout")
    public String exit() {
        return "logout";
    }

}
