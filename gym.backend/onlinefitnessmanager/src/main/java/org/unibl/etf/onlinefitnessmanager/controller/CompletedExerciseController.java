package org.unibl.etf.onlinefitnessmanager.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.onlinefitnessmanager.model.entities.CompletedExerciseEntity;
import org.unibl.etf.onlinefitnessmanager.service.CompletedExerciseService;

import java.util.List;

@Controller
@RequestMapping("/completed")
public class CompletedExerciseController
{

    private final CompletedExerciseService completedExerciseService;

    @Autowired
    public CompletedExerciseController(CompletedExerciseService completedExerciseService)
    {
        this.completedExerciseService = completedExerciseService;
    }


    @GetMapping("/find_all")
    public ResponseEntity<List<CompletedExerciseEntity>> findAllCompletedExercise()
    {
        List<CompletedExerciseEntity> listToReturn = this.completedExerciseService.findAllFitnessPrograms();
        return new ResponseEntity<>(listToReturn, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findCompletedExerciseById(@PathVariable("id") Integer id)
    {
        CompletedExerciseEntity foundEntity = this.completedExerciseService.findCompletedExerciseById(id);

        if(foundEntity != null)
        {
            return new ResponseEntity<>(foundEntity, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find_by_user/{id}")
    public ResponseEntity<List<CompletedExerciseEntity>> findCompletedExerciseByUserId(@PathVariable("id") Integer id)
    {
        List<CompletedExerciseEntity> listOfExerciseForUser = this.completedExerciseService.findCompletedExerciseByUserId(id);

        return new ResponseEntity<>(listOfExerciseForUser, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CompletedExerciseEntity> addCompletedExercise(@RequestBody CompletedExerciseEntity entityToAdd)
    {
        CompletedExerciseEntity addedEntity = this.completedExerciseService.addCompletedExercise(entityToAdd);

        return new ResponseEntity<>(addedEntity, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CompletedExerciseEntity> updateCompletedExercise(@RequestBody CompletedExerciseEntity entityToUpdate)
    {
        CompletedExerciseEntity updatedEntity = this.completedExerciseService.updateCompletedExercise(entityToUpdate);

        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompletedExercise(@PathVariable Integer id)
    {
        this.completedExerciseService.deleteCompletedExerciseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
