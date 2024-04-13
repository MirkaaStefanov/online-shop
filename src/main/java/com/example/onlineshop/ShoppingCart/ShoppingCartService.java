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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    public String addToCart(AddToCardDto addToCardDto, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);

        Optional<Product> optionalProduct = productRepository.findById(addToCardDto.getProductId());
        Product product;

        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            redirectAttributes.addFlashAttribute("message", "Product was not found");
            return "redirect:/";
        }

        List<CartItem> cartItemList = (List<CartItem>) user.getShoppingCart().getItems();
        boolean ifProductIsInCart = false;

        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProduct().equals(product)) {
                if (cartItem.getQuantity() + addToCardDto.getQuantity() > product.getQuantity()) {

                    String message = " We apologize. Out of stock" + cartItem.getQuantity() + " " + product.getName()
                            + "We have" + product.getQuantity() + "left";

                    redirectAttributes.addFlashAttribute("message", message);
                    return "redirect:/";
                }
                cartItem.setQuantity(cartItem.getQuantity() + addToCardDto.getQuantity());
                ifProductIsInCart = true;
                break;
            }
        }

        if (ifProductIsInCart == false) {
            if (addToCardDto.getQuantity() > product.getQuantity()) {
                String message = " We apologize. Out of stock " + addToCardDto.getQuantity() + " " + product.getName()
                        + "We have " + product.getQuantity() + " left";
                redirectAttributes.addFlashAttribute("message", message);
                return "redirect:/";
            }
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(addToCardDto.getQuantity());
            cartItemRepository.save(cartItem);
            user.getShoppingCart().getItems().add(cartItem);
        }

        shoppingCartRepository.save(user.getShoppingCart());

        redirectAttributes.addFlashAttribute("message", "You added to cart");
        return "redirect:/";
    }

    public String showCart(Model model, String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        model.addAttribute("shoppingCart", user.getShoppingCart());
        model.addAttribute("message", message);
        return "cart/show-cart";
    }

    public String deleteItem(Integer id, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);

        ShoppingCart shoppingCart = user.getShoppingCart();

        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            shoppingCart.getItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
            shoppingCartRepository.save(shoppingCart);
        }
        redirectAttributes.addFlashAttribute("message","You remove the product");
        return "redirect:/cart/view";
    }

}
