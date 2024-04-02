package com.example.onlineshop.ShoppingCart;

import com.example.onlineshop.Product.Product;
import com.example.onlineshop.Product.ProductRepository;
import com.example.onlineshop.User.User;
import com.example.onlineshop.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public String addToCart(AddToCardDto addToCardDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);

        Optional<Product> optionalProduct = productRepository.findById(addToCardDto.getProductId());
        Product product;

        if(optionalProduct.isPresent()){
            product = optionalProduct.get();
        }else{
            //TODO add to redirect attribute message
            return "redirect:/";
        }

        List<CartItem> cartItemList = (List<CartItem>)cartItemRepository.findAll();
        boolean ifProductIsInCart=false;
        for (CartItem cartItem: cartItemList) {
            if(cartItem.getProduct().equals(product)) {
                if(addToCardDto.getQuantity() > product.getQuantity() || cartItem.getQuantity()+addToCardDto.getQuantity()>product.getQuantity()){
                    //TODO add to redirect attribute message
                    return "redirect:/";
                }
                cartItem.setQuantity(cartItem.getQuantity()+addToCardDto.getQuantity());
                ifProductIsInCart=true;
            }
        }

        if(ifProductIsInCart==false) {
            if(addToCardDto.getQuantity() > product.getQuantity()){
                //TODO add to redirect attribute message
                return "redirect:/";
            }
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(addToCardDto.getQuantity());
            cartItemRepository.save(cartItem);
            user.getShoppingCart().getItems().add(cartItem);
        }

        shoppingCartRepository.save(user.getShoppingCart());

        return "redirect:/";
    }

    public String showCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        model.addAttribute("shoppingCart", user.getShoppingCart());
        return "cart/show-cart";
    }
    public String deleteItem(Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);

        ShoppingCart shoppingCart = user.getShoppingCart();

        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);

        if(optionalCartItem.isPresent()){
            CartItem cartItem = optionalCartItem.get();
            shoppingCart.getItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
            shoppingCartRepository.save(shoppingCart);
        }

        return "redirect:/cart/view";
    }

}
