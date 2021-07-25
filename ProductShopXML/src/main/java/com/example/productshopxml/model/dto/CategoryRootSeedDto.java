package com.example.productshopxml.model.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryRootSeedDto {
    @XmlElement(name = "category")
    private List<CategorySeedDto> categorySeedDtos;

    public CategoryRootSeedDto() {
    }

    public List<CategorySeedDto> getCategorySeedDtos() {
        return categorySeedDtos;
    }

    public void setCategorySeedDtos(List<CategorySeedDto> categorySeedDtos) {
        this.categorySeedDtos = categorySeedDtos;
    }
}
