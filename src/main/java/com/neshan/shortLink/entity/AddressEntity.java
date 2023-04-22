package com.neshan.shortLink.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "address", indexes = {
        @Index(name = "address_short_customer_id_idx", columnList = "short_address, customer_id")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "real_address", nullable = false)
    private String realAddress;

    @Column(name = "short_address", nullable = false, unique = true)
    private String shortAddress;

    @Column(name = "last_used")
    private ZonedDateTime lastUsed;

    @Column(name = "used_count")
    private Integer usedCount = 0;

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    private ZonedDateTime updated;

    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AddressEntity that = (AddressEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
