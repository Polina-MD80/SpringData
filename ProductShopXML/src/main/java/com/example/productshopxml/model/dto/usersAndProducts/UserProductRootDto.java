package com.example.productshopxml.model.dto.usersAndProducts;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "users")
public class UserProductRootDto {
    @XmlAttribute
    private Integer count;
    @XmlElement(name = "user")
    private List<UserProductsDto> users;

    public UserProductRootDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserProductsDto> users) {
        this.users = users;
    }
}
