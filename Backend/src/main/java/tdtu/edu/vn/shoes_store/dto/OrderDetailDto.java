package tdtu.edu.vn.shoes_store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tdtu.edu.vn.shoes_store.model.Product;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDto {

    private Long id;

    private Product product;
    private int size;
    private int quantity;
    private double price;

    @Builder
    public OrderDetailDto(Long id, Product product, int size, int quantity, double price) {
        this.id = id;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }
}
