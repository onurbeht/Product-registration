package productRegistration.main.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productRegistration.main.entities.product.Product;
import productRegistration.main.entities.product.ProductDTO;
import productRegistration.main.entities.product.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

//GET
    @GetMapping
    ResponseEntity getAll() {
        //Get all
        List<Product> products = productRepository.findAll();

//        //Check if is not empty
//        if(!products.isEmpty()) {
//            for (Product product : products) {
//                //for each product create a link for itself
//                product
//            }
//        }

        //Return the list fo products
        return ResponseEntity.ok(products);
    }

//POST
    @PostMapping                //Valid the data from product and get it request body
    ResponseEntity registerProduct(@RequestBody @Valid ProductDTO product) {
        Product newProduct = new Product(product.name(), product.price_in_cents());

        //Save new product
        productRepository.save(newProduct);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }

}
