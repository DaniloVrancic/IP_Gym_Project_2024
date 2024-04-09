package org.unibl.etf.onlinefitnessmanager.model.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "completed_exercise", schema = "db_online_fitness", catalog = "")
public class CompletedExerciseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "type", nullable = false, length = 45)
    private String type;
    @Basic
    @Column(name = "duration", nullable = false)
    private Integer duration; 
    @Basic
    @Column(name = "intensity", nullable = false)
    private Integer intensity;
    @Basic
    @Column(name = "day_of_completion", nullable = true)
    private LocalDate dayOfCompletion;
    @Basic
    @Column(name = "weight_loss", nullable = false)
    private Integer weightLoss;
    @Basic
    @Column(name = "result_description", nullable = true, length = 64)
    private String resultDescription;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    public LocalDate getDayOfCompletion() {
        return dayOfCompletion;
    }

    public void setDayOfCompletion(LocalDate dayOfCompletion) {
        this.dayOfCompletion = dayOfCompletion;
    }

    public Integer getWeightLoss() {
        return weightLoss;
    }

    public void setWeightLoss(Integer weightLoss) {
        this.weightLoss = weightLoss;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public Integer getUserId()
    {
        return this.user_id;
    }

    public void setUserId(Integer user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompletedExerciseEntity that = (CompletedExerciseEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(user_id, that.user_id) && Objects.equals(type, that.type) && Objects.equals(duration, that.duration) && Objects.equals(intensity, that.intensity) && Objects.equals(dayOfCompletion, that.dayOfCompletion) && Objects.equals(weightLoss, that.weightLoss) && Objects.equals(resultDescription, that.resultDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, duration, intensity, dayOfCompletion, weightLoss, resultDescription, user_id);
    }
}
