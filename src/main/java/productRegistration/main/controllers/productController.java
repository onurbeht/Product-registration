package productRegistration.main.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productRegistration.main.entities.product.Product;
import productRegistration.main.entities.product.ProductDTO;
import productRegistration.main.entities.product.ProductRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

//GET
    @GetMapping
    ResponseEntity<List<Product>> getAll() {
        //Get all
        List<Product> products = productRepository.findAll();

        //Check if is not empty
        if(!products.isEmpty()) {
            for (Product product : products) {
                //for each product create a link for itself
                product.add(linkTo(methodOn(ProductController.class).getOne(product.getId())).withSelfRel());
            }
        }

        //Return the list fo products
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    ResponseEntity getOne(@PathVariable String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

            //check if the optionalProduct exists
            if (optionalProduct.isPresent()) {
                //Get the product from optional
                Product product = optionalProduct.get();
                //add the link
                product.add(linkTo(methodOn(ProductController.class).getAll()).withRel("Products list"));

                //return the product
                return ResponseEntity.ok(product);
            }

        //If optionalProduct is false, return NOT FOUND
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Product not found");
    }


//POST
    @PostMapping                //Valid the data from product and get it request body
    ResponseEntity registerProduct(@RequestBody @Valid ProductDTO product) {
        Product newProduct = new Product(product.name(), product.price_in_cents());

        //Save new product
        productRepository.save(newProduct);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }

//PUT
    @PutMapping()
    @Transactional
    ResponseEntity<Product> getOne(@RequestBody @Valid ProductDTO data) {
        //Get data from the old product
        Optional<Product> oldProduct = productRepository.findById(data.id());

        //check if the optionalProduct exists
        if (oldProduct.isPresent()) {
            //Get the product from old
            Product updateProduct = oldProduct.get();

            //Set the new values of product
            updateProduct.setName(data.name());
            updateProduct.setPrice_in_cents(data.price_in_cents());

            return ResponseEntity.ok(updateProduct);
        }

        //If oldProduct is false, return NOT FOUND
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }

//DELETE
    @DeleteMapping("/{id}")
    ResponseEntity deleteById(@PathVariable String id) {
        if(productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Product not found");
    }

}
