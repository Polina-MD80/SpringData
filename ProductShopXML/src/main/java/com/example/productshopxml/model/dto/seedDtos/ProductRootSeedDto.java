package com.example.productshopxml.model.dto.seedDtos;

import com.example.productshopxml.model.dto.seedDtos.ProductSeedDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductRootSeedDto {
    @XmlElement(name = "product")
    private List<ProductSeedDto> productSeedDtos;

    public ProductRootSeedDto() {
    }

    public List<ProductSeedDto> getProductSeedDtos() {
        return productSeedDtos;
    }

    public void setProductSeedDtos(List<ProductSeedDto> productSeedDtos) {
        this.productSeedDtos = productSeedDtos;
    }
}
