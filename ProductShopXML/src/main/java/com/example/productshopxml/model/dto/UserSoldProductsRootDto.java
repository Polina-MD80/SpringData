package com.example.productshopxml.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "users")
public class UserSoldProductsRootDto {
    @XmlElement(name = "user")
    private List<UserNamesSoldProductsDto> users;

    public UserSoldProductsRootDto() {
    }

    public List<UserNamesSoldProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserNamesSoldProductsDto> users) {
        this.users = users;
    }
}
