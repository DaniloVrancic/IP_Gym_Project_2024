package org.unibl.etf.onlinefitnessmanager.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/*
This Entity serves for Objects to store information
about specific Fitness programs.

Fitness Programs encapsulate various data, including a certain category, which is an Entity
in itself.

 */
@Data
@Entity
@Table(name = "fitness_program")
public class FitnessProgramEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
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
    @Column(name = "difficultyLevel", nullable = true)
    private Integer difficultyLevel;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "fk_fitness_program_type_id", referencedColumnName = "id", nullable = false)
    private FitnessProgramTypeEntity fitnessProgramType;

}
