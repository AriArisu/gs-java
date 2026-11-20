package com.example.gsjava.repository;

import com.example.gsjava.model.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicaoRepository extends JpaRepository<Medicao, Long> {
    List<Medicao> findBySensorIdOrderByIdAsc(String sensorId);
}
