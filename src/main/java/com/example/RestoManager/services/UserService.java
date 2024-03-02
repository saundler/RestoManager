package com.example.RestoManager.services;

import com.example.RestoManager.models.User;
import com.example.RestoManager.models.enums.Role;
import com.example.RestoManager.repositories.UserRepository;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepository.findByName(principal.getName());
    }

    public boolean createUser(User user) {
        String name = user.getName();
        if (userRepository.findByName(name) != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        if(user.getName().equals("ADMIN")){
            user.getRoles().add(Role.ROLE_ADMIN);
        }
        log.info("Saving new User with name: {}", name);
        userRepository.save(user);
        return true;
    }

    public List<User> list(){
        return userRepository.findAll();
    }
}
