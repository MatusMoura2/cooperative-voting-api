package com.cooperativevoting.infrastructure.adapters.out.persistence.repository;

import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPautaRepository extends JpaRepository<PautaEntity, String> {
}
