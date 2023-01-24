package com.bknote71.ajouportal.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;


public class ModelMapperUtils {
    private static final ModelMapper MAPPER = new ModelMapper();

    public static ModelMapper getModelMapper() {
        return MAPPER;
    }
}
