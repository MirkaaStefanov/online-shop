package com.example.onlineshop.Product;

import com.example.onlineshop.ProductType.ProductType;
import com.example.onlineshop.ProductType.ProductTypeRepository;
import com.example.onlineshop.ShoppingCart.AddToCardDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping("/add")
    public String productForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("allTypes", productTypeRepository.findAll());
        return "product/form";
    }

    @PostMapping("/submit")
    public String submitProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        Product product;
        if (bindingResult.hasErrors()) {
            model.addAttribute("allTypes", productTypeRepository.findAll());
            return "product/form";
        }
        String productType = productDto.getProductType().getName();
        if (productType.equalsIgnoreCase("food")) {
            if (productDto.getExpires_in() == null) {
                model.addAttribute("expiresDate", "You are adding food, you must enter expiration date");
                model.addAttribute("allTypes", productTypeRepository.findAll());
                return "product/form";
            } else {
                product = productMapper.toFoodEntity(productDto);
            }
        } else if (productType.equalsIgnoreCase("railing")) {
            if (productDto.getColor() == null) {
                model.addAttribute("color", "You are adding railing, you must enter color");
                model.addAttribute("allTypes", productTypeRepository.findAll());
                return "product/form";


            } else {
                product = productMapper.toRailingOrAccessoryEntity(productDto);
            }
        } else {
            product = productMapper.toEntity(productDto);
        }

        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successfulAddedProduct", "You added successfully new product !");
        return "redirect:/";
    }


}
