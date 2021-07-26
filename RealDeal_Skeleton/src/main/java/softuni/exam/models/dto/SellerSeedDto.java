package softuni.exam.models.dto;

import softuni.exam.models.entity.Rating;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedDto {
    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "rating")
    private Rating rating;
    @XmlElement(name = "town")
    private String town;

    public SellerSeedDto() {
    }

    @Size(min = 2, max = 19)
    public String getFirstName() {
        return firstName;
    }

    @Size(min = 2, max = 19)
    public String getLastName() {
        return lastName;
    }

    @Email
    public String getEmail() {
        return email;
    }

    @NotNull
    public Rating getRating() {
        return rating;
    }

    @NotBlank
    public String getTown() {
        return town;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
