package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.unibl.etf.onlinefitnessmanager.model.entities.PurchaseEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.SubscriptionEntity;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer>, JpaSpecificationExecutor<SubscriptionEntity> {


    public List<SubscriptionEntity> findByUserId(Integer id);

    public List<SubscriptionEntity> findByFitnessProgramTypeId(Integer program_id);
}