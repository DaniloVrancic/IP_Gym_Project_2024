package org.unibl.etf.onlinefitnessmanager.model.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "work_diary", schema = "db_online_fitness", catalog = "")
public class WorkDiaryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToMany(mappedBy = "id")
    private List<CompletedExerciseEntity> completedExercises;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userByUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkDiaryEntity that = (WorkDiaryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<CompletedExerciseEntity> getCompletedExercises() {
        return completedExercises;
    }

    public void setCompletedExercises(List<CompletedExerciseEntity> completedExercises) {
        this.completedExercises = completedExercises;
    }

    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }
}
