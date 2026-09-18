package com.cooperativevoting.infrastructure.adapters.out.persistence.repository;

import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.VotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpringDataVotoRepository extends JpaRepository<VotoEntity, String> {
    boolean existsBySessaoIdAndAssociadoId(String sessaoId, String associadoId);
    List<VotoEntity> findBySessaoId(String sessaoId);
}
