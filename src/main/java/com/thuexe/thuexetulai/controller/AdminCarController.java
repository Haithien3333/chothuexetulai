package com.thuexe.thuexetulai.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thuexe.thuexetulai.model.Car;
import com.thuexe.thuexetulai.repository.CarRepository;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {

    @Autowired
    private CarRepository carRepository;



    // ================= DANH SÁCH =================
    @GetMapping
    public String listCars(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "admin/carsadmin";
    }

    // ================= THÊM =================
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("car", new Car());
        return "admin/add-car";
    }

    @PostMapping("/save")
    public String saveCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/admin/cars";
    }

    // ================= SỬA =================
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        Optional<Car> optionalCar = carRepository.findById(id);

        if (optionalCar.isEmpty()) {
            return "redirect:/admin/cars";
        }

        model.addAttribute("car", optionalCar.get());

        return "admin/edit-car";
    }

    // ================= XÓA =================
    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {

        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        }

        return "redirect:/admin/cars";
    }


}