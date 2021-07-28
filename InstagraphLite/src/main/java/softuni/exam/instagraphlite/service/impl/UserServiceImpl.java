package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserSeedDto;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final PictureService pictureService;

    public UserServiceImpl(UserRepository userRepository, Gson gson,
                           ModelMapper modelMapper, ValidatorUtil validatorUtil, PictureService pictureService) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.pictureService = pictureService;
    }

    @Override
    public Boolean аreImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/users.json"));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder sb = new StringBuilder();
        UserSeedDto[] userSeedDtos = gson.fromJson(readFromFileContent(), UserSeedDto[].class);
        Arrays.stream(userSeedDtos)
                .filter(userSeedDto -> {
                    boolean isValid = validatorUtil.isValid(userSeedDto)
                            &&pictureService.getPictureByPath(userSeedDto.getProfilePicture())!=null
                            &&!userIsInTheBase(userSeedDto.getUsername());

                    sb.append(isValid ?
                            String.format("Successfully imported User: %s", userSeedDto.getUsername())
                            : "Invalid user")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(userSeedDto -> {
                    User user = modelMapper.map(userSeedDto, User.class);
                    user.setProfilePicture(pictureService.getPictureByPath(userSeedDto.getProfilePicture()));
                    return user;
                })
                .forEach(userRepository::save);

        return sb.toString();
    }

    private boolean userIsInTheBase(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public String exportUsersWithTheirPosts() {

        StringBuilder sbb = new StringBuilder();

        List<User> users = this.userRepository.findAllUsersOrderedByPostsCountDecsThenById();
        users
                .forEach(user -> {
                    Set<Post> posts = user.getPosts().stream().sorted(Comparator.comparing(f -> f.getPicture().getSize())).collect(Collectors.toCollection(LinkedHashSet::new));
                    user.setPosts(posts);
                    sbb.append(users).append(System.lineSeparator());
                });
        return sbb.toString().trim();
    }

    @Override
    public User getUserByUsername(String username) {

      return  userRepository.findByUsername(username);
    }
}
