package softuni.exam.domain.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "picture")
public class PictureDto {
    @XmlElement(name = "url")
    @Expose
    private String url;

    public PictureDto() {
    }

    @NotBlank
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
