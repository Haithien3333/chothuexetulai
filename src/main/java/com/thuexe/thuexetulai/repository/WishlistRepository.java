package com.thuexe.thuexetulai.repository;

import com.thuexe.thuexetulai.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    Optional<Wishlist> findByUserAndCar(User user, Car car);

    void deleteByUserAndCar(User user, Car car);
}