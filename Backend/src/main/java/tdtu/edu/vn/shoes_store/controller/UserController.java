package tdtu.edu.vn.shoes_store.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.edu.vn.shoes_store.dto.OrderDto;
import tdtu.edu.vn.shoes_store.dto.ProductOrderDto;
import tdtu.edu.vn.shoes_store.dto.UserDto;
import tdtu.edu.vn.shoes_store.model.Order;
import tdtu.edu.vn.shoes_store.model.OrderDetail;
import tdtu.edu.vn.shoes_store.model.User;
import tdtu.edu.vn.shoes_store.service.OrderService;
import tdtu.edu.vn.shoes_store.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    @GetMapping
    public  ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDtoList = userService.getAllUser();
        if(userDtoList != null){
            return  ResponseEntity.ok(userDtoList);
        }
        return  ResponseEntity.notFound().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserByToken(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String token = request.getHeader("Authorization").substring(7);

        UserDto userDto = userService.findUserByToken(token);
        if (userDto == null) {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("timeStamp", LocalTime.now());
            result.put("message", "User not found!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseEntity.ok(userDto);
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable(name = "id") Long id) {
        Map<String, Object> result = new HashMap<>();

        UserDto userDtotmp = userService.findUserByID(id);

        User user = userService.findUserByEmail(userDtotmp.getEmail());
        if (user != null) {
            List<Order> orderList = orderService.findOrderByEmail(userDtotmp.getEmail());
            UserDto userDto = getUserDtoBody(user, orderList);
            result.put("statusCode", HttpStatus.OK.value());
            result.put("timeStamp", LocalTime.now());
            result.put("message", "Get profile successfully!");
            result.put("content", userDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("timeStamp", LocalTime.now());
            result.put("message", "User not found!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
//        UserDto updatedUser = userService.updateUserByID(id, userDto);
//        if (updatedUser != null) {
//            return ResponseEntity.ok(updatedUser);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    private UserDto getUserDtoBody(User user, List<Order> orderList) {
        UserDto userDto = new UserDto();

        if (user.getId() != null) userDto.setId(user.getId());
        if (user.getEmail() != null) userDto.setEmail(user.getEmail());
        if (user.getUsername() != null) userDto.setUsername(user.getUsername());
        if (user.getGender() != null) userDto.setGender(user.getGender());
        if (user.getPhone() != null) userDto.setPhone(user.getPhone());
        if (user.getAddress() != null) userDto.setAddress(user.getAddress());
        if (user.getAge() != null) userDto.setAge(user.getAge());
        if (user.getPassword() != null) userDto.setPassword(user.getPassword());

        userDto.setOrders(getListOrder(orderList));

        return userDto;
    }

    private List<OrderDto> getListOrder(List<Order> orders) {
        List<OrderDto> listOrder = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = getOrderDtoBody(order);
            listOrder.add(orderDto);
        }
        return listOrder;
    }
    private OrderDto getOrderDtoBody(Order order) {
        OrderDto orderDto = new OrderDto();

        if (order.getId() != null) orderDto.setId(order.getId());
        if (order.getEmail() != null) orderDto.setEmail(order.getEmail());
        if (order.getAddress() != null) orderDto.setAddress(order.getAddress());
        if (order.getDate() != null) orderDto.setDate(order.getDate());
        if (order.getPayment() != null) orderDto.setPayment(order.getPayment());
        if (order.getStatus() != null) orderDto.setStatus(order.getStatus());
        orderDto.setTotalPrice(order.getTotalPrice());

        List<ProductOrderDto> productDtoList = orderDto.getProducts();

        for (OrderDetail orderDetail : order.getOrderDetail()) {
            ProductOrderDto productOrders = new ProductOrderDto();

            productOrders.setName(orderDetail.getProduct().getName());
            productOrders.setDescription(orderDetail.getProduct().getDescription());
            productOrders.setImage(orderDetail.getProduct().getImage());
            productOrders.setSize(orderDetail.getSize());
            productOrders.setQuantity(orderDetail.getQuantity());
            productOrders.setPrice(orderDetail.getProduct().getPrice());

            productDtoList.add(productOrders);
        }

        orderDto.setProducts(productDtoList);
        return orderDto;
    }


    @PostMapping("")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
        Map<String, Object> result = new HashMap<>();
        userService.addUser(userDto);

        result.put("statusCode", HttpStatus.OK.value());
        result.put("message", "Create new user successfully!");
        result.put("content", userDto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        if(!userService.deleteUserByID(id)){
            result.put("statusCode", HttpStatus.NO_CONTENT.value());
            result.put("message", "Delete user fail!");
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }else {
            result.put("statusCode", HttpStatus.OK.value());
            result.put("message", "Delete user successfully!");
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUserByID(id, userDto);
        Map<String, Object> result = new HashMap<>();
        if (updatedUser != null) {
            result.put("statusCode", HttpStatus.OK.value());
            result.put("message","Update user successfully!");
            result.put("content",updatedUser);
            return  new ResponseEntity<>(result, HttpStatus.OK);
        } else {

            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/updatePass")
    public ResponseEntity<?> updatePassword(HttpServletRequest request,@RequestBody Map<String, String> passwordMap){
        Map<String, Object> result = new HashMap<>();

        String token = request.getHeader("Authorization").substring(7);
        String passwordConfirm = passwordMap.get("passwordConfirm");
        String passwordNew = passwordMap.get("passwordNew");

        boolean match = match = userService.changePassword(token, passwordConfirm,passwordNew);
        if(match){
            result.put("statusCode", HttpStatus.OK.value());
            result.put("message","Update password successfully!");
            return  new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("message","Wrong password confirm!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserByToken(HttpServletRequest request, @RequestBody UserDto userDto) {
        String token = request.getHeader("Authorization").substring(7);

        UserDto updatedUser = userService.updateUserByToken(token, userDto,userDto.getPasswordConfirm());
        Map<String, Object> result = new HashMap<>();
        if (updatedUser != null) {
            result.put("statusCode", HttpStatus.OK.value());
            result.put("message","Update user successfully!");
            result.put("content",updatedUser);
            return  new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("statusCode", HttpStatus.NOT_FOUND.value());
            result.put("timeStamp", LocalTime.now());
            result.put("message", "User not found or Wrong password Confirm!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}
