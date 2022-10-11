package com.dev.demo.controller;


import com.dev.demo.Model.User;
import com.dev.demo.payload.SignUpRequest;
import com.dev.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user/")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("profile")
    public String profile(){

        return "this is your profile";
    }

    @GetMapping("login")
    public String login(){
        return "this is login page";
    }


    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){

        System.out.println(signUpRequest.toString());

        userService.createUser(new User(signUpRequest.getUserName(), signUpRequest.getPassword(),signUpRequest.getPassword()));

        return ResponseEntity.ok("accepted");
    }


}
