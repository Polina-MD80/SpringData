package com.example.football.models.dto.playerinfo;

import com.example.football.models.entity.Position;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerDto {
    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "birth-date")
    private String birthDate;
    @XmlElement(name = "position")
    private Position position;
    @XmlElement(name = "town")
    private TownByName town;
    @XmlElement(name = "team")
    private TeamByName team;
    @XmlElement(name = "stat")
    private StatById stat;

    public PlayerDto() {
    }

    @NotBlank
    @Size(min = 2)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank
    @Size(min = 2)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public TownByName getTown() {
        return town;
    }

    public void setTown(TownByName town) {
        this.town = town;
    }


    public TeamByName getTeam() {
        return team;
    }

    public void setTeam(TeamByName team) {
        this.team = team;
    }


    public StatById getStat() {
        return stat;
    }

    public void setStat(StatById stat) {
        this.stat = stat;
    }
}
