package com.uday.Service;
import org.springframework.stereotype.Service;

import com.uday.Repository.UserRepository;
import com.uday.binding.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(User user) {
        if (userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()) != null) {
            return false;  // Username already exists
        }
        userRepository.save(user);
        return true;
    }

    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,password);
        return user != null && user.getPassword().equals(password);
    }
}
