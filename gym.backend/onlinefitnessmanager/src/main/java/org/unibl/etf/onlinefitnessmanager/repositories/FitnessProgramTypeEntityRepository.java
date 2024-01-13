package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramTypeEntity;

public interface FitnessProgramTypeEntityRepository extends JpaRepository<FitnessProgramTypeEntity,Long> {
}
