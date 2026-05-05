package com.pooja.zoomer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pooja.zoomer.entity.Address;
import com.pooja.zoomer.entity.User;
import com.pooja.zoomer.repository.AddressRepository;
import com.pooja.zoomer.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;
    
    //add address
    public Address addAddress(Long userId, Address address) {

    	User user = userRepository.findById(userId)
    	        .orElseThrow(() -> new RuntimeException("User not found"));

        address.setUser(user);

        return addressRepository.save(address);
    }

    public List<Address> getUserAddresses(Long userId) {
        return addressRepository.findByUser_UserId(userId);
    }
}