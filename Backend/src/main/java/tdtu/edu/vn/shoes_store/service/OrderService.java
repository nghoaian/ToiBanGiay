package tdtu.edu.vn.shoes_store.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.dto.OrderDto;
import tdtu.edu.vn.shoes_store.model.Order;

import java.util.List;

@Service
public interface OrderService {



    List<Order> findOrderByEmail(String email);
    List<OrderDto> getAllOrders();

    void deleteOrder(Long id);
}
