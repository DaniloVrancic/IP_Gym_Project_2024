package org.unibl.etf.onlinefitnessmanager.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "subscription", schema = "db_online_fitness", catalog = "")
public class SubscriptionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "fitness_program_type_id", nullable = false)
    private Integer fitnessProgramTypeId; // Id of referenced program
    
    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFitnessProgramTypeId() {
        return fitnessProgramTypeId;
    }

    public void setFitnessProgramTypeId(Integer fitnessProgramTypeId) {
        this.fitnessProgramTypeId = fitnessProgramTypeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionEntity that = (SubscriptionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(fitnessProgramTypeId, that.fitnessProgramTypeId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fitnessProgramTypeId, userId);
    }
}
