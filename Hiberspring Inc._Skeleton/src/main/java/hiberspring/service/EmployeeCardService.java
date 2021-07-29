package hiberspring.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface EmployeeCardService {

    Boolean employeeCardsAreImported();

    String readEmployeeCardsJsonFile() throws IOException;

    String importEmployeeCards(String employeeCardsFileContent) throws IOException;

}
