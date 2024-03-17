package com.example.onlineshop.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/add")
    public String productForm(Model model){
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("allTypes",productRepository.findAll());
        return "product/form";
    }

}
