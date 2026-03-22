package com.thuexe.thuexetulai.config;

import com.thuexe.thuexetulai.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String uri = request.getRequestURI();

        // chưa login
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }

        // không phải admin mà vào admin
        if (uri.startsWith("/admin") && !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}