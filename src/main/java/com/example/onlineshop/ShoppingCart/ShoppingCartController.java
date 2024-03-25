package com.example.onlineshop.ShoppingCart;

import com.example.onlineshop.Product.Product;
import com.example.onlineshop.Product.ProductRepository;
import com.example.onlineshop.User.User;
import com.example.onlineshop.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute AddToCardDto addToCardDto) {
       return shoppingCartService.addToCart(addToCardDto);
    }

    @GetMapping("/view")
    public String showCart(Model model) {
       return shoppingCartService.showCart(model);
    }
    @PostMapping("/delete")
    public String deleteItem(@RequestParam(name = "itemId") Integer id){
        return shoppingCartService.deleteItem(id);
    }

}
