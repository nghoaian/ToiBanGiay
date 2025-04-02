package tdtu.edu.vn.shoes_store.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.edu.vn.shoes_store.dto.OrderDto;
import tdtu.edu.vn.shoes_store.dto.UserDto;
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
@RequestMapping("api/orders")
public class OrderController {
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

    @GetMapping("")
    public ResponseEntity< List<OrderDto>> getAllOrders(){
        List<OrderDto> orderDtos = orderService.getAllOrders();
        if(orderDtos != null){
            return  ResponseEntity.ok(orderDtos);
        }
        return  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrderByID(@PathVariable(name = "id") Long id){
        Map<String, Object> result = new HashMap<>();
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()) {
            orderService.deleteOrder(id);
            result.put("statusCode", HttpStatus.OK.value());
            result.put("message", "Delete Order successfully!");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else  {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("message", "Order not found!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }


}
