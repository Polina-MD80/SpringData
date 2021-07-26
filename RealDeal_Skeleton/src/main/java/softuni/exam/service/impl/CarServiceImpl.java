package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/cars.json"));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();
        CarSeedDto[] carSeedDtos = gson.fromJson(readCarsFileContent(), CarSeedDto[].class);
        Arrays.stream(carSeedDtos)
                .filter(carSeedDto -> {
                    boolean isValid = validationUtil.isValid(carSeedDto);
                    sb.append(isValid ? String.format("Successfully imported car - %s - %s",
                            carSeedDto.getMake(), carSeedDto.getModel())
                            : "Invalid car")
                            .append(System.lineSeparator());

                return isValid;
                })
                .map(carSeedDto -> modelMapper.map(carSeedDto, Car.class))
                .forEach(carRepository :: save);
        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();

        List<Car> cars = carRepository.getAllOrderedByPicturesInDescByMake();
        cars
                .forEach(car -> sb.append(car.toString()).append(System.lineSeparator()));

        return sb.toString();
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }
}
