package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PostSeedDto;
import softuni.exam.instagraphlite.models.dto.PostSeedRootDto;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidatorUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PictureService pictureService;

    public PostServiceImpl(PostRepository postRepository,
                           XmlParser xmlParser, ValidatorUtil validatorUtil, ModelMapper modelMapper,
                           UserService userService, PictureService pictureService) {
        this.postRepository = postRepository;
        this.xmlParser = xmlParser;

        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.pictureService = pictureService;
    }

    @Override
    public Boolean аreImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/posts.xml"));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        PostSeedRootDto postSeedRootDto = xmlParser.fromFile("src/main/resources/files/posts.xml", PostSeedRootDto.class);
        List<PostSeedDto> postSeedDtos = postSeedRootDto.getPosts();
        postSeedDtos.stream()
                .filter(postSeedDto -> {
                    boolean isValid = validatorUtil.isValid(postSeedDto)
                            && pictureService.getPictureByPath(postSeedDto.getPicture().getPath())!=null
                            && userService.getUserByUsername(postSeedDto.getUser().getUsername()) != null;
                    sb.append(isValid ?
                            String.format("Successfully imported Post, made by %s",
                                    postSeedDto.getUser().getUsername())
                            : "Invalid post"
                    ).append(System.lineSeparator());

                    return isValid;
                })
                .map(postSeedDto -> {
                    Post post = modelMapper.map(postSeedDto, Post.class);
                    setUserAndPicture(post, postSeedDto);

                    return post;
                })
                .forEach(post -> {
                    try {
                        postRepository.save(post);
                    }catch (Throwable e){
                        sb.append("Invalid post!!!")
                                .append(System.lineSeparator());
                    }
                });
        return sb.toString();
    }

    private void setUserAndPicture(Post post, PostSeedDto postSeedDto) {
        post.setUser(userService.getUserByUsername(postSeedDto.getUser().getUsername()));
        post.setPicture(pictureService.getPictureByPath(postSeedDto.getPicture().getPath()));
    }
}
