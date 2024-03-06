package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.exception.FitnessProgramTypeNotFoundException;
import org.unibl.etf.onlinefitnessmanager.exception.FitnessTypeAlreadyExistsException;
import org.unibl.etf.onlinefitnessmanager.exception.SpecificProgramAttributeAlreadyExistsException;
import org.unibl.etf.onlinefitnessmanager.exception.SpecificProgramAttributeNotFoundException;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramTypeEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.SpecificProgramAttributeEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.FitnessProgramTypeRepository;
import org.unibl.etf.onlinefitnessmanager.repositories.SpecificProgramAttributeRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FitnessProgramTypeService {


    private final FitnessProgramTypeRepository programTypeRepository;

    private final SpecificProgramAttributeRepository specificProgramAttributeRepository;

    private static Set<String> setOfProgramTypes; //Contains unique names of all existing program types

    @Autowired
    public FitnessProgramTypeService(FitnessProgramTypeRepository programTypeRepository, SpecificProgramAttributeRepository specificProgramAttributeRepository) {
        this.programTypeRepository = programTypeRepository;
        this.specificProgramAttributeRepository = specificProgramAttributeRepository;

        setOfProgramTypes = this.programTypeRepository.findAll().stream()
                                                                .map(x -> x.getName().toLowerCase())
                                                                .collect(Collectors.toSet()); //Gets all elements from the database, and only allows them to appear once
    }

    public FitnessProgramTypeEntity addFitnessProgramType(FitnessProgramTypeEntity fitness_type)
    {
        if(!setOfProgramTypes.contains(fitness_type.getName().toLowerCase())) //if it's not in the set of existing program types, then you can add it
        {
            setOfProgramTypes.add(fitness_type.getName().toLowerCase()); //first add it to the set to keep track of new incoming types
            return this.programTypeRepository.save(fitness_type); // if the FitnessProgramTypeEntity doesn't exist in the database, then save it to the database
        }
        else
        {
            throw new FitnessTypeAlreadyExistsException( FitnessTypeAlreadyExistsException.class.getName() + " : Fitness program type: " + fitness_type.getName() + " already exists in the database!");
        }
    }

    public FitnessProgramTypeEntity updateFitnessProgramType(FitnessProgramTypeEntity fitness_type)
    {
        return this.programTypeRepository.save(fitness_type);
    }

    public void deleteFitnessProgramType(Integer id)
    {
        this.programTypeRepository.deleteById(id);
    }

    public List<FitnessProgramTypeEntity> findAllFitnessProgramTypes()
    {
        return programTypeRepository.findAll();
    }

    public List<FitnessProgramTypeEntity> findFitnessProgramsContainingString(String text)
    {
        return programTypeRepository.findAll()
                .stream()
                .filter(x -> x.getName().contains(text))
                .collect(Collectors.toList());
    }

    public FitnessProgramTypeEntity findFitnessProgramTypeById(Integer id)
    {
        return programTypeRepository.findById(id).orElseThrow(() -> new FitnessProgramTypeNotFoundException("Fitness program type with ID: " + id + " not found!"));
    }

    /**
     * Adds a specific attribute to a given Fitness type, if that specific attribute doesn't already exists,
     * if it exists, throws RuntimeException
     * @param typeId id of the Fitness type that this specific attribute should get attached to.
     * @param specific_attribute A specific attribute to add
     * @throws SpecificProgramAttributeAlreadyExistsException if the specific attribute name that is being added already exists in the given Fitness Type
     * @return SpecificProgramAttributeEntity if it is successfully added
     */
    public SpecificProgramAttributeEntity addSpecificProgramAttribute(Integer typeId, SpecificProgramAttributeEntity specific_attribute)
    {
        Set<String> specificAttributesNames = specificProgramAttributeRepository.findAll().stream()
                                                                                                             .filter(x -> x.getFitnessProgramType().getId().equals(typeId))
                                                                                                             .map(x -> x.getName().toLowerCase())
                                                                                                             .collect(Collectors.toSet()); //Gets the names of all specific attributes for given Fitness Type

        if(!specificAttributesNames.contains(specific_attribute.getName().toLowerCase()))
        {
            return specificProgramAttributeRepository.save(specific_attribute);
        }
        else
        {
            throw new SpecificProgramAttributeAlreadyExistsException(FitnessTypeAlreadyExistsException.class.getName()
                                                                    + " : Fitness program type: "
                                                                    + specific_attribute.getFitnessProgramType().getName()
                                                                    + " already exists on the given specifit attribute: "
                                                                    + specific_attribute.getName());
        }
    }

    public SpecificProgramAttributeEntity updateSpecificProgramAttribute(SpecificProgramAttributeEntity specific_attribute)
    {
        return this.specificProgramAttributeRepository.save(specific_attribute);
    }

    public void deleteSpecificProgramAttribute(Integer id)
    {
        this.specificProgramAttributeRepository.deleteById(id);
    }

    public SpecificProgramAttributeEntity findSpecificProgramAttributeById(Integer id)
    {
        return this.specificProgramAttributeRepository.findById(id).orElseThrow(() -> new SpecificProgramAttributeNotFoundException("Specific attribute with ID: " + id + " not found."));
    }


    public List<SpecificProgramAttributeEntity> findAllSpecificAttributes()
    {
        return specificProgramAttributeRepository.findAll();
    }

    /**
     *
     * @param typeId id of the program type that we are looking up
     * @return
     */
    public List<SpecificProgramAttributeEntity> findSpecificAttributesForType(Integer typeId)
    {
        return specificProgramAttributeRepository.findAll().stream()
                                                           .filter(x -> x.getFitnessProgramType().getId().equals(typeId))
                                                           .collect(Collectors.toList());
    }


}
