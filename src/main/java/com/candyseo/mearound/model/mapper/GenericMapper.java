package com.candyseo.mearound.model.mapper;

import org.springframework.core.convert.converter.Converter;

public interface GenericMapper<D, E> extends Converter<D, E> {
    
    D toDto(E entity);
    E toEntity(D dto);

}
