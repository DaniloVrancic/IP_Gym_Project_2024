package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.unibl.etf.onlinefitnessmanager.model.entities.ChatroomEntity;

public interface ChatroomRepository extends JpaRepository<ChatroomEntity, Integer>, JpaSpecificationExecutor<ChatroomEntity> {
}