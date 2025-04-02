package tdtu.edu.vn.shoes_store.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.dto.UserDto;
import tdtu.edu.vn.shoes_store.model.User;

import java.util.List;

@Service
public interface UserService {
    void registerUser(UserDto userDto);
    User findUserByEmail(String email);
    UserDto findUserByID(Long id);
    UserDto updateUserByID(Long id,UserDto userDto);
    UserDto updateUserByToken(String token,UserDto userDto,String passwordConfirm);
    List<UserDto> getAllUser();

    UserDto addUser(UserDto userDto);

    boolean deleteUserByID(Long id);

    UserDto findUserByToken(String token);

    boolean changePassword(String token,String passwordConfirm,String passwordNew);

}
