package com.example.productshopxml.model.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserNamesSoldProductsDto {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "sold-products")
    private SoldProductsRootDto productsRootDto;

    public UserNamesSoldProductsDto() {
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

    public SoldProductsRootDto getProductsRootDto() {
        return productsRootDto;
    }

    public void setProductsRootDto(SoldProductsRootDto productsRootDto) {
        this.productsRootDto = productsRootDto;
    }
}
