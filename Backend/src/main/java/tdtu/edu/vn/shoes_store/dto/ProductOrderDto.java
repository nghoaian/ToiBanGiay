package tdtu.edu.vn.shoes_store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class ProductOrderDto {
    private String name;
    private double price;
    private String description;
    private int size;
    private int quantity;
    private String image;

    @Builder
    public ProductOrderDto(String name, double price, String description, int size, int quantity, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.size = size;
        this.quantity = quantity;
        this.image = image;
    }

}
