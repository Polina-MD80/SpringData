package entity;

import javax.persistence.*;

@Entity
@Table(name = "medications")
public class Medicament extends BaseEntity {
    private Patient patient;
    private String name;

    public Medicament() {
    }

    @ManyToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
