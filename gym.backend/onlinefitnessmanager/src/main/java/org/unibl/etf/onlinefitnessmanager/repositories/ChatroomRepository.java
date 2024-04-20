package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.unibl.etf.onlinefitnessmanager.model.entities.ChatroomEntity;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<ChatroomEntity, Integer>, JpaSpecificationExecutor<ChatroomEntity> {

    @Query("SELECT c FROM ChatroomEntity c WHERE c.user_sender.id = :senderId")
    List<ChatroomEntity> findAllBySenderId(Integer senderId);

    @Query("SELECT c FROM ChatroomEntity c WHERE c.user_receiver.id = :receiverId")
    List<ChatroomEntity> findAllByReceiverId(Integer receiverId);
}