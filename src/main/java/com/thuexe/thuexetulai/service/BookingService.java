package com.thuexe.thuexetulai.service;

import com.thuexe.thuexetulai.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.thuexe.thuexetulai.repository.CarRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Double getTotalRevenue() {
        Double total = bookingRepository.getTotalRevenue();
        return total != null ? total : 0;
    }

    public Long getTotalCompletedBookings() {
        Long total = bookingRepository.countCompletedBookings();
        return total != null ? total : 0;
    }

    public Map<String, Double> getRevenueByDate() {
        List<Object[]> results = bookingRepository.getRevenueByDate();
        Map<String, Double> map = new LinkedHashMap<>();

        for (Object[] row : results) {
            Double value = row[1] != null ? (Double) row[1] : 0;
            map.put(row[0].toString(), value);
        }


        return map;
    }
    public Map<String, Double> getTopCars(CarRepository carRepository) {
        List<Object[]> list = bookingRepository.getTopCarsRevenue();
        Map<String, Double> map = new LinkedHashMap<>();

        for (Object[] row : list) {
            Long carId = (Long) row[0];
            Double revenue = row[1] != null ? (Double) row[1] : 0;

            String carName = carRepository.findById(carId)
                    .map(c -> c.getName())
                    .orElse("Xe #" + carId);

            map.put(carName, revenue);
        }

        return map;
    }

}
