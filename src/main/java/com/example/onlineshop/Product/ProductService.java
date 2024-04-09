package com.example.onlineshop.Product;

import com.example.onlineshop.ProductType.ProductTypeRepository;
import com.example.onlineshop.ShoppingCart.AddToCardDto;
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

    public String foodForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-food";
    }

    public String addFood( ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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
            name = ""; // Set default value or handle as needed
        }

        if (minPrice == null) {
            minPrice = (double) 0; // Set default value or handle as needed
        }
        if (maxPrice == null) {
            maxPrice = (double) Integer.MAX_VALUE; // Set default value or handle as needed
        }

        List<Product> filteredProduct = productRepository.filter(name, categoryId, minPrice, maxPrice);
        model.addAttribute("products", filteredProduct);
        model.addAttribute("addToCardDto", new AddToCardDto());
        return "product/show";


    }
//    @GetMapping("/filter")
//    public String filterProducts(@ModelAttribute ProductFilterDto productFilterDto, Model model) {
//
//        if (productFilterDto.name == null) {
//            productFilterDto.name = ""; // Set default value or handle as needed
//        }
////        if (productFilterDto.typeId==null) {
////            productFilterDto.typeId = 0; // Set default value or handle as needed
////        }
////        if (productFilterDto.minPrice == null) {
////            productFilterDto.minPrice = 0; // Set default value or handle as needed
////        }
////        if (productFilterDto.maxPrice == null) {
////            productFilterDto.maxPrice = Integer.MAX_VALUE; // Set default value or handle as needed
////        }
//
//        List<Product> filteredProduct = productRepository.filter(productFilterDto.name, productFilterDto.getType().getId(), productFilterDto.minPrice, productFilterDto.maxPrice);
//        model.addAttribute("products", filteredProduct);
//        model.addAttribute("addToCardDto", new AddToCardDto());
//        return "product/show";
//    }


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

    public String update(Integer productId, Model model){
        model.addAttribute("updateProduct", new ProductDto());
        model.addAttribute("productId", productId);
        return "product/update";
    }

    public String submitUpdate(ProductDto productDto, Integer productId){
        Product product;
        String productTypeName = productDto.getProductType().getName();
        if(productTypeName.equalsIgnoreCase("Accessories")){
        product = productMapper.toAccessoryEntity(productDto);
        }
        else if(productTypeName.equalsIgnoreCase("Drink")){
            product = productMapper.toDrinkEntity(productDto);
        }
        else if(productTypeName.equalsIgnoreCase("Food")){
            product = productMapper.toFoodEntity(productDto);
        }
        else if(productTypeName.equalsIgnoreCase("Railing")){
            product = productMapper.toRailingEntity(productDto);
        }
        else if(productTypeName.equalsIgnoreCase("Sanitary")){
            product = productMapper.toSanitaryEntity(productDto);
        }
        else {
            product = productMapper.toOthersEntity(productDto);
        }
        product.setId(productId);
        return "redirect:/";
    }

}
