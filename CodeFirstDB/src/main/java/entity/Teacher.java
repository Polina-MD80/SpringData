package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends User {
    private String email;
    private Float salaryPerHour;
    private Set<Course> courses;


    public Teacher() {
        this.courses = new HashSet<>();
    }

    @OneToMany(mappedBy = "teacher")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }


    @Column(name = "email", unique = true, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salary_per_hour", precision = 10, scale = 3)
    public Float getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(Float salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }
}
