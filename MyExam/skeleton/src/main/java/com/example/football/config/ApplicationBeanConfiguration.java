package com.example.football.config;

import com.example.football.util.ValidationUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Converter<String, LocalDate> localDateConverter = new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
                return mappingContext.getSource() == null ? LocalDate.now() :
                        LocalDate.parse(mappingContext.getSource(),
                                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        };
        modelMapper.addConverter(localDateConverter);
        return modelMapper;
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtil() {
            @Override
            public <E> boolean isValid(E entity) {
                Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
                return validator.validate(entity).isEmpty();
            }
        };
    }

}
