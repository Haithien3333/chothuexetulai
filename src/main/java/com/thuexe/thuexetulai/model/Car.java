package com.thuexe.thuexetulai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private int seats;
    private double price;

    @Column(name = "fuel_type")
    private String fuelType;

    private String transmission;
    private String image;

    public Car(){}

    // GETTER
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public int getSeats() { return seats; }
    public double getPrice() { return price; }
    public String getFuelType() { return fuelType; }
    public String getTransmission() { return transmission; }
    public String getImage() { return image; }

    // SETTER
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setSeats(int seats) { this.seats = seats; }
    public void setPrice(double price) { this.price = price; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setTransmission(String transmission) { this.transmission = transmission; }
    public void setImage(String image) { this.image = image; }
}