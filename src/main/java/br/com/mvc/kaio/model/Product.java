package br.com.mvc.kaio.model;

import br.com.mvc.kaio.dto.ProductDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column
    @Size(max = 255)
    private String name;

    @Column
    @Size(max = 255)
    private String description;

    @Column
    private BigDecimal price;

    @Column
    private Boolean available = Boolean.TRUE;

    @Column
    @Size(max = 255)
    private String categoryPath;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .available(product.getAvailable())
                .categoryId(product.getCategory().getId())
                .categoryPath(product.getCategoryPath())
                .price(product.getPrice())
                .build();
    }
}
