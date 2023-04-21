package com.neshan.shortLink.mapper;

import com.neshan.shortLink.dto.CreateCustomerDTO;
import com.neshan.shortLink.entity.CustomerEntity;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-21T20:08:16+0330",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Private Build)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerEntity toEntity(CreateCustomerDTO createCustomerDTO) {
        if ( createCustomerDTO == null ) {
            return null;
        }

        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setPhoneNumber( createCustomerDTO.getPhoneNumber() );
        customerEntity.setName( createCustomerDTO.getName() );

        return customerEntity;
    }
}
