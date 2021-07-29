package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.domain.dto.BranchDto;
import hiberspring.domain.entity.Branch;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.TownRepository;
import hiberspring.service.BranchService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static hiberspring.common.GlobalConstants.*;

@Service
public class BranchServiceImpl implements BranchService {
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final BranchRepository branchRepository;
    private final TownRepository townRepository;

    public BranchServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil,
                             BranchRepository branchRepository, TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.branchRepository = branchRepository;
        this.townRepository = townRepository;
    }

    @Override
    public Boolean branchesAreImported() {
        return branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(BRANCHES_FILE_PATH));
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();
        BranchDto[] branchDtos = gson.fromJson(readBranchesJsonFile(), BranchDto[].class);
        Arrays.stream(branchDtos)
                .filter(branchDto -> {
                    boolean isValid = validationUtil.isValid(branchDto)
                            && !branchRepository.existsByName(branchDto.getName())
                            && townRepository.existsByName(branchDto.getTown());
                    sb.append(isValid ? String.format(SUCCESSFUL_IMPORT_MESSAGE, "Branch", branchDto.getName())
                            : INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                    return isValid;
                })
                .map(branchDto -> {
                    Branch branch = modelMapper.map(branchDto, Branch.class);
                    branch.setTown(townRepository.findByName(branchDto.getTown()));
                    return branch;
                }).forEach(branchRepository::save);
        return sb.toString();
    }
}
