package org.unibl.etf.onlinefitnessmanager.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

/*

By user request, Fitness Program Types should enable CRUD operations, therefore
they have been encapsulated as a class for themselves.

All Fitness Programs have their own Lists of Type-specific attributes, which are stored
in a seperate Entity.
 */
@Data
@Entity
@Table(name = "fitness_program_type")
public class FitnessProgramTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = 128)
    private String name;
    @OneToMany(mappedBy = "fitnessProgramType")
    @JsonIgnore
    private List<FitnessProgramEntity> fitnessProgramsById;
    @OneToMany(mappedBy = "fitnessProgramType")
    @JsonIgnore
    private List<SpecificProgramAttributeEntity> specificProgramAttributesById;

}
