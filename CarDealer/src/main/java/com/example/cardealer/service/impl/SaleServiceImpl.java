package com.example.cardealer.service.impl;

import com.example.cardealer.model.entity.Car;
import com.example.cardealer.model.entity.Customer;
import com.example.cardealer.model.entity.Sale;
import com.example.cardealer.repository.CustomerRepository;
import com.example.cardealer.repository.SaleRepository;
import com.example.cardealer.service.CarService;
import com.example.cardealer.service.CustomerService;
import com.example.cardealer.service.SaleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SaleServiceImpl implements SaleService {
    private final CustomerService customerService;
    private final CarService carService;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public SaleServiceImpl(CustomerService customerService, CarService carService, CustomerRepository customerRepository, SaleRepository saleRepository) {
        this.customerService = customerService;
        this.carService = carService;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public void seedData() {
        if (saleRepository.count() > 0) {
            return;
        }
        long count = customerRepository.count();
        for (long i = 1; i <= count; i++) {
            Sale sale = new Sale();
            Customer customer = customerRepository.getById(i);
            Car car = carService.getRandomCar();
            sale.setCustomer(customer);
            try {

                sale.setCar(car);
            } catch (Throwable e) {
                i--;
            }
            BigDecimal[] discounts = new BigDecimal[]{
                    BigDecimal.valueOf(0.0),
                    BigDecimal.valueOf(0.05),
                    BigDecimal.valueOf(0.1),
                    BigDecimal.valueOf(0.15),
                    BigDecimal.valueOf(0.20),
                    BigDecimal.valueOf(0.30),
                    BigDecimal.valueOf(0.40),
                    BigDecimal.valueOf(0.50),
            };
            int random = ThreadLocalRandom.current().nextInt(0, discounts.length);
            BigDecimal discount = discounts[random];

            sale.setDiscount(discount);

            saleRepository.save(sale);
        }
        addDiscountForYoungDriver();


    }


    private void addDiscountForYoungDriver() {
        List<Sale> sales = saleRepository.findAll();
        sales
                .forEach(sale -> {
                    if (sale.getCustomer().getYoungDriver()) {
                        sale
                                .setDiscount(sale.getDiscount().add(BigDecimal.valueOf(0.5)));
                        saleRepository.save(sale);
                    }

                });

    }

    @Override
    public List<Sale> getAllSales() {
        List<Sale> saleList = saleRepository.findAll();
        return saleList;
    }
}
