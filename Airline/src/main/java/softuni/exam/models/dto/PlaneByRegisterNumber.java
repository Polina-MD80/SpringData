package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.lang.reflect.Field;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneByRegisterNumber {
    @XmlElement(name = "register-number")
    private String registerNumber;

    public PlaneByRegisterNumber() {
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }
}
