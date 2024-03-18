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

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute AddToCardDto addToCardDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);

        Product product = productRepository.findById(addToCardDto.getProductId()).get();

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(addToCardDto.getQuantity());
        cartItemRepository.save(cartItem);

        user.getShoppingCart().getItems().add(cartItem);

        shoppingCartRepository.save(user.getShoppingCart());

        return "redirect:/";
    }

    @GetMapping("/view")
    public String showCart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        model.addAttribute("shoppingCart", user.getShoppingCart());
        return "show-cart";
    }
}
