package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class CarSeedDto {

    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Integer kilometers;
    @Expose
    private LocalDate registeredOn;

    public CarSeedDto() {
    }

    @Size(min = 2, max = 19)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Size(min = 2, max = 19)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @PositiveOrZero
    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarSeedDto)) return false;
        CarSeedDto that = (CarSeedDto) o;
        return Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects.equals(kilometers, that.kilometers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, kilometers);
    }
}
