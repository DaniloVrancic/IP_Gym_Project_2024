package org.unibl.etf.onlinefitnessmanager.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "purchase", schema = "db_online_fitness", catalog = "")
public class PurchaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "fitness_program_id", nullable = false)
    private Integer fitnessProgramId;
    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Basic
    @Column(name = "time_of_purchase")
    private LocalDateTime timeOfPurchase;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFitnessProgramId() {
        return fitnessProgramId;
    }

    public void setFitnessProgramId(Integer fitnessProgramId) {
        this.fitnessProgramId = fitnessProgramId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getTimeOfPurchase() {
        return this.timeOfPurchase;
    }

    public void setTimeOfPurchase(LocalDateTime timeOfPurchase) {
        this.timeOfPurchase = timeOfPurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(fitnessProgramId, that.fitnessProgramId) && Objects.equals(userId, that.userId) && Objects.equals(timeOfPurchase, that.timeOfPurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fitnessProgramId, userId, timeOfPurchase);
    }
}
