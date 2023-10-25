package com.fashionrental.website.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.fashionrental.website.global.GlobalData;
import com.fashionrental.website.model.Product;
import com.fashionrental.website.service.ProductService;

@Controller
public class CartController {
	@Autowired
	ProductService productService;
	
	 @GetMapping("/addToCart/{id}")
	    public String addToCart(@PathVariable int id) {
	        // Check if the product already exists in the cart
		 Optional<Product> productOptional = productService.getProductById(id);

		    if (productOptional.isPresent()) {
		        Product product = productOptional.get();
		        
		        // Check if the product already exists in the cart
		        boolean productExistsInCart = false;
		        for (Product cartProduct : GlobalData.cart) {
		            if (cartProduct.getId() == product.getId()) {
		                cartProduct.incrementQuantity();
		                productExistsInCart = true;
		                break;
		            }
		        }
		        
		        if (!productExistsInCart) {
		            product.setQuantity(1);
		            GlobalData.cart.add(product);
		        }
		    }

		    return "redirect:/shop";
	    }
	
	 @GetMapping("/cart")
	 public String cartGet(Model model) {
	     double total = GlobalData.cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

	     model.addAttribute("cartCount", GlobalData.cart.size());
	     model.addAttribute("total", total);
	     model.addAttribute("cart", GlobalData.cart);

	     return "cart";
	 }

	
	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index) {
		GlobalData.cart.remove(index);
		return "redirect:/cart";
	}
	
	@GetMapping("/checkout")
	public String checkout(Model model) {
		double total = GlobalData.cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
		model.addAttribute("total", total);
		return "checkout";
	}
	
	@GetMapping("/cart/incrementQuantity/{index}")
    public String incrementQuantity(@PathVariable int index) {
        if (index >= 0 && index < GlobalData.cart.size()) {
            GlobalData.cart.get(index).incrementQuantity();
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/decrementQuantity/{index}")
    public String decrementQuantity(@PathVariable int index) {
        if (index >= 0 && index < GlobalData.cart.size()) {
            Product product = GlobalData.cart.get(index);
            if (product.getQuantity() > 1) {
                product.decrementQuantity();
            } else {
                GlobalData.cart.remove(index);
            }
        }
        return "redirect:/cart";
    }
    
    @GetMapping("/payment")
    public String redirectToPaymentForm(Model model) {
    	double total = GlobalData.cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
		model.addAttribute("total", total);
		model.addAttribute("cartCount", GlobalData.cart.size());
        return "/payment"; // Thymeleaf template name
    }
    
    @GetMapping("/orderPlaced")
    public String redirectToOrderPlaced(Model model) {
    	GlobalData.cart.clear();
    	double total = GlobalData.cart.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
		model.addAttribute("total", total);
		model.addAttribute("products",productService.getAllProduct());
        return "/orderPlaced"; // Thymeleaf template name
    }
}

