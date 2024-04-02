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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    private String addOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);

        ShoppingCart shoppingCart = user.getShoppingCart();
        List<CartItem> cartItemList = (List<CartItem>) shoppingCart.getItems();

        Order order = new Order();
        order.setOrderItems(new ArrayList<>());
        orderRepository.save(order);

        for (int i = 0; i < cartItemList.size(); i++) {
            if (cartItemList.get(i).getQuantity() > cartItemList.get(i).getProduct().getQuantity()) {
                //TODO add message
                return "redirect:/cart/view";
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItemList.get(i).getProduct());
            orderItem.setQuantity(cartItemList.get(i).getQuantity());
            cartItemList.get(i).getProduct().setQuantity(cartItemList.get(i).getProduct().getQuantity() - cartItemList.get(i).getQuantity());
            orderItem.setPriceEach(cartItemList.get(i).getPrice());
            orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem);
        }
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.NEW);
        order.setPrice(shoppingCart.getTotalPrice());
        orderRepository.save(order);


        for (int i = 0; i < shoppingCart.getItems().size(); i++) {
            shoppingCartService.deleteItem(((List<CartItem>) shoppingCart.getItems()).get(i).getId());
            i--;
        }

        return "order/order";
    }

    @GetMapping("/show")
    public String showAllOrders(Model model) {
        model.addAttribute("allOrders", orderRepository.findAll());
        return "order/show";
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam("orderId") Integer id) {

        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order;
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            //TODO message
            return "redirect:/order/show";
        }

        switch (order.getOrderStatus().getStatusNum()) {
            case (1):
                order.setOrderStatus(OrderStatus.PROCESSING);
                break;
            case (2):
                order.setOrderStatus(OrderStatus.DELIVERING);
            case (3):
                order.setOrderStatus(OrderStatus.COMPLETED);
            default:
                //TODO message
        }
        orderRepository.save(order);

        return "redirect:/order/show";
    }

}
