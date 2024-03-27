package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.onlinefitnessmanager.model.entities.PurchaseEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.SubscriptionEntity;
import org.unibl.etf.onlinefitnessmanager.service.PurchaseService;
import org.unibl.etf.onlinefitnessmanager.service.SubscriptionService;

@Controller
@RequestMapping("/subscribe")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService)
    {
        this.subscriptionService = subscriptionService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addSubscription(@RequestParam("userId") Integer userId, @RequestParam("programId") Integer programId)
    {
        SubscriptionEntity newSub = new SubscriptionEntity();
        SubscriptionEntity returnedSub = null;
        try
        {
            newSub.setUserId(userId);
            newSub.setFitnessProgramTypeId(programId);
            returnedSub = subscriptionService.addSubscription(newSub);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("An error occured: " + ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<SubscriptionEntity>(returnedSub, HttpStatus.CREATED);
    }

    @GetMapping("/find_all")
    public ResponseEntity<?> findAll()
    {

        try{
            return new ResponseEntity<>(subscriptionService.findAllSubs(), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("An error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find_by_user")
    public ResponseEntity<?> findByUserId(@RequestParam("id") Integer id)
    {
        try
        {
            return new ResponseEntity<>(subscriptionService.findAllByUserId(id), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find_by_program")
    public ResponseEntity<?> findByProgramId(@RequestParam("id") Integer id)
    {
        try
        {
            return new ResponseEntity<>(subscriptionService.findAllByProgramTypeId(id), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public void removePurchaseBy(@RequestParam("id") Integer id)
    {
        subscriptionService.removeById(id);
    }
}
