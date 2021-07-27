package softuni.exam.instagraphlite.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface PostService {
    Boolean аreImported();
    String readFromFileContent() throws IOException, JAXBException;
    String importPosts() throws IOException, JAXBException;

}
