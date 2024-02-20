package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.exception.FitnessProgramNotFoundException;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramTypeEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.FitnessProgramRepository;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FitnessProgramService {

    private final FitnessProgramRepository programRepository;

    @Autowired
    public FitnessProgramService(FitnessProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public FitnessProgramEntity addFitnessProgram(FitnessProgramEntity entity)
    {
        return this.programRepository.save(entity);
    }

    public FitnessProgramEntity updateFitnessProgram(FitnessProgramEntity entity)
    {
        return this.programRepository.save(entity);
    }

    public List<FitnessProgramEntity> findAllFitnessPrograms()
    {
        return this.programRepository.findAll();
    }

    public List<FitnessProgramEntity> findFitnessProgramsByName(String name)
    {
        return this.programRepository.findAll().stream()
                                               .filter(x -> x.getName().contains(name))
                                               .collect(Collectors.toList());
    }

    public FitnessProgramEntity findFitnessProgramById(Integer id)
    {
        return this.programRepository.findById(id).orElseThrow(() -> new FitnessProgramNotFoundException());
    }

    public void deleteFitnessProgramById(Integer id)
    {
        this.programRepository.deleteById(id);
    }


}
