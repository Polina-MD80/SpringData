package com.example.productshopxml.model.dto.usersAndProducts;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsPerUserRootDto {
    @XmlAttribute()
    private int count;
    @XmlElement(name = "sold-product")
    private List<ProductNameAndPriceDto> soldProducts;

    public SoldProductsPerUserRootDto() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductNameAndPriceDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductNameAndPriceDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
