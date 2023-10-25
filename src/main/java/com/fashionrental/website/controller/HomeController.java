package com.fashionrental.website.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fashionrental.website.global.GlobalData;
import com.fashionrental.website.model.User;
import com.fashionrental.website.service.CategoryService;
import com.fashionrental.website.service.CustomUserDetailService;
import com.fashionrental.website.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	
	@GetMapping({"/" , "/home"})
	public String home(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}
	
	@GetMapping({"/shop"})
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}
	
	@GetMapping({"/shop/category/{id}"})
	public String shopByCategory(Model model,@PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}
	
	@GetMapping({"/shop/viewproduct/{id}"})
	public String viewProduct(Model model,@PathVariable int id) {
		model.addAttribute("product", productService.getProductById(id).get());
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "viewProduct";
	}
	
	@GetMapping("/profile")
	public String viewProfile(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (authentication != null) {
	        if (authentication.getPrincipal() instanceof UserDetails) {
	            // User logged in using credentials
	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	            String email = userDetails.getUsername();
	            
	            Optional<User> user = customUserDetailService.findByEmail(email);
	            user.ifPresent(u -> {
	                model.addAttribute("user", u);
	                model.addAttribute("cartCount", GlobalData.cart.size());
	            });
	        } else if (authentication instanceof OAuth2AuthenticationToken) {
	            // User logged in using Google OAuth2
	            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
	            String email = oauth2Token.getPrincipal().getAttribute("email");
	            
	            Optional<User> user = customUserDetailService.findByEmail(email);
	            user.ifPresent(u -> {
	                model.addAttribute("user", u);
	                model.addAttribute("cartCount", GlobalData.cart.size());
	            });
	        }
	    }
	    
	    return "profile";
	}

}

