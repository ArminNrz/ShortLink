package com.neshan.shortLink.service.entity;

import com.neshan.shortLink.entity.AddressEntity;
import com.neshan.shortLink.entity.CustomerEntity;
import com.neshan.shortLink.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public String create(String realAddress, CustomerEntity customerEntity) {
        String shortAddress = UUID.randomUUID().toString();
        AddressEntity address = new AddressEntity();
        address.setRealAddress(realAddress);
        address.setShortAddress(shortAddress);
        address.setCustomer(customerEntity);
        repository.save(address);
        return shortAddress;
    }

    public AddressEntity fetchByShortAddress(String shortAddress, String customerPhoneNumber) {
        AddressEntity foundEntity = repository.getAddressEntitiesByShortAddressAndCustomerPhoneNumber(shortAddress, customerPhoneNumber)
                .orElseThrow(() -> Problem.valueOf(Status.BAD_REQUEST, "No such link exist!"));
        log.info("Found with short address: {}, phoneNumber: {}, address: {}", shortAddress, customerPhoneNumber, foundEntity);
        this.usedAddressProcess(foundEntity);
        return foundEntity;
    }

    private void usedAddressProcess(AddressEntity address) {
        address.setUsedCount(address.getUsedCount() + 1);
        address.setLastUsed(ZonedDateTime.now());
        repository.save(address);
        log.info("Update address last used to: {}, used count to: {}", address.getLastUsed(), address.getUsedCount());
    }
}
