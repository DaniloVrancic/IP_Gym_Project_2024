package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.exception.CompletedExerciseNotFoundException;
import org.unibl.etf.onlinefitnessmanager.exception.FitnessProgramNotFoundException;
import org.unibl.etf.onlinefitnessmanager.model.entities.CompletedExerciseEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.CompletedExerciseRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompletedExerciseService {

    private final CompletedExerciseRepository completedExerciseRepository;

    @Autowired
    public CompletedExerciseService(CompletedExerciseRepository completedExerciseRepository)
    {
        this.completedExerciseRepository = completedExerciseRepository;
    }

    public CompletedExerciseEntity addCompletedExercise(CompletedExerciseEntity entity)
    {
        if(entity.getDayOfCompletion() == null || "".equals(entity.getDayOfCompletion().toString()))
        {
            LocalDateTime currentTime = LocalDateTime.now();
            entity.setDayOfCompletion(currentTime.toLocalDate());
        }
        return this.completedExerciseRepository.save(entity);
    }

    public void deleteCompletedExerciseById(Integer id)
    {
        this.completedExerciseRepository.deleteById(id);
    }

    public CompletedExerciseEntity updateCompletedExercise(CompletedExerciseEntity newEntity)
    {
        return this.completedExerciseRepository.save(newEntity);
    }

    public List<CompletedExerciseEntity> findAllFitnessPrograms()
    {
        return this.completedExerciseRepository.findAll();
    }

    public CompletedExerciseEntity findCompletedExerciseById(Integer id)
    {
        return this.completedExerciseRepository.findById(id).orElseThrow(() -> new CompletedExerciseNotFoundException());
    }

    public List<CompletedExerciseEntity> findCompletedExerciseByUserId(Integer userId)
    {
        return this.completedExerciseRepository.findAll().stream().filter(entry -> entry.getUserId().equals(userId)).collect(Collectors.toList());
    }


}
