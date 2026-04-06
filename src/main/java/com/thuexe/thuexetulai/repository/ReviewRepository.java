package com.thuexe.thuexetulai.repository;

import com.thuexe.thuexetulai.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCar(Car car);

}