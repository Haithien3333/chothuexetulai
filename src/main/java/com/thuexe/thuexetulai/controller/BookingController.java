package com.thuexe.thuexetulai.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.thuexe.thuexetulai.dto.BookingHistoryView;
import com.thuexe.thuexetulai.model.Booking;
import com.thuexe.thuexetulai.model.User;
import com.thuexe.thuexetulai.repository.BookingRepository;
import com.thuexe.thuexetulai.repository.CarRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookingController {

    private static final List<String> ACTIVE_BOOKING_STATUSES = List.of("PENDING", "APPROVED", "PAID");

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/booking-success")
    public String success(){
        return "booking-success";
    }



    @GetMapping("/booking/{carId}")
    public String bookCar(
            @PathVariable Long carId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer depositPercent,
            @RequestParam(required = false) String depositMethod,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (bookingRepository.existsByCarIdAndStatusInAndEndDateGreaterThanEqual(
                carId, ACTIVE_BOOKING_STATUSES, LocalDate.now())) {
            return "redirect:/car/" + carId;
        }

        Booking booking = new Booking();
        booking.setCarId(carId);
        booking.setUserId(user.getId());

        if (startDate != null && !startDate.isBlank()) {
            try { booking.setStartDate(LocalDate.parse(startDate)); } catch (Exception ignored) {}
        }
        if (endDate != null && !endDate.isBlank()) {
            try { booking.setEndDate(LocalDate.parse(endDate)); } catch (Exception ignored) {}
        }
        if (booking.getStartDate() == null) booking.setStartDate(LocalDate.now());
        if (booking.getEndDate() == null) booking.setEndDate(booking.getStartDate().plusDays(1));

        booking.setStatus("PENDING");
        // ====== THÊM QR PAYMENT ======
        String paymentCode = "COC-" + System.currentTimeMillis();
        booking.setPaymentCode(paymentCode);


        if (depositPercent != null && (depositPercent == 20 || depositPercent == 50 || depositPercent == 70)) {
            booking.setDepositPercent(depositPercent);
        }
        if (depositMethod != null && ("CASH".equalsIgnoreCase(depositMethod) || "TRANSFER".equalsIgnoreCase(depositMethod))) {
            booking.setDepositMethod(depositMethod.toUpperCase());
        }
        bookingRepository.save(booking);

        return "redirect:/booking-success";
    }

    @GetMapping("/payment/{id}")
    public String payment(@PathVariable Long id){

        Booking b = bookingRepository.findById(id).orElse(null);

        if(b != null){
            b.setStatus("PAID");
            bookingRepository.save(b);
        }

        return "redirect:/booking/history";
    }

    @GetMapping("/complete-booking/{id}")
    public String completeBooking(@PathVariable Long id, HttpSession session){

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Booking b = bookingRepository.findById(id).orElse(null);

        if(b != null && b.getUserId().equals(user.getId())){
            b.setStatus("COMPLETED");
            bookingRepository.save(b);
        }

        return "redirect:/booking/history";
    }
    @GetMapping("/booking/history")
    public String history(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<BookingHistoryView> rows = bookingRepository.findAll().stream()
                .filter(b -> b.getUserId().equals(user.getId()))
                .sorted(Comparator.comparing(Booking::getId).reversed())
                .map(b -> new BookingHistoryView(b, carRepository.findById(b.getCarId()).orElse(null)))
                .toList();

        model.addAttribute("rows", rows);

        return "history";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<BookingHistoryView> bookings = bookingRepository.findAll().stream()
                .filter(b -> b.getUserId().equals(user.getId()))
                .sorted(Comparator.comparing(Booking::getId).reversed())
                .map(b -> new BookingHistoryView(b, carRepository.findById(b.getCarId()).orElse(null)))
                .toList();

        model.addAttribute("bookings", bookings);
        return "profile";
    }

}