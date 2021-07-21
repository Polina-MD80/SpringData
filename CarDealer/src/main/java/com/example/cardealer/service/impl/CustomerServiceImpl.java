package com.example.cardealer.service.impl;

import com.example.cardealer.model.dto.CustomerOrderedCapitalDto;
import com.example.cardealer.model.dto.CustomerSeedDto;
import com.example.cardealer.model.entity.Customer;
import com.example.cardealer.repository.CustomerRepository;
import com.example.cardealer.service.CustomerService;
import com.example.cardealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.constants.GlobalApplicationConstants.FILE_PATH_READ;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(Gson gson, ValidationUtil validationUtil,
                               ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public void seedData() throws IOException {
        if (this.customerRepository.count() > 0) {
            return;
        }

        String data = Files.readString(Path.of(FILE_PATH_READ + "customers.json"));

        CustomerSeedDto[] customerSeedDtos = gson.fromJson(data, CustomerSeedDto[].class);

        Arrays.stream(customerSeedDtos)
                .filter(validationUtil::isValid)
                .map(customerSeedDto -> {
                    Customer customer = modelMapper.map(customerSeedDto, Customer.class);
                    customer.setBirthDate(LocalDateTime.parse(customerSeedDto.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                    return customer;
                })
                .forEach(customerRepository::save);
    }

    @Override
    public Customer getRandomCustomer() {
        long count = this.customerRepository.count();
        long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);

        return this.customerRepository.findById(randomId).orElse(null);

    }

    @Override
    public List<CustomerOrderedCapitalDto> getOrderedCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<Customer> orderedCustomers = customers.stream().sorted(Comparator.comparing(Customer::getBirthDate).thenComparing(Customer::getYoungDriver)).collect(Collectors.toList());
        List<CustomerOrderedCapitalDto> customerOrderedCapitalDtos = orderedCustomers.stream()
                .map(customer -> {
                   return modelMapper.map(customer, CustomerOrderedCapitalDto.class);
                })
                .collect(Collectors.toList());
        return customerOrderedCapitalDtos;
    }
}
