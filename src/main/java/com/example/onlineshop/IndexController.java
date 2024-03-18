package com.example.onlineshop;

import com.example.onlineshop.Product.ProductRepository;
import com.example.onlineshop.ShoppingCart.AddToCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class IndexController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String homeAllProducts(Model model, @ModelAttribute("successfulAddedProduct")String message){
        model.addAttribute("allProducts", productRepository.findAll());
        model.addAttribute("addToCardDto", new AddToCardDto());
        model.addAttribute("successfulAddedProduct", message);
        return "index";
    }



}
