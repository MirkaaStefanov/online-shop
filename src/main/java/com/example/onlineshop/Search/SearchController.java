package com.example.onlineshop.Search;

import com.example.onlineshop.Product.ProductRepository;
import com.example.onlineshop.ShoppingCart.AddToCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SearchController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/search")
    public String search(@RequestParam(name = "search") String search, Model model) {
        try {
            Integer id = Integer.parseInt(search);
            model.addAttribute("searchedProducts", productRepository.findProductById(id));
        } catch (Exception e) {
            model.addAttribute("searchedProducts", productRepository.findProductByNameOrCategory(search));
        }
        model.addAttribute("addToCardDto", new AddToCardDto());
        return "product/search";
    }


}
