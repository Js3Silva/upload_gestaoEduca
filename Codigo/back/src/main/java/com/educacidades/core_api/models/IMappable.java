package com.educacidades.core_api.models;

import com.educacidades.core_api.utils.ObjectMapperConfig;

public interface IMappable<T> {
    
    default T toEntity(Class<T> entityClass) {
        return ObjectMapperConfig.OBJECT_MAPPER.convertValue(this, entityClass);
    }
}
