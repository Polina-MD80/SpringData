package com.example.cardealer.repository;

import com.example.cardealer.model.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
}
