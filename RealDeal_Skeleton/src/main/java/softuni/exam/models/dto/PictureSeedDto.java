package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PictureSeedDto {
    @Expose
    private String name;
    @Expose
    private LocalDateTime dateAndTime;
    @Expose
    private Long car;

    public PictureSeedDto() {
    }

    @Size(min = 2, max = 19)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Long getCar() {
        return car;
    }

    public void setCar(Long car) {
        this.car = car;
    }
}
