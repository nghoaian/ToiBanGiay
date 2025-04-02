package tdtu.edu.vn.shoes_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.dto.OrderDto;
import tdtu.edu.vn.shoes_store.model.Order;
import tdtu.edu.vn.shoes_store.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transient
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setPayment(order.getPayment());
            orderDto.setDate(order.getDate());
            orderDto.setEmail(order.getEmail());
//            orderDto.setOrderDetail(order.getOrderDetail());

            orderDto.setStatus(order.getStatus());
            orderDto.setAddress(order.getAddress());
            orderDto.setTotalPrice(order.getTotalPrice());
            orderDto.setDelivery(order.getDelivery());
            orderDtos.add(orderDto);

        }
        return orderDtos;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    @Override
    public List<Order> findOrderByEmail(String email) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getEmail().equals(email)).toList();
    }

}
