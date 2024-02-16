package org.unibl.etf.onlinefitnessmanager.model.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "fitness_program", schema = "db_online_fitness", catalog = "")
public class FitnessProgramEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = 512)
    private String description;
    @Basic
    @Column(name = "location_of_event", nullable = true, length = 128)
    private String locationOfEvent;
    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    private Double price;
    @Basic
    @Column(name = "duration", nullable = true)
    private Integer duration;
    @Basic
    @Column(name = "difficulty_level", nullable = true)
    private Integer difficultyLevel;
    @Basic
    @Column(name = "image_url", nullable = true, length = 512)
    private String imageUrl;
    @Basic
    @Column(name = "status", nullable = false, length = 8)
    private String status;
    @OneToMany(mappedBy = "targetFitnessProgram")
    private List<CommentEntity> comments;
    @ManyToOne
    @JoinColumn(name = "fitness_program_type_id", referencedColumnName = "id", nullable = false)
    private FitnessProgramTypeEntity fitnessProgramTypeByFitnessProgramTypeId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user_creator;
    @OneToMany(mappedBy = "fitnessProgramId")
    private List<PurchaseEntity> purchasesById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationOfEvent() {
        return locationOfEvent;
    }

    public void setLocationOfEvent(String locationOfEvent) {
        this.locationOfEvent = locationOfEvent;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FitnessProgramEntity that = (FitnessProgramEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(locationOfEvent, that.locationOfEvent) && Objects.equals(price, that.price) && Objects.equals(duration, that.duration) && Objects.equals(difficultyLevel, that.difficultyLevel) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, locationOfEvent, price, duration, difficultyLevel, imageUrl, status);
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public FitnessProgramTypeEntity getFitnessProgramTypeByFitnessProgramTypeId() {
        return fitnessProgramTypeByFitnessProgramTypeId;
    }

    public void setFitnessProgramTypeByFitnessProgramTypeId(FitnessProgramTypeEntity fitnessProgramTypeByFitnessProgramTypeId) {
        this.fitnessProgramTypeByFitnessProgramTypeId = fitnessProgramTypeByFitnessProgramTypeId;
    }

    public UserEntity getUser_creator() {
        return user_creator;
    }

    public void setUser_creator(UserEntity user_creator) {
        this.user_creator = user_creator;
    }

    public List<PurchaseEntity> getPurchasesById() {
        return purchasesById;
    }

    public void setPurchasesById(List<PurchaseEntity> purchasesById) {
        this.purchasesById = purchasesById;
    }
}
