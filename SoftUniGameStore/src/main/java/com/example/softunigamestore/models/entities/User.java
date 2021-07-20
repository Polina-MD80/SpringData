package com.example.softunigamestore.models.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String email;
    private String password;
    private String fullName;
    private Boolean isAdmin = false;
    private Set<Game> games;

    public User() {
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    @Column
    public String getPassword() {
        return password;
    }

    @Column
    public String getFullName() {
        return fullName;
    }

    @Column(name = "is_admin")
    public Boolean getAdmin() {
        return isAdmin;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Game> getGames() {
        return games;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void purchase(Game game) {
        this.getGames().add(game);
    }
}
