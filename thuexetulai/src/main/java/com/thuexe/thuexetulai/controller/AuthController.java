package com.thuexe.thuexetulai.controller;

import com.thuexe.thuexetulai.model.User;
import com.thuexe.thuexetulai.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ======================
    // PAGE LOGIN
    // ======================
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // ======================
    // PAGE REGISTER
    // ======================
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // ======================
    // REGISTER
    // ======================
    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                           @RequestParam String confirmPassword,
                           Model model) {

        // kiểm tra email tồn tại
        User exist = userRepository.findByEmail(user.getEmail());

        if (exist != null) {
            model.addAttribute("error", "Email đã tồn tại");
            return "register";
        }

        // kiểm tra confirm password
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu không khớp");
            return "register";
        }

        // set role mặc định
        user.setRole("USER");

        userRepository.save(user);

        return "redirect:/login";
    }

    // ======================
    // LOGIN
    // ======================
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userRepository.findByEmailAndPassword(email, password);

        // login sai
        if (user == null) {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng");
            return "login";
        }

        // lưu session
        session.setAttribute("user", user);

        // debug (in ra console)
        System.out.println("LOGIN SUCCESS: " + user.getEmail() + " | ROLE: " + user.getRole());

        // phân quyền
        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin/cars"; // sửa lại chuẩn hơn
        }

        return "redirect:/";
    }

    // ======================
    // LOGOUT
    // ======================
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}