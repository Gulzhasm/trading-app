package com.momentumtrading.signals.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;


@UtilityClass
public class Utils {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new SimpleModule());

    private static final Configuration configuration = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .build();

    @SneakyThrows
    public static String readResource(String path){
        return IOUtils.resourceToString(path, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public static <T> String serialize(T object){
        return Utils.OBJECT_MAPPER.writeValueAsString(object);
    }
}
