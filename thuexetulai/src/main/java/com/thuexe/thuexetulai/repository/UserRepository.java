package com.thuexe.thuexetulai.repository;

import com.thuexe.thuexetulai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findByEmailAndPassword(String email,String password);

}