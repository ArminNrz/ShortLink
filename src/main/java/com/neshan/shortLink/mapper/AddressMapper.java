package com.neshan.shortLink.mapper;

import com.neshan.shortLink.dto.address.AddressStaticDTO;
import com.neshan.shortLink.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressStaticDTO toStaticDTO(AddressEntity entity);
}
