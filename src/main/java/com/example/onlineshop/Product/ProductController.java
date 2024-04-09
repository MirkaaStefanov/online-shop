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
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/add/food")
    public String foodForm(Model model) {
        return  productService.foodForm(model);
    }

    @PostMapping("/submit/food")
    public String addFood(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
    return productService.addFood(productDto,bindingResult, model, redirectAttributes);
    }
    @GetMapping("/add/drink")
    public String drinkForm(Model model) {
     return productService.drinkForm(model);
    }

    @PostMapping("/submit/drink")
    public String addDrink(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        return productService.addDrink(productDto, bindingResult, model, redirectAttributes);
    }

    @GetMapping("/add/sanitary")
    public String sanitaryForm(Model model) {
        return productService.sanitaryForm(model);
    }

    @PostMapping("/submit/sanitary")
    public String addSanitary(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        return productService.addSanitary(productDto, bindingResult, model, redirectAttributes);
    }

    @GetMapping("/add/railing")
    public String railingForm(Model model) {
        return productService.railingForm(model);
    }

    @PostMapping("/submit/railing")
    public String addRailing(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        return productService.addRailing(productDto, bindingResult, model, redirectAttributes);
    }

    @GetMapping("/add/accessories")
    public String accessoriesForm(Model model) {
        return  productService.accessoriesForm(model);
    }

    @PostMapping("/submit/accessories")
    public String addAccessories(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        return productService.addAccessories(productDto, bindingResult, model, redirectAttributes);
    }

    @GetMapping("/add/others")
    public String othersForm(Model model) {
        return productService.othersForm(model);
    }

    @PostMapping("/submit/others")
    public String addOthers(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
       return productService.addOthers(productDto, bindingResult, model, redirectAttributes);
    }

    @GetMapping("/filter")
    public String filterProducts(@RequestParam(name = "name", required = false) String name,
                                 @RequestParam(name = "categoryId", required = false) Integer categoryId,
                                 @RequestParam(name = "minPrice", required = false) Double minPrice,
                                 @RequestParam(name = "maxPrice", required = false) Double maxPrice,
                                 Model model) {
        return productService.filterProducts(name, categoryId, minPrice, maxPrice, model);
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

    @GetMapping("/search")
    public String search(@RequestParam(name = "search") String search, Model model) {
        return productService.search(search, model);
    }

    @GetMapping("/update")
    public String update(@RequestParam(name = "productId") Integer productId, Model model){
        return productService.update(productId, model);
    }
    @PostMapping("/submit/update")
    public String submitUpdate(@Valid @ModelAttribute ProductDto productDto, @RequestParam(name="productId") Integer productId){
        return productService.submitUpdate(productDto, productId);
    }
}
