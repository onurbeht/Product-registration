package productRegistration.main.entities.product;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

//Association the object with the table
@Table(name = "products")
//Define as Entity
@Entity(name = "product")
//Generate EqualsAndHashCode, getter, setter, Constructor with all and no args.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product extends RepresentationModel<Product> {

    //Define id as a PK from table, and define the strategy for generation.
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Integer price_in_cents;
    private Boolean active;

    //Constructor with name and price_in_cents
    public Product (String name, Integer price_in_cents) {
        this.name = name;
        this.price_in_cents = price_in_cents;
        this.active = true;
    }


}
