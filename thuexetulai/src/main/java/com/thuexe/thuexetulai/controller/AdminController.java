package com.thuexe.thuexetulai.controller;

import com.thuexe.thuexetulai.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {



    @GetMapping("/admin")
    public String adminPage(HttpSession session){

        User user = (User) session.getAttribute("user");

        // chưa login
        if(user == null){
            return "redirect:/login";
        }

        // không phải admin
        if(!"ADMIN".equals(user.getRole())){
            return "redirect:/";
        }

        return "admin"; // phải có file admin.html
    }
}