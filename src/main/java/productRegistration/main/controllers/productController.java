package productRegistration.main.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class productController {

    @GetMapping
    ResponseEntity getAll() {
        return ResponseEntity.ok("Deu certo");
    }

}
