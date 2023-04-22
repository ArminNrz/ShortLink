package com.neshan.shortLink.dto.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortLinkCreateResponseDTO {
    private String shortLinkAddress;
}
