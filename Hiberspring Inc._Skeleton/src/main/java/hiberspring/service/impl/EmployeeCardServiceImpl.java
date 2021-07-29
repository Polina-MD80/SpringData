package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.domain.dto.EmployeeCardDto;
import hiberspring.domain.entity.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static hiberspring.common.GlobalConstants.EMPLOYEE_CARDS_FILE_PATH;
import static hiberspring.common.GlobalConstants.INCORRECT_DATA_MESSAGE;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final EmployeeCardRepository employeeCardRepository;

    public EmployeeCardServiceImpl(ModelMapper modelMapper, Gson gson,
                                   ValidationUtil validationUtil,
                                   EmployeeCardRepository employeeCardRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;

        this.employeeCardRepository = employeeCardRepository;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEE_CARDS_FILE_PATH));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();
        EmployeeCardDto[] employeeCardDtos = gson.fromJson(readEmployeeCardsJsonFile(), EmployeeCardDto[].class);
        Arrays.stream(employeeCardDtos)
                .filter(employeeCardDto -> {
                    boolean isValid = validationUtil.isValid(employeeCardDto)
                            && !employeeCardRepository.existsByNumber(employeeCardDto.getNumber());
                    sb.append(isValid ? String.format(GlobalConstants.SUCCESSFUL_IMPORT_MESSAGE, "Employee Card",
                            employeeCardDto.getNumber())
                    : INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    return isValid;
                })
                .map(employeeCardDto -> modelMapper.map(employeeCardDto, EmployeeCard.class))
                .forEach(employeeCardRepository::save);

        return sb.toString();
    }
}
