package com.st1020.shuttermall.interceptor;

import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.exception.TokenException;
import com.st1020.shuttermall.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.st1020.shuttermall.utils.JwtUtil;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    final private UserRepository userRepository;

    public JwtInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws TokenException {
        final String token = request.getHeader("Authorization");
        if (token == null) {
            throw new TokenException("No Token");
        }
        Claims claims = JwtUtil.verifyToken(token);
        if (claims == null) {
            throw new TokenException("Illegal Token");
        }
        if (claims.getExpiration().toInstant().isBefore(new Date().toInstant())) {
            throw new TokenException("Expired Token");
        }
        Long id = Long.valueOf((String) claims.get("id"));
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new TokenException("Unknown User");
        }
        request.setAttribute("user", user.get());
        return true;
    }
}
