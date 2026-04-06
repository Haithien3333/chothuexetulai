package com.thuexe.thuexetulai.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.thuexe.thuexetulai.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String uri = request.getRequestURI();

        System.out.println("➡️ URI: " + uri);
        System.out.println("➡️ USER: " + user);

        // 🔥 CHO PHÉP API (QUAN TRỌNG NHẤT)
        if (uri.startsWith("/reviews") || uri.startsWith("/wishlist")) {
            return true;
        }

        // ================= CHƯA LOGIN =================
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }

        // ================= CHECK ADMIN =================
        if (uri.startsWith("/admin")) {

            String role = user.getRole();

            if (role == null || !role.trim().equalsIgnoreCase("ADMIN")) {
                response.sendRedirect("/");
                return false;
            }
        }

        return true;
    }
}