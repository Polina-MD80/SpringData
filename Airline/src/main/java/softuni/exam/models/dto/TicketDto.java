package softuni.exam.models.dto;

import org.springframework.format.annotation.DateTimeFormat;
import softuni.exam.util.impl.LocalDateTimeAdapter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class TicketDto {

    @XmlElement(name = "serial-number")
    private String serialNumber;
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "take-off")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime takeoff;
    @XmlElement(name = "from-town")
    private TownByName fromTown;
    @XmlElement(name = "to-town")
    private TownByName toTown;
    @XmlElement(name = "passenger")
    private PassengerByEmail passenger;
    @XmlElement(name = "plane")
    private PlaneByRegisterNumber plane;

    public TicketDto() {
    }

    @NotBlank
    @Size(min = 2)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(LocalDateTime takeoff) {
        this.takeoff = takeoff;
    }

    public TownByName getFromTown() {
        return fromTown;
    }

    public void setFromTown(TownByName fromTown) {
        this.fromTown = fromTown;
    }

    public TownByName getToTown() {
        return toTown;
    }

    public void setToTown(TownByName toTown) {
        this.toTown = toTown;
    }

    public PassengerByEmail getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerByEmail passenger) {
        this.passenger = passenger;
    }

    public PlaneByRegisterNumber getPlane() {
        return plane;
    }

    public void setPlane(PlaneByRegisterNumber plane) {
        this.plane = plane;
    }
}
