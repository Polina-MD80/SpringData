package com.example.softunigamestore.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class GameAddDto {
    private String title;
    private BigDecimal price;
    private Double size;
    private String trailer;
    private String thumbnailURL;
    private String description;
    private String releaseDate;

    public GameAddDto() {
    }

    public GameAddDto(String title, BigDecimal price, Double size, String trailer, String thumbnailURL, String description, String releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.thumbnailURL = thumbnailURL;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Pattern(regexp = "[A-Z][\\s,a-z]{2,99}", message = "Invalid game title.")
    public String getTitle() {
        return title;
    }

    @Min(value = 0, message = "Enter valid price.")
    public BigDecimal getPrice() {
        return price;
    }

    @Min(value = 0, message = "Enter valid size.")
    public Double getSize() {
        return size;
    }

    @Size(min = 11, max = 11, message = "Enter valid trailer.")
    public String getTrailer() {
        return trailer;
    }

    @Pattern(regexp = "(https?).+", message = "Enter valid Url")
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Size(min = 20, message = "Enter valid description.")
    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
