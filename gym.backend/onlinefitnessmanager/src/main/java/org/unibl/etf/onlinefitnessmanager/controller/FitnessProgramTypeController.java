package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.onlinefitnessmanager.exception.FitnessProgramTypeNotFoundException;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramTypeEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.SpecificProgramAttributeEntity;
import org.unibl.etf.onlinefitnessmanager.service.FitnessProgramTypeService;

import java.util.List;

@Controller
@RequestMapping("/program_type")
public class FitnessProgramTypeController {

    private final FitnessProgramTypeService programTypeService;

    @Autowired
    public FitnessProgramTypeController(FitnessProgramTypeService programTypeService) {
        this.programTypeService = programTypeService;
    }

    @GetMapping("/get_all_types")
    public ResponseEntity<List<FitnessProgramTypeEntity>> getAllFitnessProgramTypes()
    {
        List<FitnessProgramTypeEntity> entityList = programTypeService.findAllFitnessProgramTypes();

        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    //////////////////////////// PROGRAM TYPES SECTION /////////////////////////////////////
    @GetMapping("/get_types/{text}")
    public ResponseEntity<List<FitnessProgramTypeEntity>> getFitnessProgramTypeByText(@PathVariable("text") String text)
    {
        List<FitnessProgramTypeEntity> entityList = programTypeService.findFitnessProgramsContainingString(text);

        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @GetMapping("/get_type/{id}")
    public ResponseEntity<FitnessProgramTypeEntity> getFitnessProgramTypeByText(@PathVariable("id") Integer id)
    {
        try
        {
            FitnessProgramTypeEntity entity = programTypeService.findFitnessProgramTypeById(id);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        catch(FitnessProgramTypeNotFoundException ex)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_type")
    public ResponseEntity<FitnessProgramTypeEntity> updateFitnessProgramType(@RequestBody FitnessProgramTypeEntity typeEntity)
    {
        FitnessProgramTypeEntity newEntity = programTypeService.updateFitnessProgramType(typeEntity);

        if(!newEntity.equals(null))
            return new ResponseEntity<>(newEntity, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add_type")
    public ResponseEntity<FitnessProgramTypeEntity> addFitnessProgramType(@RequestBody FitnessProgramTypeEntity typeEntity)
    {
        FitnessProgramTypeEntity newEntity = programTypeService.addFitnessProgramType(typeEntity);
        if(!newEntity.equals(null))
        {
            return new ResponseEntity<>(newEntity, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete_type/{id}")
    public ResponseEntity<?> deleteFitnessProgramType(@PathVariable Integer id)
    {
        programTypeService.deleteFitnessProgramType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //////////////////////////// SPECIFIC ATTRIBUTES SECTION /////////////////////////////////////

    @GetMapping("/get_all_attributes")
    public ResponseEntity<List<SpecificProgramAttributeEntity>> getAllSpecificAttributes()
    {
        List<SpecificProgramAttributeEntity> entityList = programTypeService.findAllSpecificAttributes();

        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @GetMapping("/get_attributes_for_type/{typeId}")
    public ResponseEntity<List<SpecificProgramAttributeEntity>> getAllSpecificAttributesForTypeId(@PathVariable("typeId") Integer typeId)
    {
        List<SpecificProgramAttributeEntity> entityList = programTypeService.findSpecificAttributesForType(typeId);

        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @GetMapping("/get_attribute/{id}")
    public ResponseEntity<SpecificProgramAttributeEntity> getSpecificAttributeWithId(@PathVariable("id") Integer id)
    {
        SpecificProgramAttributeEntity entity = programTypeService.findSpecificProgramAttributeById(id);

        if(entity != null)
        {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update_attribute")
    public ResponseEntity<SpecificProgramAttributeEntity> updateSpecificAttribute(@RequestBody SpecificProgramAttributeEntity specific_attribute)
    {
        SpecificProgramAttributeEntity entity = programTypeService.updateSpecificProgramAttribute(specific_attribute);

        if(entity != null)
        {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add_attribute")
    public ResponseEntity<SpecificProgramAttributeEntity> addSpecificAttribute(@RequestBody SpecificProgramAttributeEntity specific_attribute)
    {
        SpecificProgramAttributeEntity entity = programTypeService.addSpecificProgramAttribute(specific_attribute.getFitnessProgramType().getId(), specific_attribute);

        if(entity != null)
        {
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_attribute/{id}")
    public ResponseEntity<?> deleteSpecificAttribute(@PathVariable("id") Integer id) {
        programTypeService.deleteSpecificProgramAttribute(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
