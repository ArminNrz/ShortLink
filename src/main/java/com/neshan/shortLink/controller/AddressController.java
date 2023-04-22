package com.neshan.shortLink.controller;

import com.neshan.shortLink.constant.Constant;
import com.neshan.shortLink.dto.address.AddressRegisterDTO;
import com.neshan.shortLink.dto.address.ShortLinkCreateResponseDTO;
import com.neshan.shortLink.dto.address.ShortLinkGetResponseDTO;
import com.neshan.shortLink.service.higLevel.ShortLinkManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(Constant.BASE_URL + Constant.VERSION + "/address")
public class AddressController {

    private final ShortLinkManagerService shortLinkManagerService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ShortLinkCreateResponseDTO> registerShortLink(
            @Valid @RequestBody AddressRegisterDTO registerDTO,
            @RequestHeader("Authorization") String token
    ) {
        log.info("REST request to register address: {}", registerDTO.getRealAddress());
        return ResponseEntity.ok().body(shortLinkManagerService.register(registerDTO.getRealAddress(), token));
    }

    @GetMapping("/{shortAddress}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ShortLinkGetResponseDTO> getRealAddress(
            @RequestHeader("Authorization") String token,
            @PathVariable("shortAddress") String shortAddress
    ) {
        log.info("REST request to get address with short Address: {}", shortAddress);
        return ResponseEntity.ok().body(shortLinkManagerService.fetch(shortAddress, token));
    }

    @DeleteMapping("/{shortAddress}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Void> removeShortAddress(
            @RequestHeader("Authorization") String token,
            @PathVariable("shortAddress") String shortAddress
    ) {
        log.info("REST request to remove short address: {}", shortAddress);
        shortLinkManagerService.remove(shortAddress, token);
        return ResponseEntity.noContent().build();
    }
}
