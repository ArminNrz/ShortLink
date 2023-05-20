package com.neshan.shortLink.service.entity;

import com.neshan.shortLink.entity.AddressEntity;
import com.neshan.shortLink.entity.CustomerEntity;
import com.neshan.shortLink.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public String create(String realAddress, CustomerEntity customerEntity) {

        Optional<AddressEntity> addressOptional = repository.findByRealAddressAndCustomerId(realAddress, customerEntity.getId());
        if (addressOptional.isPresent()) {
            log.warn("Real address: {}, is present and short address is: {}", realAddress, addressOptional.get().getShortAddress());
            this.usedAddressProcess(addressOptional.get());
            return addressOptional.get().getShortAddress();
        }

        String shortAddress = UUID.randomUUID().toString();
        AddressEntity address = new AddressEntity();
        address.setRealAddress(realAddress);
        address.setShortAddress(shortAddress);
        address.setCustomer(customerEntity);
        repository.save(address);
        return shortAddress;
    }

    public AddressEntity fetchByShortAddress(String shortAddress, String customerPhoneNumber, boolean isStatic) {
        AddressEntity foundEntity = repository.findByShortAddressAndCustomerPhoneNumber(shortAddress, customerPhoneNumber)
                .orElseThrow(() -> Problem.valueOf(Status.BAD_REQUEST, "No such link exist!"));
        log.info("Found with short address: {}, phoneNumber: {}, address: {}", shortAddress, customerPhoneNumber, foundEntity);
        if (!isStatic)
            this.usedAddressProcess(foundEntity);
        return foundEntity;
    }

    private void usedAddressProcess(AddressEntity address) {
        address.setUsedCount(address.getUsedCount() + 1);
        address.setLastUsed(ZonedDateTime.now());
        repository.save(address);
        log.info("Update address last used to: {}, used count to: {}", address.getLastUsed(), address.getUsedCount());
    }

    public void remove(String shortAddress, String phoneNumber) {
        repository.deleteByShortAddressAndCustomerPhoneNumber(shortAddress, phoneNumber);
        log.info("Remove address record with short address: {}, customer phone number: {}", shortAddress, phoneNumber);
    }

    public Slice<AddressEntity> getSlice(Pageable pageable) {
        log.debug("Try to fetch address entities with page: {}", pageable);
        return repository.findAll(pageable);
    }

    public void remove(AddressEntity entity) {
        repository.delete(entity);
        log.info("Remove address entity: {}", entity);
    }
}
