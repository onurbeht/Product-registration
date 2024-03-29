package productRegistration.main.entities.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        String id,
        @NotBlank String name,
        @NotNull Integer price_in_cents) {
}
