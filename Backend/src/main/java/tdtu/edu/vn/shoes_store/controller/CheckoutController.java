package tdtu.edu.vn.shoes_store.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdtu.edu.vn.shoes_store.model.*;
import tdtu.edu.vn.shoes_store.repository.OrderDetailRepository;
import tdtu.edu.vn.shoes_store.repository.OrderRepository;
import tdtu.edu.vn.shoes_store.security.jwt.JwtTokenUtil;
import tdtu.edu.vn.shoes_store.service.OrderService;
import tdtu.edu.vn.shoes_store.service.ProductService;
import tdtu.edu.vn.shoes_store.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("api/checkout")
public class CheckoutController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ProductService productService;


    @PostMapping()
    public ResponseEntity<?> checkOut(@RequestBody Checkout checkout , HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Map<String, Object> result = new HashMap<>();
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Order order = new Order();

        order.setDate(new Date());
        order.setStatus("PENDING");
        order.setPayment(checkout.getPayment());
        order.setEmail(email);
        order.setAddress(checkout.getAddress());
        order.setTotalPrice(checkout.getTotalPrice());
        order.setOrderDetail(new ArrayList<>());
        order.setDelivery(checkout.getDelivery());

        for(DetailCheckout orderDetail: checkout.getDetailCheckout()){
            Product product = productService.getProductById(orderDetail.getProductId());
            OrderDetail newOrderDetail1 = new OrderDetail();

            newOrderDetail1.setProduct(product);
            newOrderDetail1.setSize(orderDetail.getSize());
            newOrderDetail1.setQuantity(orderDetail.getQuantity());
            newOrderDetail1.setPrice(orderDetail.getQuantity() * product.getPrice());


            order.getOrderDetail().add(newOrderDetail1);
            orderDetailRepository.save(newOrderDetail1);
        }
        orderRepository.save(order);

        result.put("message", "Order created successfully");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



}