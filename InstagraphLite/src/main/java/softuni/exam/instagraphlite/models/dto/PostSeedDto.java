package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "post")
public class PostSeedDto {
    @XmlElement
    private String caption;
    @XmlElement(name = "user")
    private UserByUserName user;
    @XmlElement(name = "picture")
    private PictureByPath picture;

    public PostSeedDto() {
    }
  @NotBlank
  @Size(min = 21)
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserByUserName getUser() {
        return user;
    }

    public void setUser(UserByUserName user) {
        this.user = user;
    }

    public PictureByPath getPicture() {
        return picture;
    }

    public void setPicture(PictureByPath picture) {
        this.picture = picture;
    }
}
