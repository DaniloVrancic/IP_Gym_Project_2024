package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.repositories.PurchaseRepository;

@Service
public class PurchaseService {


    private PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {this.purchaseRepository = purchaseRepository;}
}
