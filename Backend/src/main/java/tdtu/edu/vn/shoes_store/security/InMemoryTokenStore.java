package tdtu.edu.vn.shoes_store.security;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryTokenStore implements TokenStore{
    private final Set<String> tokenSet = ConcurrentHashMap.newKeySet();

    @Override
    public void storeToken(String token) {
        tokenSet.add(token);
    }

    @Override
    public void removeToken(String token) {
        tokenSet.remove(token);
    }

    @Override
    public boolean isValidToken(String token) {
        return tokenSet.contains(token);
    }

}
