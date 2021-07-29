package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "passengers")
public class Passenger extends BaseEntity {

    //•	firstName – a char sequence with minimum length 2.
    //•	lastName – a char sequence with minimum length 2.
    //•	age –  a number (must be positive).
    //•	phoneNumber – a char sequence – phone number.
    //•	email – an email – (must contains ‘@’ and ‘.’ – dot). The email of a person is unique.
    //o	Note: Passenger has a relation with Town

    private String firstname;
    private String lastname;
    private Integer age;
    private String phoneNumber;
    private String email;
    private Town town;
    private Set<Ticket> ticketSet;

    public Passenger() {
    }

    @Column(name = "first_name")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "last_name")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @OneToMany(mappedBy = "passenger", fetch = FetchType.EAGER)
    public Set<Ticket> getTicketSet() {
        return ticketSet;
    }

    public void setTicketSet(Set<Ticket> ticketSet) {
        this.ticketSet = ticketSet;
    }

    @Override
    public String toString() {
        return String.format("\"Passenger %s  %s\n" +
                "\tEmail - %s\n" +
                "\tPhone - %s\n" +
                "\tNumber of tickets - %d\n", getFirstname(),
                getLastname(),getEmail(),getPhoneNumber(),getTicketSet().size());
    }
}
