package org.unibl.etf.onlinefitnessmanager.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/*
Specific attributes which are added by users with administrator priviledges.
They are added with Names, and are referenced to specified Type via type ID which
is stored here as a foreign key.
 */
@Data
@Entity
@Table(name = "specific_program_attribute")
public class SpecificProgramAttributeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = true, length = 64)
    private String name;
    @Basic
    @Column(name = "value", nullable = true, length = 128)
    private String value;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "fk_fitness_program_type_id", referencedColumnName = "id", nullable = false)
    private FitnessProgramTypeEntity fitnessProgramType;

}
