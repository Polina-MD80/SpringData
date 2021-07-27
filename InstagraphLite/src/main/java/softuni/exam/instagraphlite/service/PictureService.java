package softuni.exam.instagraphlite.service;

import softuni.exam.instagraphlite.models.entity.Picture;

import java.io.IOException;
import java.util.Optional;

public interface PictureService {
    Boolean areImported();
    String readFromFileContent() throws IOException;
    String importPictures() throws IOException;
    String exportPictures();
    Picture getPictureByPath(String path);

}
