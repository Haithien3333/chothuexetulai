package com.thuexe.thuexetulai.controller;

import com.thuexe.thuexetulai.repository.CarRepository;   // THÊM DÒNG NÀY
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("cars", carRepository.findAll());

        return "index";
    }
}