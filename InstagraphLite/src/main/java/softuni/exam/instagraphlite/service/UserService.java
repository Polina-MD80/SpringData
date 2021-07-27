package softuni.exam.instagraphlite.service;

import softuni.exam.instagraphlite.models.entity.User;

import java.io.IOException;

public interface UserService {
    Boolean аreImported();
    String readFromFileContent() throws IOException;
    String importUsers() throws IOException;
    String exportUsersWithTheirPosts();

    User getUserByUsername(String username);
}
