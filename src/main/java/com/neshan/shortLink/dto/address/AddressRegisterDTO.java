package com.neshan.shortLink.dto.address;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddressRegisterDTO {
    @NotEmpty(message = "real address is empty")
    private String realAddress;
}
