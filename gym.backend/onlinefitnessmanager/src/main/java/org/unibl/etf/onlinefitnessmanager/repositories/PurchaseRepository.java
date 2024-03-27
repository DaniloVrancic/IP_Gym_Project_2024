package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.unibl.etf.onlinefitnessmanager.model.entities.PurchaseEntity;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer>, JpaSpecificationExecutor<PurchaseEntity> {

    public List<PurchaseEntity> findByUserId(Integer id);

    public List<PurchaseEntity> findByFitnessProgramId(Integer program_id);
}