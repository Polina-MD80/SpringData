package com.example.productshopxml.model.dto;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductInfoAndBuyerDto {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement (name = "buyer-first-name")
    private String BuyerFirstName;
    @XmlElement (name = "buyer-last-name")
    private String BuyerLastName;

    public ProductInfoAndBuyerDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBuyerFirstName() {
        return BuyerFirstName;
    }

    public void setBuyerFirstName(String buyerFirstName) {
        BuyerFirstName = buyerFirstName;
    }

    public String getBuyerLastName() {
        return BuyerLastName;
    }

    public void setBuyerLastName(String buyerLastName) {
        BuyerLastName = buyerLastName;
    }
}
