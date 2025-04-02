package tdtu.edu.vn.shoes_store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private Long id;

    private Date date;

    private String status;

    private String payment;
    private String email;
    private String address;

    private double totalPrice;

    private List<ProductOrderDto> products = new ArrayList<>();

    private String delivery;
//    private List<OrderDetail> orderDetail = new ArrayList<>();

    @Builder
    public OrderDto(Long id,  Date date, String status, String payment,String email,String address, double totalPrice,String delivery) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.payment = payment;
        this.email = email;
        this.address = address;
        this.totalPrice = totalPrice;
        this.delivery = delivery;
//        this.orderDetail = orderDetail;
    }
}
