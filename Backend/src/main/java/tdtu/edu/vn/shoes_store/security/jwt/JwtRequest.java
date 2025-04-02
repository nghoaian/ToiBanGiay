package tdtu.edu.vn.shoes_store.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
    private String email;
    private String password;
}
