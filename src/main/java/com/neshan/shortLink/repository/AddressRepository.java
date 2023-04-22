package com.neshan.shortLink.repository;

import com.neshan.shortLink.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    Optional<AddressEntity> findByRealAddressAndCustomerId(String realAddress, long customerId);

    @Query("select ad from AddressEntity as ad left join CustomerEntity cu on ad.customerId = cu.id where ad.shortAddress =?1 and cu.phoneNumber=?2")
    Optional<AddressEntity> findByShortAddressAndCustomerPhoneNumber(String shortAddress, String customerPhoneNumber);

    @Modifying
    @Query(nativeQuery = true,
            value = "delete from address where address.id = " +
                    "(select * from " +
                        "( " +
                            "select ad.id " +
                            "from address ad left join customer cu on (cu.id=ad.customer_id) " +
                            "where ad.short_address=? and cu.phone_number=? limit 1 " +
                        ") " +
                    " tblTemp)"
    )
    void deleteByShortAddressAndCustomerPhoneNumber(String shortAddress, String customerPhoneNumber);
}
