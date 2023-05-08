package com.neshan.shortLink.dto.address;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AddressStaticDTO {
    private String shortAddress;
    private String realAddress;
    private ZonedDateTime lastUsed;
    private int usedCount;
}
