package com.thuexe.thuexetulai.dto;

import com.thuexe.thuexetulai.model.Booking;
import com.thuexe.thuexetulai.model.Car;

/** Một dòng lịch sử: đặt xe + thông tin xe (nếu còn trong hệ thống). */
public class BookingHistoryView {

    private final Booking booking;
    private final Car car;

    public BookingHistoryView(Booking booking, Car car) {
        this.booking = booking;
        this.car = car;
    }

    public Booking getBooking() {
        return booking;
    }

    public Car getCar() {
        return car;
    }
}
