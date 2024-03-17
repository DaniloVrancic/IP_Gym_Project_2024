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
        FitnessProgramEntity newProgramWithPhoto; //Can also be without photo if no photo was ever set
        try
        {
            String coverPhoto = newEntity.getImageUrl(); //Saves the full raw data into String avatar
            newEntity.setImageUrl(null); //sets the Avatar inside user to null so that it doesn't try to save the whole raw picture data to the database
            newProgramWithPhoto = fitnessProgramService.addFitnessProgram(newEntity); //saves to Database (without profile picture link)

            if(coverPhoto != null && coverPhoto.length() > 0) //checks if there is any Base64 data present.
            {
                newProgramWithPhoto.setImageUrl(fitnessProgramService.saveBase64EncodedPhoto(coverPhoto, newProgramWithPhoto));
                fitnessProgramService.updateFitnessProgram(newProgramWithPhoto); //updates the daTabase with the link to the uploaded photo
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(newProgramWithPhoto, HttpStatus.CREATED);
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
