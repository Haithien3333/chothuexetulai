package com.thuexe.thuexetulai.controller;

import com.thuexe.thuexetulai.model.Car;
import com.thuexe.thuexetulai.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CarController {

    @Autowired
    private CarRepository carRepository;

    // ================== USER ==================

    // Trang chủ (user xem xe)
    @GetMapping("/")
    public String home(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "index";
    }
}