package tdtu.edu.vn.shoes_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tdtu.edu.vn.shoes_store.dto.OrderDto;
import tdtu.edu.vn.shoes_store.dto.ProductDto;
import tdtu.edu.vn.shoes_store.dto.ProductOrderDto;
import tdtu.edu.vn.shoes_store.dto.UserDto;
import tdtu.edu.vn.shoes_store.model.Order;
import tdtu.edu.vn.shoes_store.model.OrderDetail;
import tdtu.edu.vn.shoes_store.model.Product;
import tdtu.edu.vn.shoes_store.model.User;
import tdtu.edu.vn.shoes_store.security.TokenStore;
import tdtu.edu.vn.shoes_store.security.jwt.JwtRequest;
import tdtu.edu.vn.shoes_store.security.jwt.JwtTokenUtil;
import tdtu.edu.vn.shoes_store.service.OrderService;
import tdtu.edu.vn.shoes_store.service.UserDetailsServiceImpl;
import tdtu.edu.vn.shoes_store.service.UserService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody UserDto userDto) {
        Map<String, Object> result = new HashMap<>();

        if (userDto.getEmail() == null) {
            return new ResponseEntity<>("Email is required!", HttpStatus.BAD_REQUEST);
        } else if (userDto.getPassword() == null) {
            return new ResponseEntity<>("Password is required!", HttpStatus.BAD_REQUEST);
        } else if (userDto.getUsername() == null) {
            return new ResponseEntity<>("Username is required!", HttpStatus.BAD_REQUEST);
        } else if (userDto.getGender() == null) {
            return new ResponseEntity<>("Gender is required!", HttpStatus.BAD_REQUEST);
        } else if (userDto.getPhone() == null) {
            return new ResponseEntity<>("Phone is required!", HttpStatus.BAD_REQUEST);
        } else if (userService.findUserByEmail(userDto.getEmail()) != null) {
            return new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST);
        }

        userService.registerUser(userDto);
        result.put("statusCode", HttpStatus.OK.value());
        result.put("timeStamp", LocalTime.now());
        result.put("message", "Register account successfully!");
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("Your account has been disabled. Please contact the system administrator for assistance.", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid email or password.", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody JwtRequest authenticationRequest)
            throws Exception {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> content = new HashMap<>();

        if (authenticationRequest.getEmail() == null) {
            return new ResponseEntity<>("Email is required!", HttpStatus.BAD_REQUEST);
        } else if (authenticationRequest.getPassword() == null) {
            return new ResponseEntity<>("Password is required!", HttpStatus.BAD_REQUEST);
        }

        try {
            authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            tokenStore.storeToken(token);

            content.put("email", userDetails.getUsername());
            content.put("accessToken", token);

            result.put("statusCode", HttpStatus.OK.value());
            result.put("timeStamp", LocalTime.now());
            result.put("message", "Login successfully!");
            result.put("content", content);
        } catch (Exception e) {
            result.put("statusCode", HttpStatus.UNAUTHORIZED.value());
            result.put("timeStamp", LocalTime.now());
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<Object> loginAdmin(@RequestBody JwtRequest authenticationRequest)
            throws Exception {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> content = new HashMap<>();

        if (authenticationRequest.getEmail() == null) {
            return new ResponseEntity<>("Email is required!", HttpStatus.BAD_REQUEST);
        } else if (authenticationRequest.getPassword() == null) {
            return new ResponseEntity<>("Password is required!", HttpStatus.BAD_REQUEST);
        }

        try {
            authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            if (userDetails.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                throw new Exception("You are not allowed to login!");
            }

            tokenStore.storeToken(token);

            content.put("email", userDetails.getUsername());
            content.put("accessToken", token);

            result.put("statusCode", HttpStatus.OK.value());
            result.put("timeStamp", LocalTime.now());
            result.put("message", "Login successfully!");
            result.put("content", content);
        } catch (Exception e) {
            result.put("statusCode", HttpStatus.UNAUTHORIZED.value());
            result.put("timeStamp", LocalTime.now());
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logoutUser(@RequestHeader("Authorization") String authToken) {
        Map<String, Object> result = new HashMap<>();
        String token = authToken.replace("Bearer ", "");
        tokenStore.removeToken(token);

        result.put("statusCode", HttpStatus.OK.value());
        result.put("timeStamp", LocalTime.now());
        result.put("message", "Logged out successfully!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("getProfile")
    public ResponseEntity<Object> getProfile(@RequestHeader("Authorization") String authToken) {
        Map<String, Object> result = new HashMap<>();
        String token = authToken.replace("Bearer ", "");
        String email = jwtTokenUtil.getUsernameFromToken(token);

        User user = userService.findUserByEmail(email);
        if (user != null) {
            List<Order> orderList = orderService.findOrderByEmail(email);
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


    private List<OrderDto> getListOrder(List<Order> orders) {
        List<OrderDto> listOrder = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = getOrderDtoBody(order);
            listOrder.add(orderDto);
        }
        return listOrder;
    }
}
