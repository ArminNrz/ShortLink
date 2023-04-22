package com.neshan.shortLink.repository;

import com.neshan.shortLink.entity.AddressEntity;
import com.neshan.shortLink.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    Optional<AddressEntity> findByRealAddressAndCustomerId(String realAddress, long customerId);

    @Query("select add from AddressEntity as add left join CustomerEntity cu on add.customerId = cu.id where add.shortAddress =?1 and cu.phoneNumber=?2")
    Optional<AddressEntity> getAddressEntitiesByShortAddressAndCustomerPhoneNumber(String shortAddress, String customerPhoneNumber);
}
