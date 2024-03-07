package productRegistration.main.entities.product;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

//Association the object with the table
@Table(name = "products")
//Define as Entity
@Entity(name = "product")
//Generate EqualsAndHashCode, getter, setter, Constructor with all and no args.

//Não sei pq o lombok não funcionou, para getters e setters :(
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

    //Constructor with name and price_in_cents
    public Product (String name, Integer price_in_cents) {
        this.name = name;
        this.price_in_cents = price_in_cents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice_in_cents() {
        return price_in_cents;
    }

    public void setPrice_in_cents(Integer price_in_cents) {
        this.price_in_cents = price_in_cents;
    }
}
