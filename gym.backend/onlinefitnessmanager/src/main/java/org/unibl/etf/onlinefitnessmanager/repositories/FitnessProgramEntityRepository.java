package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramEntity;

public interface FitnessProgramEntityRepository extends JpaRepository<FitnessProgramEntity, Long> {
}
