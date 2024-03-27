package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.onlinefitnessmanager.model.entities.PurchaseEntity;
import org.unibl.etf.onlinefitnessmanager.service.PurchaseService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    private PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService)
    {
        this.purchaseService = purchaseService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addPurchase(@RequestParam("userId") Integer userId, @RequestParam("programId") Integer programId)
    {
        PurchaseEntity newPurchase = new PurchaseEntity();
        PurchaseEntity returnedPurchase = null;
        try
        {
            newPurchase.setUserId(userId);
            newPurchase.setFitnessProgramId(programId);
            newPurchase.setTimeOfPurchase(LocalDateTime.now());
            returnedPurchase = purchaseService.addPurchase(newPurchase);
        }
        catch(Exception ex)
        {
        return new ResponseEntity<>("An error occured: " + ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PurchaseEntity>(returnedPurchase, HttpStatus.CREATED);
    }

    @GetMapping("/find_all")
    public ResponseEntity<?> findAll()
    {

        try{
            return new ResponseEntity<>(purchaseService.findAllPurchases(), HttpStatus.OK);
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
            return new ResponseEntity<>(purchaseService.findAllByUserId(id), HttpStatus.OK);
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
            return new ResponseEntity<>(purchaseService.findAllByProgramId(id), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public void removePurchaseBy(@RequestParam("id") Integer id)
    {
        purchaseService.removeById(id);
    }
}
