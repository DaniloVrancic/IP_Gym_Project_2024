package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.onlinefitnessmanager.model.entities.RecommendedExcerciseEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsernameIs(String username);
}
