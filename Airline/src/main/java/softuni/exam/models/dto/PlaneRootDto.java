package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneRootDto {
    @XmlElement(name = "plane")
    private List<PlaneDto> planes;

    public PlaneRootDto() {
    }

    public List<PlaneDto> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlaneDto> planes) {
        this.planes = planes;
    }
}
