package productRegistration.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import productRegistration.main.entities.product.Product;
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

}
