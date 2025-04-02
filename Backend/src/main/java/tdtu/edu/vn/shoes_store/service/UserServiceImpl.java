package tdtu.edu.vn.shoes_store.service;

import org.springframework.security.core.Transient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tdtu.edu.vn.shoes_store.dto.UserDto;
import tdtu.edu.vn.shoes_store.model.Role;
import tdtu.edu.vn.shoes_store.model.User;
import tdtu.edu.vn.shoes_store.repository.RoleRepository;
import tdtu.edu.vn.shoes_store.repository.UserRepository;
import tdtu.edu.vn.shoes_store.security.jwt.JwtTokenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transient
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public void registerUser(UserDto userToRegister) {
        User user = new User();
        user.setUsername(userToRegister.getUsername());
        user.setEmail(userToRegister.getEmail());
        user.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
        user.setPhone(userToRegister.getPhone());
        user.setGender(userToRegister.getGender());

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRole(role);

        userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto findUserByID(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDto userDto = new UserDto();
            userDto.setId(user.get().getId());
            userDto.setAddress(user.get().getAddress());
            userDto.setAge(user.get().getAge());
            userDto.setEmail(user.get().getEmail());
            userDto.setGender(user.get().getGender());
            userDto.setPassword(user.get().getPassword());
            userDto.setPhone(user.get().getPhone());
            userDto.setUsername(user.get().getUsername());
            userDto.setRole(user.get().getRole().getId());
            return userDto;
        }
        return null;
    }



    @Override
    public UserDto updateUserByID(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setAddress(userDto.getAddress());
            user.setAge(userDto.getAge());
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            user.setGender(userDto.getGender());
//            user.setPassword(userDto.getPassword());
            user.setPhone(userDto.getPhone());
//            Optional<Role> role = roleRepository.findById(userDto.getRole()); // tìm kiếm role trong cơ sở dữ liệu
//            if (!role.isPresent()) { // nếu không tìm thấy role, trả về null
//                return null;
//            }
            User updatedUser = userRepository.save(user);
            UserDto updatedUserDto = new UserDto();
            updatedUserDto.setId(id);
            updatedUserDto.setAddress(updatedUser.getAddress());
            updatedUserDto.setAge(updatedUser.getAge());
            updatedUserDto.setEmail(updatedUser.getEmail());
            updatedUserDto.setGender(updatedUser.getGender());
            updatedUserDto.setPassword(updatedUser.getPassword());
            updatedUserDto.setPhone(updatedUser.getPhone());
            updatedUserDto.setUsername(updatedUser.getUsername());
            updatedUserDto.setRole(updatedUser.getRole().getId());
            return updatedUserDto;
        }
        return null;
    }

    @Override
    public UserDto updateUserByToken(String token, UserDto userDto,String passwordConfirm) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
//        System.out.print("username ne:"+username);
        Optional<User> optionalUser = userRepository.findUsersByEmail(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(passwordEncoder.matches(passwordConfirm, user.getPassword())) {

                user.setAddress(userDto.getAddress());
                user.setAge(userDto.getAge());
                user.setEmail(userDto.getEmail());
                user.setGender(userDto.getGender());
                user.setUsername(userDto.getUsername());
//            user.setPassword(userDto.getPassword());
                user.setPhone(userDto.getPhone());
                User updatedUser = userRepository.save(user);

                UserDto updatedUserDto = new UserDto();

                updatedUserDto.setAddress(updatedUser.getAddress());
                updatedUserDto.setAge(updatedUser.getAge());
                updatedUserDto.setEmail(updatedUser.getEmail());
                updatedUserDto.setGender(updatedUser.getGender());
                updatedUserDto.setPhone(updatedUser.getPhone());
                updatedUserDto.setUsername(updatedUser.getUsername());

                return updatedUserDto;
            }
            else {
                return null;
            }
        }
        return null;
    }


    @Override
    public List<UserDto> getAllUser(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setAddress(user.getAddress());
            userDto.setAge(user.getAge());
            userDto.setEmail(user.getEmail());
            userDto.setGender(user.getGender());
            userDto.setPassword(user.getPassword());
            userDto.setPhone(user.getPhone());
            userDto.setUsername(user.getUsername());
            userDto.setRole(user.getRole().getId());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = new User();
        user.setAddress(userDto.getAddress());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // mã hóa password trước khi lưu vào cơ sở dữ liệu
        user.setPhone(userDto.getPhone());
        user.setUsername(userDto.getUsername());
        Optional<Role> role = roleRepository.findById(userDto.getRole()); // tìm kiếm role trong cơ sở dữ liệu
        if (!role.isPresent()) { // nếu không tìm thấy role, trả về null
            return null;
        }
        user.setRole(role.get()); // gán role cho user
        userRepository.save(user);
        return userDto;
    }

    @Override
    public boolean deleteUserByID(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;

    }

    @Override
    public UserDto findUserByToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
//        System.out.print("username ne:"+username);
        Optional<User> user = userRepository.findUsersByEmail(username);
        if (user.isPresent()) {
            UserDto userDto = new UserDto();
            userDto.setId(user.get().getId());
            userDto.setAddress(user.get().getAddress());
            userDto.setAge(user.get().getAge());
            userDto.setEmail(user.get().getEmail());
            userDto.setGender(user.get().getGender());
//            userDto.setPassword(user.get().getPassword());
            userDto.setPhone(user.get().getPhone());
            userDto.setUsername(user.get().getUsername());
            userDto.setRole(user.get().getRole().getId());
            return userDto;
        }
        return null;
    }

    @Override
    public boolean changePassword(String token, String passwordConfirm, String passwordNew) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Optional<User> optionalUser = userRepository.findUsersByEmail(username);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(passwordEncoder.matches(passwordConfirm,user.getPassword())){

                user.setPassword(passwordEncoder.encode(passwordNew));
                userRepository.save(user);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }


}
