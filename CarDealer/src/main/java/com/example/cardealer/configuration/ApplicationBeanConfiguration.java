package com.example.cardealer.configuration;

import com.example.cardealer.model.dto.CustomerSeedDto;
import com.example.cardealer.model.entity.Customer;

import com.google.gson.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


//        Converter<String, LocalDateTime> localDateConverter = new Converter<String, LocalDateTime>() {
//            @Override
//            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
//                return mappingContext.getSource() == null ? LocalDateTime.now() :
//                        LocalDateTime.parse(mappingContext.getSource(),
//                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
//            }
//        };
//        modelMapper.addConverter(localDateConverter);
//        modelMapper
//                .typeMap(CustomerSeedDto.class, Customer.class);
        return modelMapper;
    }

    @Bean

    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .setPrettyPrinting()
                .create();
    }
}

@Configuration
class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}

@Configuration
class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME.withLocale(Locale.ENGLISH));
    }
}