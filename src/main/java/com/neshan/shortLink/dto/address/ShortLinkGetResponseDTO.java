package com.neshan.shortLink.dto.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortLinkGetResponseDTO {
    private String realAddress;
}
