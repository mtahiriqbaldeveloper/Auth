package com.dev.demo.service;

import com.dev.demo.Model.User;
import com.dev.demo.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    public final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
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
                        (user.getId(),user.getUserName(),user.getPassword(),user.getPassword());
        return userDetails;
    }

    public UserDetails loadUserById(Long id){
        return (UserDetails) userRepo.findById(id).orElseThrow();
    }
}
