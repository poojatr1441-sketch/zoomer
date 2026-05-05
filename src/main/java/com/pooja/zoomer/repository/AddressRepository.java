package com.pooja.zoomer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pooja.zoomer.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


	List<Address> findByUser_UserId(Long userId);
}