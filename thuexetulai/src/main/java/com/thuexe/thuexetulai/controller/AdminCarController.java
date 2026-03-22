package com.thuexe.thuexetulai.controller;

import com.thuexe.thuexetulai.model.Car;
import com.thuexe.thuexetulai.model.User;
import com.thuexe.thuexetulai.repository.CarRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {

    @Autowired
    private CarRepository carRepository;

    // =============================
    // CHECK ADMIN
    // =============================
    private boolean isAdmin(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole());
    }

    // =============================
    // DANH SÁCH XE
    // =============================
    @GetMapping
    public String cars(Model model, HttpSession session){

        if(!isAdmin(session)){
            return "redirect:/login";
        }

        model.addAttribute("cars", carRepository.findAll());

        // ⚠️ vì bạn đặt tên carsadmin.html
        return "admin/carsadmin";
    }

    // =============================
    // FORM THÊM XE
    // =============================
    @GetMapping("/add")
    public String addForm(Model model, HttpSession session){

        if(!isAdmin(session)){
            return "redirect:/login";
        }

        model.addAttribute("car", new Car());

        return "admin/add-car";
    }

    // =============================
    // LƯU XE (THÊM / SỬA)
    // =============================
    @PostMapping("/save")
    public String save(@ModelAttribute Car car, HttpSession session){

        if(!isAdmin(session)){
            return "redirect:/login";
        }

        carRepository.save(car);

        return "redirect:/admin/cars";
    }

    // =============================
    // FORM SỬA XE
    // =============================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       Model model,
                       HttpSession session){

        if(!isAdmin(session)){
            return "redirect:/login";
        }

        Car car = carRepository.findById(id).orElse(null);

        if(car == null){
            return "redirect:/admin/cars";
        }

        model.addAttribute("car", car);

        return "admin/edit-car";
    }

    // =============================
    // XÓA XE
    // =============================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         HttpSession session){

        if(!isAdmin(session)){
            return "redirect:/login";
        }

        carRepository.deleteById(id);

        return "redirect:/admin/cars";
    }
}