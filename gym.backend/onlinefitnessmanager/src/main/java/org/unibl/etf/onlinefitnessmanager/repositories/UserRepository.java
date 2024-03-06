package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
