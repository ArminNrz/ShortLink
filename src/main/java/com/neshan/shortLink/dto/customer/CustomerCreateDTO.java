package com.neshan.shortLink.dto.customer;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CustomerCreateDTO {
    @NotEmpty(message = "phone number is empty")
    @Size(min = 1, max = 11, message = "phone number has not normal size")
    private String phoneNumber;
    @NotEmpty(message = "password is empty")
    private String password;
    @NotEmpty(message = "name is empty")
    private String name;
}
