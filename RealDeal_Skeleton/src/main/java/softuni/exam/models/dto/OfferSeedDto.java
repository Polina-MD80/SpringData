package softuni.exam.models.dto;


import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDto {

    private String description;
    private BigDecimal price;
    @XmlElement(name = "added-on")
    private String addedOn;
    @XmlElement(name = "has-gold-status")
    private Boolean hasGoldStatus;
    @XmlElement(name = "car")
    private CarId car;
    @XmlElement(name = "seller")
    private SellerId seller;

    public OfferSeedDto() {
    }

    @Size(min = 5)
    public String getDescription() {
        return description;
    }

    @PositiveOrZero
    public BigDecimal getPrice() {
        return price;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public Boolean getHasGoldStatus() {
        return hasGoldStatus;
    }

    public CarId getCar() {
        return car;
    }

    public SellerId getSeller() {
        return seller;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public void setHasGoldStatus(Boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public void setCar(CarId car) {
        this.car = car;
    }

    public void setSeller(SellerId seller) {
        this.seller = seller;
    }


}