package com.example.onlineshop.Product;

import com.example.onlineshop.Order.OrderItem;
import com.example.onlineshop.Order.OrderItemRepository;
import com.example.onlineshop.ProductType.ProductTypeRepository;
import com.example.onlineshop.ShoppingCart.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public String foodForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-food";
    }

    public String addFood(ProductDto productDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/form-food";
        }
        if (productDto.getExpires_in() == null) {
            return "product/form-food";
        }
        Product product = productMapper.toFoodEntity(productDto);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successfulAddedProduct", "You added successfully new food !");
        return "redirect:/";
    }

    public String drinkForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-drink";
    }

    public String addDrink(ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/form-drink";
        }
        if (productDto.getExpires_in() == null) {
            return "product/form-drink";
        }
        Product product = productMapper.toDrinkEntity(productDto);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successfulAddedProduct", "You added successfully new drink !");
        return "redirect:/";
    }


    public String sanitaryForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-sanitary";
    }

    public String addSanitary(ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/form-sanitary";
        }
        if (productDto.getColor() == null) {
            return "product/form-sanitary";
        }
        Product product = productMapper.toSanitaryEntity(productDto);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successfulAddedProduct", "You added successfully new sanitary !");
        return "redirect:/";
    }

    public String railingForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-railing";
    }

    public String addRailing(ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/form-railing";
        }
        if (productDto.getColor() == null) {
            return "product/form-railing";
        }
        Product product = productMapper.toRailingEntity(productDto);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successfulAddedProduct", "You added successfully new railing !");
        return "redirect:/";
    }

    public String accessoriesForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-accessories";
    }

    public String addAccessories(ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/form-accessories";
        }
        if (productDto.getColor() == null) {
            return "product/form-accessories";
        }
        Product product = productMapper.toAccessoryEntity(productDto);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successfulAddedProduct", "You added successfully new accessories !");
        return "redirect:/";
    }


    public String othersForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-others";
    }

    public String addOthers(ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/form-others";
        }
        Product product = productMapper.toOthersEntity(productDto);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successfulAddedProduct", "You added successfully new product !");
        return "redirect:/";
    }


    public String filterProducts(String name,
                                 Integer categoryId,
                                 Double minPrice,
                                 Double maxPrice,
                                 Model model) {
        if (name == null) {
            name = "";
        }

        if (minPrice == null) {
            minPrice = (double) 0;
        }
        if (maxPrice == null) {
            maxPrice = (double) Integer.MAX_VALUE;
        }
        if (minPrice > maxPrice) {
            double maxPrice1 = maxPrice;
            maxPrice = minPrice;
            minPrice=maxPrice1;
        }
        List<Product> filteredProduct = productRepository.filter(name, categoryId, minPrice, maxPrice);
        model.addAttribute("products", filteredProduct);
        model.addAttribute("categories", productTypeRepository.findAll());
        model.addAttribute("addToCardDto", new AddToCardDto());
        return "product/show";


    }

    public String search(String search, Model model) {
        try {
            Integer id = Integer.parseInt(search);
            model.addAttribute("products", productRepository.findProductById(id));
        } catch (Exception e) {
            model.addAttribute("products", productRepository.findProductByNameOrCategory(search));
        }
        model.addAttribute("addToCardDto", new AddToCardDto());
        return "product/show";
    }

    public String update(Integer productId, Model model) {
        model.addAttribute("updateProduct", new ProductDto());
        model.addAttribute("productId", productId);
        model.addAttribute("allTypes", productTypeRepository.findAll());
        return "product/update";
    }

    public String submitUpdate(ProductDto productDto, Integer productId, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Product product;
        String productTypeName = productDto.getProductType().getName();
        if (productTypeName.equalsIgnoreCase("Accessories")) {
            product = productMapper.toAccessoryEntity(productDto);
        } else if (productTypeName.equalsIgnoreCase("Drink")) {
            if (bindingResult.hasErrors()) {
                return "product/update";
            }
            if (productDto.getExpires_in() == null) {
                return "product/update";
            }
            product = productMapper.toDrinkEntity(productDto);
        } else if (productTypeName.equalsIgnoreCase("Food")) {
            if (bindingResult.hasErrors()) {
                return "product/update";
            }
            if (productDto.getExpires_in() == null) {
                return "product/update";
            }
            product = productMapper.toFoodEntity(productDto);
        } else if (productTypeName.equalsIgnoreCase("Railing")) {
            if (bindingResult.hasErrors()) {
                return "product/update";
            }
            if (productDto.getColor() == null) {
                return "product/update";
            }
            product = productMapper.toRailingEntity(productDto);
        } else if (productTypeName.equalsIgnoreCase("Sanitary")) {
            if (bindingResult.hasErrors()) {
                return "product/update";
            }
            if (productDto.getColor() == null) {
                return "product/update";
            }
            product = productMapper.toSanitaryEntity(productDto);
        } else {
            if (bindingResult.hasErrors()) {
                return "product/update";
            }
            product = productMapper.toOthersEntity(productDto);
        }

        product.setId(productId);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("message", "Product is updated");
        return "redirect:/";
    }

    public String deleteProduct(Integer productId, RedirectAttributes redirectAttributes) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            redirectAttributes.addFlashAttribute("message", "Product could not be found");
            return "redirect:/";
        }


        List<ShoppingCart> shoppingCarts = (List<ShoppingCart>) shoppingCartRepository.findAll();

        for (ShoppingCart shoppingCart : shoppingCarts) {
            List<CartItem> cartItemList = (List<CartItem>) shoppingCart.getItems();
            for (int i = 0; i < cartItemList.size(); i++) {
                if (cartItemList.get(i).getProduct().equals(product)) {
                    CartItem cartItemForDelete = cartItemList.get(i);
                    shoppingCart.getItems().remove(cartItemList.get(i));
                    cartItemRepository.delete(cartItemForDelete);
                    shoppingCartRepository.save(shoppingCart);
                    break;
                }
            }
        }
        List<OrderItem> orderItemList = (List<OrderItem>) orderItemRepository.findAll();
        for (int i = 0; i < orderItemList.size(); i++) {
            if (orderItemList.get(i).getProduct().equals(product)) {
                redirectAttributes.addFlashAttribute("message", "Product is ordered, and cannot be deleted");
                return "redirect:/";
            }
        }
        productRepository.delete(product);
        redirectAttributes.addFlashAttribute("message", "Product is deleted successfully");

        return "redirect:/";
    }

}
