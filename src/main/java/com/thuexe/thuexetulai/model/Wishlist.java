package com.thuexe.thuexetulai.model;

import jakarta.persistence.*;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Car car;

    // getter setter
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
}