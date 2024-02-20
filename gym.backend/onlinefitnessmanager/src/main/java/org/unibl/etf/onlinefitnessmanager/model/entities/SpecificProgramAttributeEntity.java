package org.unibl.etf.onlinefitnessmanager.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "specific_program_attribute", schema = "db_online_fitness", catalog = "")
public class SpecificProgramAttributeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = true, length = 64)
    private String name;
    @Basic
    @Column(name = "value", nullable = true, length = 128)
    private String value;
    @ManyToOne
    @JoinColumn(name = "fitness_program_type_id", referencedColumnName = "id", nullable = false)
    private FitnessProgramTypeEntity fitnessProgramType;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificProgramAttributeEntity that = (SpecificProgramAttributeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }

    public FitnessProgramTypeEntity getFitnessProgramType() {
        return fitnessProgramType;
    }

    public void setFitnessProgramType(FitnessProgramTypeEntity fitnessProgramType) {
        this.fitnessProgramType = fitnessProgramType;
    }
}
