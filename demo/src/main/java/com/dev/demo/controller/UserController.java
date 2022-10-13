package com.dev.demo.controller;


import com.dev.demo.Model.User;
import com.dev.demo.payload.LoginRequest;
import com.dev.demo.payload.SignUpRequest;
import com.dev.demo.security.JwtTokenProvider;
import com.dev.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user/")
@Controller
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("profile")
    public String profile(){

        return "this is your profile";
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        System.out.println(loginRequest);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println(token);
        return ResponseEntity.ok("successful");
    }


    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){

        System.out.println(signUpRequest.toString());

        userService.createUser(new User(signUpRequest.getUserName(), signUpRequest.getPassword(),signUpRequest.getPassword()));

        return ResponseEntity.ok("accepted");
    }


}
