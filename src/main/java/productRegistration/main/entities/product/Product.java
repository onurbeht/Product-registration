package productRegistration.main.entities.product;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

//Association the object with the table
@Table(name = "products")
//Define as Entity
@Entity(name = "product")
//Generate EqualsAndHashCode, getter, setter, Constructor with all and no args.
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends RepresentationModel<Product> {

    //Define id as a PK from table, and define the strategy for generation.
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Number price_in_cents;
}
