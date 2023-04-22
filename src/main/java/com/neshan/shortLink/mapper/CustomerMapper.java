package com.neshan.shortLink.mapper;

import com.neshan.shortLink.dto.customer.CustomerCreateDTO;
import com.neshan.shortLink.dto.customer.CustomerDTO;
import com.neshan.shortLink.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "password", ignore = true)
    CustomerEntity toEntity(CustomerCreateDTO customerCreateDTO);

    CustomerDTO toDTO(CustomerEntity entity);
}
