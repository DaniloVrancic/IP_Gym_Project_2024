package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.unibl.etf.onlinefitnessmanager.model.entities.ChatroomEntity;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<ChatroomEntity, Integer>, JpaSpecificationExecutor<ChatroomEntity> {

    List<ChatroomEntity> findAllBySenderIdOrReceiverId(Integer id);
}