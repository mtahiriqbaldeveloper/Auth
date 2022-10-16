package com.dev.demo.service;

import com.dev.demo.Model.User;
import com.dev.demo.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User user){
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user=userRepo.findByEmail(username).orElseThrow(()->
            new UsernameNotFoundException("user not found"));
        com.dev.demo.security.User userDetails =
                new com.dev.demo.security.User
                        (user.getId(),user.getUserName(), passwordEncoder.encode(user.getPassword()) ,user.getPassword());
        return userDetails;
    }

    public UserDetails loadUserById(Long id){
        return (UserDetails) userRepo.findById(id).orElseThrow();
    }
}
