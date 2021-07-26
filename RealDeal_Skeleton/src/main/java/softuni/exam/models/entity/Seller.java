package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private Rating rating;
    private String town;

    public Seller() {
    }

    @Column
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Enumerated
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Column
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
