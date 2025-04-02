package tdtu.edu.vn.shoes_store.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "status")
    private String status;


    @Column(name = "payment")
    private String payment;


    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "total_price",nullable = false)
    private double totalPrice;

    @Column(name = "delivery",nullable = false)
    private String delivery;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail = new ArrayList<>();

}
