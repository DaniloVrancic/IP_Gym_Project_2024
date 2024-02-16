package org.unibl.etf.onlinefitnessmanager.model.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "comment", schema = "db_online_fitness", catalog = "")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "comment", nullable = false, length = 1024)
    private String comment;
    @Basic
    @Column(name = "comment_time", nullable = false)
    private Timestamp commentTime;
    @ManyToOne
    @JoinColumn(name = "fitness_program_id", referencedColumnName = "id", nullable = false)
    private FitnessProgramEntity targetFitnessProgram;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user_commenter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(comment, that.comment) && Objects.equals(commentTime, that.commentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, commentTime);
    }

    public FitnessProgramEntity getTargetFitnessProgram() {
        return targetFitnessProgram;
    }

    public void setTargetFitnessProgram(FitnessProgramEntity targetFitnessProgram) {
        this.targetFitnessProgram = targetFitnessProgram;
    }

    public UserEntity getUser_commenter() {
        return user_commenter;
    }

    public void setUser_commenter(UserEntity user_commenter) {
        this.user_commenter = user_commenter;
    }
}
