package hiberspring.service.impl;

import hiberspring.common.GlobalConstants;
import hiberspring.domain.dto.EmployeeRootDto;
import hiberspring.domain.entity.Employee;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static hiberspring.common.GlobalConstants.INCORRECT_DATA_MESSAGE;
import static hiberspring.common.GlobalConstants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final EmployeeCardRepository employeeCardRepository;

    public EmployeeServiceImpl(XmlParser xmlParser, ModelMapper modelMapper,
                               ValidationUtil validationUtil, EmployeeRepository employeeRepository,
                               BranchRepository branchRepository, EmployeeCardRepository employeeCardRepository) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.employeeCardRepository = employeeCardRepository;
    }


    @Override
    public Boolean employeesAreImported() {
        return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.EMPLOYEES_FILE_PATH));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        EmployeeRootDto employeeRootDto = xmlParser.parseXml(EmployeeRootDto.class, GlobalConstants.EMPLOYEES_FILE_PATH);
        employeeRootDto.getEmployees().stream()
                .filter(employeeDto -> {
                    boolean isValid = validationUtil.isValid(employeeDto)
                            && branchRepository.existsByName(employeeDto.getBranch())
                            && employeeCardRepository.existsByNumber(employeeDto.getCard());
                    sb.append(isValid ? String.format(SUCCESSFUL_IMPORT_MESSAGE,
                            "Employee", employeeDto.getFirstName() + " " + employeeDto.getLastName())
                            : INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    return isValid;
                })
                .map(employeeDto -> {
                    Employee employee = modelMapper.map(employeeDto, Employee.class);
                    employee.setBranch(branchRepository.findByName(employeeDto.getBranch()));
                    employee.setCard(employeeCardRepository.findByNumber(employeeDto.getCard()));

                    return employee;
                })
                .forEach(employeeRepository::save);
        return sb.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        StringBuilder sb = new StringBuilder();
        List<Employee> employees = employeeRepository.findAllByBranchWithProducts();
        employees.forEach(employee -> sb.append(employee.toString()));
        return sb.toString();
    }
}
