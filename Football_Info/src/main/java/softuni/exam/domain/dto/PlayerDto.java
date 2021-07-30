package softuni.exam.domain.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PlayerDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer number;
    @Expose
    private BigDecimal salary;
    @Expose
    private Position position;
    @Expose
    private PictureDto picture;
    @Expose
    private TeamDto team;

    public PlayerDto() {
    }

    @NotBlank
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Size(min = 3, max = 15)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Min(1)
    @Max(99)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @PositiveOrZero
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @NotNull
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @NotNull
    public PictureDto getPicture() {
        return picture;
    }

    public void setPicture(PictureDto picture) {
        this.picture = picture;
    }

    @NotNull
    public TeamDto getTeam() {
        return team;
    }

    public void setTeam(TeamDto team) {
        this.team = team;
    }
}
