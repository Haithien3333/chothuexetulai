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

        // DEBUG (xem log console)
        System.out.println("➡️ URI: " + uri);
        System.out.println("➡️ USER: " + user);

        // ================= CHƯA LOGIN =================
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }

        // ================= CHECK ADMIN =================
        if (uri.startsWith("/admin")) {

            String role = user.getRole();

            // FIX: chống lỗi null + khoảng trắng + viết hoa/thường
            if (role == null || !role.trim().equalsIgnoreCase("ADMIN")) {
                response.sendRedirect("/");
                return false;
            }
        }

        return true;
    }
}