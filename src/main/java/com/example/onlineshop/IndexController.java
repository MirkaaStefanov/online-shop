package com.example.onlineshop;

import com.example.onlineshop.Product.ProductFilterDto;
import com.example.onlineshop.Product.ProductRepository;
import com.example.onlineshop.ProductType.ProductTypeRepository;
import com.example.onlineshop.ShoppingCart.AddToCardDto;
import jakarta.annotation.ManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class IndexController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping("/")
    public String homeAllProducts(@ModelAttribute("message") String message, Model model) {

        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("addToCardDto", new AddToCardDto());
        model.addAttribute("categories", productTypeRepository.findAll());

        model.addAttribute("message", message);

        return "product/show";
    }


}
