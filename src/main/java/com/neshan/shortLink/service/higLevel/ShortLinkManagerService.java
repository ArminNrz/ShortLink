package com.neshan.shortLink.service.higLevel;

import com.neshan.shortLink.dto.address.ShortLinkCreateResponseDTO;
import com.neshan.shortLink.dto.address.ShortLinkGetResponseDTO;
import com.neshan.shortLink.entity.AddressEntity;
import com.neshan.shortLink.entity.CustomerEntity;
import com.neshan.shortLink.service.entity.AddressService;
import com.neshan.shortLink.service.entity.CustomerService;
import com.neshan.shortLink.service.lowLevel.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortLinkManagerService {

    private final SecurityService securityService;
    private final AddressService addressService;
    private final CustomerService customerService;

    @Transactional
    public ShortLinkCreateResponseDTO register(String address, String token) {
        String phoneNumber = securityService.getCustomerPhoneNumberByToken(token);
        CustomerEntity customer = customerService.getByPhoneNumber(phoneNumber);
        int registeredAddressCount = customer.getAddressEntities().size();
        if (registeredAddressCount >= 10) {
            log.error("Customer with phoneNumber: {}, has more than 10 registered address", customer.getPhoneNumber());
            throw Problem.valueOf(Status.BAD_REQUEST, "customer has more than 10 registered address!");
        }
        String shortLink = addressService.create(address, customer);
        return ShortLinkCreateResponseDTO.builder().shortLinkAddress(shortLink).build();
    }

    @Transactional
    public ShortLinkGetResponseDTO fetch(String shortAddress, String token) {
        String phoneNumber = securityService.getCustomerPhoneNumberByToken(token);
        AddressEntity addressEntity = addressService.fetchByShortAddress(shortAddress, phoneNumber);
        return ShortLinkGetResponseDTO.builder()
                .realAddress(addressEntity.getRealAddress())
                .build();
    }

    @Transactional
    public void remove(String shortAddress, String token) {
        String phoneNumber = securityService.getCustomerPhoneNumberByToken(token);
        addressService.remove(shortAddress, phoneNumber);
    }
}
