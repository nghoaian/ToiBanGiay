package tdtu.edu.vn.shoes_store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String gender;
    private String phone;

    private Integer age;
    private String address;
    private Long role;

    private List<OrderDto> orders = new ArrayList<>();

    private String passwordConfirm;
    @Builder
    public UserDto(Long id, String address, int age,String username, String password, String email, String gender, String phone, Long role,String passwordConfirm) {
        this.id = id;
        this.address = address;
        this.age = age;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
        this.passwordConfirm = passwordConfirm;
    }
}
