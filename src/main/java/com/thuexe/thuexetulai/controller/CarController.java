package com.thuexe.thuexetulai.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.thuexe.thuexetulai.model.Car;
import com.thuexe.thuexetulai.repository.BookingRepository;
import com.thuexe.thuexetulai.repository.CarRepository;

@Controller
public class CarController {

    private static final List<String> ACTIVE_BOOKING_STATUSES = List.of("PENDING", "APPROVED", "PAID");

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Value("${app.payment.bank-name:Ngân hàng}")
    private String paymentBankName;
    @Value("${app.payment.account-holder:THUE XE TU LAI}")
    private String paymentAccountHolder;
    @Value("${app.payment.account-number:0000000000}")
    private String paymentAccountNumber;


    @GetMapping("/delivery")
    public String showDelivery() {
        return "delivery";
    }
    @GetMapping("/")
    public String home(Model model) {
        LocalDate today = LocalDate.now();
        Set<Long> bookedCarIds = new HashSet<>(
                bookingRepository.findDistinctCarIdsWithActiveBooking(ACTIVE_BOOKING_STATUSES, today));
        List<Car> cars = carRepository.findAll().stream()
                .filter(c -> !bookedCarIds.contains(c.getId()))
                .toList();
        model.addAttribute("cars", cars);
        return "index";
    }

    @GetMapping("/cars")
    public String getCars(Model model) {
        LocalDate today = LocalDate.now();
        Set<Long> bookedCarIds = new HashSet<>(
                bookingRepository.findDistinctCarIdsWithActiveBooking(ACTIVE_BOOKING_STATUSES, today));
        List<Car> cars = carRepository.findAll().stream()
                .filter(c -> !bookedCarIds.contains(c.getId()))
                .toList();
        model.addAttribute("cars", cars);
        return "cars";
    }

    @GetMapping("/available-cars")
    public String getAvailableCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sortBy,
            Model model) {
        LocalDate today = LocalDate.now();
        Set<Long> bookedCarIds = new HashSet<>(
                bookingRepository.findDistinctCarIdsWithActiveBooking(ACTIVE_BOOKING_STATUSES, today));
        
        List<Car> cars = carRepository.findAll().stream()
                .filter(c -> !bookedCarIds.contains(c.getId()))
                .filter(c -> brand == null || brand.isEmpty() || c.getBrand().toLowerCase().contains(brand.toLowerCase()))
                .filter(c -> name == null || name.isEmpty() || c.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
        
        // Sắp xếp
        if (sortBy != null && !sortBy.isEmpty()) {
            cars = switch(sortBy) {
                case "price-asc" -> cars.stream().sorted((a, b) -> Double.compare(a.getPrice(), b.getPrice())).toList();
                case "price-desc" -> cars.stream().sorted((a, b) -> Double.compare(b.getPrice(), a.getPrice())).toList();
                case "name" -> cars.stream().sorted((a, b) -> a.getName().compareTo(b.getName())).toList();
                default -> cars;
            };
        }
        
        model.addAttribute("cars", cars);
        return "available-cars";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }

    @GetMapping("/car/{id}")
    public String carDetail(@PathVariable Long id, Model model) {

        Car car = carRepository.findById(id).orElse(null);

        model.addAttribute("car", car);
        boolean unavailable = car != null && bookingRepository.existsByCarIdAndStatusInAndEndDateGreaterThanEqual(
                id, ACTIVE_BOOKING_STATUSES, LocalDate.now());
        model.addAttribute("carUnavailable", unavailable);

        return "car-detail";
    }

    @GetMapping("/car/{id}/hop-dong")
    public String rentalContract(@PathVariable Long id, Model model) {
        Car car = carRepository.findById(id).orElse(null);
        if (car == null) {
            return "redirect:/";
        }
        if (bookingRepository.existsByCarIdAndStatusInAndEndDateGreaterThanEqual(
                id, ACTIVE_BOOKING_STATUSES, LocalDate.now())) {
            return "redirect:/car/" + id;
        }
        model.addAttribute("car", car);
        model.addAttribute("paymentBankName", paymentBankName);
        model.addAttribute("paymentAccountHolder", paymentAccountHolder);
        model.addAttribute("paymentAccountNumber", paymentAccountNumber);
        return "rental-contract";
    }
}