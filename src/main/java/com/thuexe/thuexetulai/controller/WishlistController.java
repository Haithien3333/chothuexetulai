package com.thuexe.thuexetulai.controller;

import com.thuexe.thuexetulai.model.*;
import com.thuexe.thuexetulai.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepo;

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/{carId}")
    public String add(@PathVariable Long carId, Principal principal) {

        if (principal == null) return "NOT_LOGIN";

        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        Car car = carRepo.findById(carId).orElseThrow();

        if (wishlistRepo.findByUserAndCar(user, car).isPresent()) {
            return "EXISTS";
        }

        Wishlist w = new Wishlist();
        w.setUser(user);
        w.setCar(car);

        wishlistRepo.save(w);

        return "OK";
    }

    @DeleteMapping("/{carId}")
    public String remove(@PathVariable Long carId, Principal principal) {

        if (principal == null) return "NOT_LOGIN";

        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        Car car = carRepo.findById(carId).orElseThrow();

        wishlistRepo.deleteByUserAndCar(user, car);

        return "OK";
    }

    @GetMapping
    public List<Wishlist> getAll(Principal principal) {

        if (principal == null) return List.of();

        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        return wishlistRepo.findByUser(user);
    }
}