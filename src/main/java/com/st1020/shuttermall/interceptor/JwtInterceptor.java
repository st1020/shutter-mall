package com.st1020.shuttermall.interceptor;

import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            response.getWriter().write("No token");
            return false;
        }
        Claims claims = JwtUtil.verifyToken(token);
        if (claims == null) {
            response.setStatus(401);
            response.getWriter().write("Illegal token");
            return false;
        }
        if (claims.getExpiration().toInstant().isBefore(new Date().toInstant())) {
            response.setStatus(401);
            response.getWriter().write("Expired token");
            return false;
        }
        Long id = Long.valueOf((String) claims.get("id"));
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            response.setStatus(401);
            response.getWriter().write("UnKnow user");
            return false;
        }
        request.setAttribute("user", user.get());
        return true;
    }
}
