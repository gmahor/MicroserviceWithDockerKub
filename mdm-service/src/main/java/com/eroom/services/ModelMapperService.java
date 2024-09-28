package com.eroom.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ModelMapperService {

    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public <T> T map(Object source, Class<T> className) {
        T t = null;
        try {
            t = modelMapper.map(source, className);
        } catch (Exception e) {
            log.error("Error in model mapper", e);
        }
        return t;
    }
}
