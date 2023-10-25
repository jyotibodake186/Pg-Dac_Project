package com.fashionrental.website.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fashionrental.website.global.GlobalData;
import com.fashionrental.website.model.Role;
import com.fashionrental.website.model.User;
import com.fashionrental.website.repository.RoleRepository;
import com.fashionrental.website.repository.UserRepository;

@Controller
public class LoginController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}
	@GetMapping("/register")
	public String registerGet() {
		return "login";
	}
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user,HttpServletRequest request)throws ServletException{
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";
	}
	
	@GetMapping("/forgotpassword")
	public String forgotPasswordGet() {
		return "forgotpassword";
	}
	
	@PostMapping("/forgotpassword")
    public String updatePasswordPost(@RequestParam String newPassword, @RequestParam String email) throws ServletException {
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent()) {
            User foundUser = existingUser.get();
            foundUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(foundUser);
            return "redirect:/login";
        } else {
            // Handle user not found scenario
            return "redirect:/forgotpassword?error";
        }
    }


}
