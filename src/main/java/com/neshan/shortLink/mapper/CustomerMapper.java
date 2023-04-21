package com.neshan.shortLink.mapper;

import com.neshan.shortLink.dto.CreateCustomerDTO;
import com.neshan.shortLink.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "password", ignore = true)
    CustomerEntity toEntity(CreateCustomerDTO createCustomerDTO);
}
