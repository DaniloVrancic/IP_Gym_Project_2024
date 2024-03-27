package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.unibl.etf.onlinefitnessmanager.model.entities.RecommendedExcerciseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecommendedExcerciseRepository extends JpaRepository<RecommendedExcerciseEntity, Integer>, JpaSpecificationExecutor<RecommendedExcerciseEntity> {

    List<RecommendedExcerciseEntity> findByDateUpdatedBetween(LocalDateTime begin, LocalDateTime end);
    Optional<RecommendedExcerciseEntity> findTopByOrderByDateUpdatedDesc();
}