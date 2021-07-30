package softuni.exam.domain.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.domain.entities.Position;

public class PlayerOutDto {
    private String firstName;
    private String lastName;
    private Position position;
    private Integer number;

    public PlayerOutDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("\tPlayer name: %s %s - %s%n" +
                "\tNumber: %s", getFirstName(), getLastName(),
                getPosition(), getNumber());
    }
}
