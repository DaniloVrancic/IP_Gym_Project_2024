package org.unibl.etf.onlinefitnessmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.onlinefitnessmanager.model.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}