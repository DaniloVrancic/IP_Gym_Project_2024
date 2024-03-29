package org.unibl.etf.onlinefitnessmanager.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private LocalDateTime commentTime;
    @ManyToOne
    @JoinColumn(name = "fitness_program_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
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

    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Date created: " );
        sb.append(this.commentTime);
        sb.append("\nComment text: ");
        sb.append(this.comment);
        sb.append("\nUser ID: ");
        sb.append(this.getUser_commenter().getId());
        sb.append("\nProgram ID: ");
        sb.append(this.getTargetFitnessProgram().getId());

        return sb.toString();
    }
}
