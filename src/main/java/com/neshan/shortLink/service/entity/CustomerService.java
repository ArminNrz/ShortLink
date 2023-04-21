package com.neshan.shortLink.service.entity;

import com.neshan.shortLink.dto.CreateCustomerDTO;
import com.neshan.shortLink.entity.CustomerEntity;
import com.neshan.shortLink.entity.enumaration.Role;
import com.neshan.shortLink.mapper.CustomerMapper;
import com.neshan.shortLink.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        CustomerEntity foundEntity = this.getByPhoneNumber(phoneNumber);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
        return new User(foundEntity.getPhoneNumber(), foundEntity.getPassword(), authorities);
    }

    @Transactional
    public void create(CreateCustomerDTO createCustomerDTO) {
        CustomerEntity entity = mapper.toEntity(createCustomerDTO);
        entity.setPassword(new BCryptPasswordEncoder().encode(createCustomerDTO.getPassword()));

        try {
            entity = repository.save(entity);
        } catch (DataIntegrityViolationException exception) {
            log.error("This phone number is iterable, phone number: {}", createCustomerDTO.getPhoneNumber());
            throw Problem.valueOf(Status.BAD_REQUEST, "phone number is iterable!");
        }
        log.info("Saved customer: {}", entity);
    }

    public CustomerEntity getByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND, "customer not found!"));
    }
}
