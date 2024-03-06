package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramEntity;
import org.unibl.etf.onlinefitnessmanager.service.FitnessProgramService;

import java.util.List;

@Controller
@RequestMapping("/program")
public class FitnessProgramController {


    private final FitnessProgramService fitnessProgramService;

    @Autowired
    public FitnessProgramController(FitnessProgramService fitnessProgramService) {
        this.fitnessProgramService = fitnessProgramService;
    }

    @GetMapping("/find_all")
    public ResponseEntity<List<FitnessProgramEntity>> findAllFitnessPrograms()
    {
        List<FitnessProgramEntity> entityList = fitnessProgramService.findAllFitnessPrograms();
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @GetMapping("/find_by_name/{name}")
    public ResponseEntity<List<FitnessProgramEntity>> findFitnessProgramsByName(@PathVariable("name") String name)
    {
        List<FitnessProgramEntity> entityList = fitnessProgramService.findFitnessProgramsByName(name);
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<FitnessProgramEntity> findFitnessProgramById(@PathVariable Integer id)
    {
        FitnessProgramEntity entity = fitnessProgramService.findFitnessProgramById(id);


        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<FitnessProgramEntity> addFitnessProgram(@RequestBody FitnessProgramEntity newEntity)
    {
        FitnessProgramEntity addedEntity = fitnessProgramService.addFitnessProgram(newEntity);

        return new ResponseEntity<>(addedEntity, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<FitnessProgramEntity> updateFitnessProgram(@RequestBody FitnessProgramEntity newEntity)
    {
        FitnessProgramEntity updatedEntity = fitnessProgramService.updateFitnessProgram(newEntity);

        return new ResponseEntity<>(updatedEntity, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFitnessProgram(@PathVariable Integer id)
    {
        fitnessProgramService.deleteFitnessProgramById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
