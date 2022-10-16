package com.dev.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping(path = "/auth2/")
public class AuthController {

    @GetMapping("profile")
    public String getProfile(){

        return "das ist die profile";
    }
}
