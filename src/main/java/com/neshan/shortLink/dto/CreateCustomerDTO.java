package com.neshan.shortLink.dto;

import lombok.Data;

@Data
public class CreateCustomerDTO {
    private String phoneNumber;
    private String password;
    private String name;
}
