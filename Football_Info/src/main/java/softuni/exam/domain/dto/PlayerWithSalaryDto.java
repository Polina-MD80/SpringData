package softuni.exam.domain.dto;

import softuni.exam.domain.entities.Team;

import java.math.BigDecimal;

public class PlayerWithSalaryDto {
    private String firstName;
    private String lastName;
    private Integer number;
    private BigDecimal salary;
    private Team team;

    public PlayerWithSalaryDto() {
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return String.format("Player name: %s %s \n" +
                "Number: %d\n" +
                "Salary: %s\n" +
                "Team: %s\n", getFirstName(),getLastName(),getNumber(), getSalary(), getTeam().getName());
    }
}
