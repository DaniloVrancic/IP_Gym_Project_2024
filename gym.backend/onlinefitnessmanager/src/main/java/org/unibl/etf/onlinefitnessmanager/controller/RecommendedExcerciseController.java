package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.onlinefitnessmanager.model.entities.RecommendedExcerciseEntity;
import org.unibl.etf.onlinefitnessmanager.service.RecommendedExcerciseService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recommended")
public class RecommendedExcerciseController {

    private RecommendedExcerciseService recommendedExcerciseService;

    @Autowired
    public RecommendedExcerciseController(RecommendedExcerciseService recommendedExcerciseService)
    {
        this.recommendedExcerciseService = recommendedExcerciseService;
    }
    @GetMapping("/get")
    public ResponseEntity<?> getRecommended()
    {
        String foundExcercise = "";
        try
        {
            foundExcercise = this.recommendedExcerciseService.findTodaysExcercise();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getLocalizedMessage());
        }
        if(!foundExcercise.isEmpty())
        {
            return new ResponseEntity<>(foundExcercise, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
        }
    }
}
