package tdtu.edu.vn.shoes_store.security.jwt;

import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        JSONObject errorResponse = new JSONObject();
        errorResponse.put("statusCode", response.SC_UNAUTHORIZED);
        errorResponse.put("timeStamp", LocalTime.now());
        errorResponse.put("message", request.getAttribute("message"));
        errorResponse.put("path", request.getServletPath());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(errorResponse.toString());

    }
}
