package com.thuexe.thuexetulai.dto;

import com.thuexe.thuexetulai.model.Booking;
import com.thuexe.thuexetulai.model.Car;
import com.thuexe.thuexetulai.model.User;

/** Hiển thị booking đã hoàn thành kèm thông tin xe và khách hàng. */
public class CompletedBookingView {

    private final Booking booking;
    private final Car car;
    private final User user;

    public CompletedBookingView(Booking booking, Car car, User user) {
        this.booking = booking;
        this.car = car;
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }

    public Car getCar() {
        return car;
    }

    public User getUser() {
        return user;
    }
}
