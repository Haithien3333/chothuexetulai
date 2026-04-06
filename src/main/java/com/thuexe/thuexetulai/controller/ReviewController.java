package com.thuexe.thuexetulai.controller;

import com.thuexe.thuexetulai.model.*;
import com.thuexe.thuexetulai.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CarRepository carRepo;

    @PostMapping("/{carId}")
    public String add(@PathVariable Long carId,
                      @RequestBody Review review,
                      HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) return "NOT_LOGIN";

        Car car = carRepo.findById(carId).orElseThrow();

        review.setUser(user);
        review.setCar(car);

        reviewRepo.save(review);

        return "OK";
    }

    // lấy review theo xe
    @GetMapping("/car/{carId}")
    public List<Review> getByCar(@PathVariable Long carId) {

        Car car = carRepo.findById(carId).orElseThrow();
        return reviewRepo.findByCar(car);
    }
}