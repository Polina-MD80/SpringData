package hiberspring.service.impl;

import hiberspring.common.GlobalConstants;
import hiberspring.domain.dto.ProductRootDto;
import hiberspring.domain.entity.Product;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.ProductRepository;
import hiberspring.service.ProductService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static hiberspring.common.GlobalConstants.*;

@Service
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;
    private final BranchRepository branchRepository;

    public ProductServiceImpl(ModelMapper modelMapper,
                              XmlParser xmlParser, ProductRepository productRepository,
                              ValidationUtil validationUtil, BranchRepository branchRepository) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.productRepository = productRepository;
        this.validationUtil = validationUtil;
        this.branchRepository = branchRepository;
    }

    @Override
    public Boolean productsAreImported() {
        return productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return Files.readString(Path.of(PRODUCTS_FILE_PATH));
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        ProductRootDto productRootDto = xmlParser.parseXml(ProductRootDto.class, PRODUCTS_FILE_PATH);
        productRootDto.getProducts().stream()
                .filter(productDto -> {
                    boolean isValid = validationUtil.isValid(productDto)
                            && !productRepository.existsByName(productDto.getName())
                            && branchRepository.existsByName(productDto.getBranch());
                    sb.append(isValid ? String.format(SUCCESSFUL_IMPORT_MESSAGE, "Product", productDto.getName())
                            : INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    return isValid;
                })
                .map(productDto -> {
                    Product product = modelMapper.map(productDto, Product.class);
                    product.setBranch(branchRepository.findByName(productDto.getBranch()));
                    return product;
                }).forEach(productRepository::save);
        return sb.toString();
    }
}
