package com.thuexe.thuexetulai.config;

import com.thuexe.thuexetulai.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private PaymentService paymentService;

    @Scheduled(fixedRate = 10000) // 10 giây check
    public void autoCheck() {
        paymentService.autoCheckPayment();
    }
}