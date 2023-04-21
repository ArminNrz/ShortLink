package com.neshan.shortLink.controller;

import com.neshan.shortLink.constant.Constant;
import com.neshan.shortLink.dto.CreateCustomerDTO;
import com.neshan.shortLink.service.entity.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(Constant.BASE_URL + Constant.VERSION + "/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateCustomerDTO createDTO) {
        log.info("REST request to create Customer: {}", createDTO);
        customerService.create(createDTO);
        return ResponseEntity.created(URI.create("/customers")).build();
    }
}
