package com.neshan.shortLink.controller;

import com.neshan.shortLink.constant.Constant;
import com.neshan.shortLink.dto.address.ShortLinkCreateResponseDTO;
import com.neshan.shortLink.dto.address.ShortLinkGetResponseDTO;
import com.neshan.shortLink.service.higLevel.ShortLinkManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(Constant.BASE_URL + Constant.VERSION + "/address")
public class AddressController {

    private final ShortLinkManagerService shortLinkManagerService;

    @PostMapping("/{address}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ShortLinkCreateResponseDTO> registerShortLink(
            @PathVariable("address") String address,
            @RequestHeader("Authorization") String token
    ) {
        log.info("REST request to register address: {}", address);
        return ResponseEntity.ok().body(shortLinkManagerService.register(address, token));
    }

    @GetMapping("/{shortLink}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ShortLinkGetResponseDTO> getRealAddress(
            @RequestHeader("Authorization") String token,
            @PathVariable("shortLink") String shortLink
    ) {
        log.info("REST request to get address with short link: {}", shortLink);
        return ResponseEntity.ok().body(shortLinkManagerService.fetch(shortLink, token));
    }
}
