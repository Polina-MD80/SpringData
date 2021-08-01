package com.example.football.repository;


import com.example.football.models.entity.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<Stat, Long> {

    boolean existsByPassingAndShootingAndEndurance(Float passing, Float shooting, Float endurance);

    Stat findStatById(Long id);
}
