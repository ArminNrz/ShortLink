package com.neshan.shortLink.service.higLevel;

import com.neshan.shortLink.entity.AddressEntity;
import com.neshan.shortLink.service.entity.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RemoveShortLinkManagerService {

    @Value("${neshan.scheduler.remove-link.page-size}")
    private int pageSize;

    private final AddressService addressService;

    public void remove() {
        Slice<AddressEntity> addressSlices = addressService.getSlice(PageRequest.of(0, pageSize));
        List<AddressEntity> entities = addressSlices.getContent();
        this.checkAndRemoves(entities);
        while (addressSlices.hasNext()) {
            addressSlices = addressService.getSlice(addressSlices.nextPageable());
            this.checkAndRemoves(addressSlices.getContent());
        }
    }

    private void checkAndRemoves(List<AddressEntity> entities) {
        entities.forEach(this::checkAndRemove);
    }

    private void checkAndRemove(AddressEntity entity) {
        log.debug("Try to check and remove Address entity: {}", entity);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime lastUsePlusOneYear = entity.getLastUsed().plus(1, ChronoUnit.YEARS);
        if (lastUsePlusOneYear.isBefore(now)) {
            addressService.remove(entity);
            log.warn("Removed address entity id: {}, because don't use more than one year", entity.getId());
        }
    }
}
