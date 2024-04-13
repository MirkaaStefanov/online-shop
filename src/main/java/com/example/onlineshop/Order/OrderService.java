package com.example.onlineshop.Order;

import com.example.onlineshop.ShoppingCart.CartItem;
import com.example.onlineshop.ShoppingCart.ShoppingCart;
import com.example.onlineshop.ShoppingCart.ShoppingCartRepository;
import com.example.onlineshop.ShoppingCart.ShoppingCartService;
import com.example.onlineshop.User.User;
import com.example.onlineshop.User.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
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

    public String addOrder(RedirectAttributes redirectAttributes) {
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
                String message = " We apologize. Out of stock " + cartItemList.get(i).getQuantity() + " " + cartItemList.get(i).getProduct().getName()
                        + "We have " + cartItemList.get(i).getProduct().getQuantity() + " left";

                redirectAttributes.addFlashAttribute("message", message);
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
            shoppingCartService.deleteItem(((List<CartItem>) shoppingCart.getItems()).get(i).getId(), redirectAttributes);
            i--;
        }

        return "order/order";
    }

    public String showAllOrders(Model model, String message) {
        model.addAttribute("allOrders", orderRepository.findAll());
        model.addAttribute("message", message);
        return "order/show-all";
    }

    public String changeStatus(@RequestParam("orderId") Integer id, RedirectAttributes redirectAttributes) {

        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order;
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            redirectAttributes.addFlashAttribute("message", "Order was not found !");
            return "redirect:/order/show";
        }

        switch (order.getOrderStatus().getStatusNum()) {
            case (1):
                order.setOrderStatus(OrderStatus.PROCESSING);
                break;
            case (2):
                order.setOrderStatus(OrderStatus.DELIVERING);
                break;
            case (3):
                order.setOrderStatus(OrderStatus.COMPLETED);
                break;
        }
        orderRepository.save(order);

        redirectAttributes.addFlashAttribute("message", "You updated status successfully");
        return "redirect:/order/show";
    }

    public String showOneOrder(@RequestParam("orderId") Integer orderId, Model model, RedirectAttributes redirectAttributes) {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order;
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            redirectAttributes.addFlashAttribute("message", "Order was not found");
            return "redirect:/order/show";
        }

        model.addAttribute("order", order);
        return "order/show-one";
    }

    public String sortOrder(String status, Model model) {

        if(status == null || status.isEmpty() || status.equals("ALL")){
            model.addAttribute("allOrders", orderRepository.findAll());
        }
        model.addAttribute("allOrders", orderRepository.sortByStatus(OrderStatus.valueOf(status)));
        return "order/show-all";
    }

}
