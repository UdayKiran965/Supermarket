package com.uday.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uday.Repository.UserRepository;
import com.uday.binding.User;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Show the login page
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User()); // If needed, initialize a User object
        return "login";  // Thymeleaf template 'login.html'
    }

    // Handle login POST request
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model) {
        // Verify user credentials
        User foundUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (foundUser != null) {
            model.addAttribute("message", "Login successful!");
            return "index";  // Redirect to home page after login
        } else {
            model.addAttribute("error", "Invalid credentials...., please try again.");
            return "login"; // Stay on the login page if credentials are incorrect
        }
    }

    // Show the registration page
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";  // Thymeleaf template 'register.html'
    }

    // Handle registration POST request
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Save user details to the repository (You can modify this as per your logic)
        // userRepository.save(user); // Uncomment when actual repository is ready

        redirectAttributes.addFlashAttribute("message", "Registration successful! Please login.");
        return "redirect:/login"; // Redirect to login page after registration
    }

    

    // Show the home page
    @GetMapping("/home")
    public String showHomePage() {
        return "index";  // This should match your home HTML/Thymeleaf template
    }
}
