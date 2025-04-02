package tdtu.edu.vn.shoes_store.security;

import org.springframework.stereotype.Service;

@Service
public interface TokenStore {
    void storeToken(String token);
    void removeToken(String token);
    boolean isValidToken(String token);
}
