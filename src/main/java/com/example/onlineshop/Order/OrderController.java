package com.example.onlineshop.Order;

import com.example.onlineshop.ShoppingCart.CartItem;
import com.example.onlineshop.ShoppingCart.ShoppingCart;
import com.example.onlineshop.ShoppingCart.ShoppingCartRepository;
import com.example.onlineshop.ShoppingCart.ShoppingCartService;
import com.example.onlineshop.User.User;
import com.example.onlineshop.User.UserRepository;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    private String addOrder(RedirectAttributes redirectAttributes) {
        return orderService.addOrder(redirectAttributes);
    }

    @GetMapping("/show")
    public String showAllOrders(Model model, @ModelAttribute("message") String message) {
        return orderService.showAllOrders(model, message);
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam("orderId") Integer id, RedirectAttributes redirectAttributes) {
        return orderService.changeStatus(id, redirectAttributes);
    }

    @GetMapping("/show-order")
    public String showOneOrder(@RequestParam("orderId") Integer orderId, Model model, RedirectAttributes redirectAttributes) {
        return orderService.showOneOrder(orderId, model, redirectAttributes);
    }

    @GetMapping("/sort")
    public String sortOrder(@RequestParam(name = "status") String status, Model model) {
        return orderService.sortOrder(status, model);
    }
    @GetMapping("/success")
    public String successMessage(){
        return "order/order";
    }

}
