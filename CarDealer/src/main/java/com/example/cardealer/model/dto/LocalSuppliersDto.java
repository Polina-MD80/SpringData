package com.example.cardealer.model.dto;

import com.example.cardealer.model.entity.Part;
import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Set;

public class LocalSuppliersDto {
    @Expose
    private String Id;
    @Expose
    private String Name;
    @Expose
    private Integer partsCount;




    public LocalSuppliersDto() {

    }



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = getPartsCount();
    }

}
