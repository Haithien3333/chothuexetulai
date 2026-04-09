package com.thuexe.thuexetulai.service;

import com.thuexe.thuexetulai.model.Booking;
import com.thuexe.thuexetulai.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private BookingRepository bookingRepository;

    public void autoCheckPayment() {

        List<Booking> list = bookingRepository.findAll();

        for (Booking b : list) {

            // giả lập: có mã là coi như đã cọc
            if (b.getPaymentCode() != null && !Boolean.TRUE.equals(b.getDepositPaid())) {
                b.setDepositPaid(true);
                b.setStatus("PAID_DEPOSIT");
            }

            // giả lập thanh toán đủ
            if (Boolean.TRUE.equals(b.getDepositPaid()) && !Boolean.TRUE.equals(b.getFullPaid())) {
                b.setFullPaid(true);
                b.setStatus("COMPLETED");
            }

            bookingRepository.save(b);
        }
    }
}