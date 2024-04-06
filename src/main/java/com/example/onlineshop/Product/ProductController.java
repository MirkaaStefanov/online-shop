package com.example.onlineshop.Product;

import com.example.onlineshop.ProductType.ProductType;
import com.example.onlineshop.ProductType.ProductTypeRepository;
import com.example.onlineshop.ShoppingCart.AddToCardDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping("/add/food")
    public String foodForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-food";
    }

    @PostMapping("/submit/food")
    public String addFood(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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

    @GetMapping("/add/drink")
    public String drinkForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-drink";
    }

    @PostMapping("/submit/drink")
    public String addDrink(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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

    @GetMapping("/add/sanitary")
    public String sanitaryForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-sanitary";
    }

    @PostMapping("/submit/sanitary")
    public String addSanitary(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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

    @GetMapping("/add/railing")
    public String railingForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-railing";
    }

    @PostMapping("/submit/railing")
    public String addRailing(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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

    @GetMapping("/add/accessories")
    public String accessoriesForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-accessories";
    }

    @PostMapping("/submit/accessories")
    public String addAccessories(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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

    @GetMapping("/add/others")
    public String othersForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "product/form-others";
    }

    @PostMapping("/submit/others")
    public String addOthers(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "product/form-others";
        }
        Product product = productMapper.toOthersEntity(productDto);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successfulAddedProduct", "You added successfully new product !");
        return "redirect:/";
    }

    @GetMapping("/filter")
    public String filterProducts(@RequestParam("name") String name, @RequestParam("type") Integer typeId,
                                 @RequestParam("minPrice") Integer minPrice, @RequestParam("maxPrice") Integer maxPrice,
                                 Model model) {

        if (name == null) {
            name = ""; // Set default value or handle as needed
        }
        if (typeId == null) {
            typeId = 0; // Set default value or handle as needed
        }
        if (minPrice == null) {
            minPrice = 0; // Set default value or handle as needed
        }
        if (maxPrice == null) {
            maxPrice = Integer.MAX_VALUE; // Set default value or handle as needed
        }

        List<Product> filteredProduct = productRepository.filter(name, typeId, minPrice, maxPrice);
        model.addAttribute("products", filteredProduct);
        model.addAttribute("addToCardDto", new AddToCardDto());
        return "product/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search") String search, Model model) {
        try {
            Integer id = Integer.parseInt(search);
            model.addAttribute("products", productRepository.findProductById(id));
        } catch (Exception e) {
            model.addAttribute("products", productRepository.findProductByNameOrCategory(search));
        }
        model.addAttribute("addToCardDto", new AddToCardDto());
        return "product/show";
    }


}
