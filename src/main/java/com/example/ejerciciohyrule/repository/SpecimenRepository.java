package com.example.ejerciciohyrule.repository;

import com.example.ejerciciohyrule.entity.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecimenRepository extends JpaRepository<Specimen, UUID> {
}
