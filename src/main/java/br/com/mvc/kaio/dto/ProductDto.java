package br.com.mvc.kaio.dto;

import br.com.mvc.kaio.model.Category;
import br.com.mvc.kaio.model.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Boolean available;

    @NotBlank
    private String categoryPath;

    private BigDecimal price;

    private Long categoryId;

    public Product toProduct() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .available(this.available)
                .categoryPath(this.categoryPath)
                .category(buidCategory()).build();
    }

    private Category buidCategory() {
        return Category.builder()
                .id(this.categoryId)
                .build();
    }

}
