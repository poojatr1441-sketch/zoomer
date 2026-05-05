package com.pooja.zoomer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pooja.zoomer.entity.Addon;

@Repository
public interface AddonRepository extends JpaRepository<Addon, Long> {
}