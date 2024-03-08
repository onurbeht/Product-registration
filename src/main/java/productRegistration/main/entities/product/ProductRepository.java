package productRegistration.main.entities.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    //Create a method to find all active
    List<Product> findAllByActiveTrue();
}
