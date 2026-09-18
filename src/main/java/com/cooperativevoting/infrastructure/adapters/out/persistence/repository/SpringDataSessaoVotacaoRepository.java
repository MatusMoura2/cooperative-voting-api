package com.cooperativevoting.infrastructure.adapters.out.persistence.repository;

import com.cooperativevoting.infrastructure.adapters.out.persistence.entity.SessaoVotacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSessaoVotacaoRepository extends JpaRepository<SessaoVotacaoEntity, String> {
}
