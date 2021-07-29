package hiberspring.domain.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;

public class EmployeeCardDto {
    @Expose
    private String number;

    public EmployeeCardDto() {
    }

    @NotBlank
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
