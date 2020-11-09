package com.tts.ecommercepage.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.ecommercepage.model.Product;
import com.tts.ecommercepage.service.ProductService;

@Controller
@ControllerAdvice // This makes the `@ModelAttribute`s of this controller available to all
                  // controllers, for the navbar.
public class MainController {
    @Autowired
    ProductService productService;

    // public Product(int quantity, float price, String description,
    // String name, String brand, String category,String image) {

    public void addNew() {
        List<Product> allProducts = productService.findAll();

        if (allProducts.isEmpty()) {

            List<Product> newProducts = new ArrayList <Product>();

            newProducts.add(new Product(4, (float) 150.00, "Armani Emporio", "Emporio", "Armani", "watch",
                    "images/Emporio Armani.jpeg"));

            newProducts.add(new Product(3, (float) 1800.00, "Rolex GMT Master", "GMT Master", "Rolex", "watch",
                    "images/rolex.jpeg"));

            newProducts.add(
                    new Product(12, (float) 2200.00, "Rado Quartz", "Quartz", "Rado", "watch", "images/rado.jpeg"));

            newProducts.add(new Product(7, (float) 700.00, "Timex Leather Strap", "Leather Strap", "Timex", "watch",
                    "images/timex.jpeg"));

            for (Product product : newProducts) {
                productService.save(product);
            }
        } else {

            System.out.println("You don't need more items!");
        }
    }

    @GetMapping("/")
    public String main() {
        addNew();
        return "main";
    }

    @ModelAttribute("products")
    public List<Product> products() {
        return productService.findAll();
    }

    @ModelAttribute("categories")
    public List<String> categories() {
        return productService.findDistinctCategories();
    }

    @ModelAttribute("brands")
    public List<String> brands() {
        return productService.findDistinctBrands();
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) String category, @RequestParam(required = false) String brand,
            Model model) {
        List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
        model.addAttribute("products", filtered); // Overrides the @ModelAttribute above.
        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}