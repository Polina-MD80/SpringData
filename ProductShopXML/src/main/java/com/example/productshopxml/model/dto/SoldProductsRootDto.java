package com.example.productshopxml.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsRootDto {
    @XmlElement(name = "product")
    private List<ProductInfoAndBuyerDto> products;

    public SoldProductsRootDto() {
    }

    public List<ProductInfoAndBuyerDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfoAndBuyerDto> products) {
        this.products = products;
    }
}
