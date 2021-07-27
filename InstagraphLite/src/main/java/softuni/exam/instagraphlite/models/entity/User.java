package softuni.exam.instagraphlite.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    //•	id – integer number, primary identification field.
    //•	username – a char sequence. Cannot be null.
    // The username is unique. Must be between 2 and 18 (both numbers are INCLUSIVE)
    //•	password – a char sequence. Cannot be null. Must be at least 4 characters long, inclusive.
    //•	profilePicture – a Picture. Cannot be null.
    private String username;
    private String password;
    private Picture profilePicture;
    private Set<Post> posts;

    public User() {
    }

    @Column(nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("""
                User: %s
                Post count: %d
                """, this.getUsername(), this.getPosts().size()));
        this.getPosts().forEach(post -> sb.append(post.toString()));

        return sb.toString().trim();
    }
}
