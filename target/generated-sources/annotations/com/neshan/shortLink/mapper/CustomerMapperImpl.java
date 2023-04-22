package com.neshan.shortLink.mapper;

import com.neshan.shortLink.dto.customer.CustomerCreateDTO;
import com.neshan.shortLink.dto.customer.CustomerDTO;
import com.neshan.shortLink.entity.CustomerEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-22T13:23:46+0330",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Private Build)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerEntity toEntity(CustomerCreateDTO customerCreateDTO) {
        if ( customerCreateDTO == null ) {
            return null;
        }

        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setPhoneNumber( customerCreateDTO.getPhoneNumber() );
        customerEntity.setName( customerCreateDTO.getName() );

        return customerEntity;
    }

    @Override
    public CustomerDTO toDTO(CustomerEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setPhoneNumber( entity.getPhoneNumber() );
        customerDTO.setName( entity.getName() );

        return customerDTO;
    }
}
