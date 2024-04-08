package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.model.entities.PurchaseEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.PurchaseRepository;

import java.util.List;

@Service
public class PurchaseService {


    private PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {this.purchaseRepository = purchaseRepository;}

    public PurchaseEntity addPurchase(PurchaseEntity newPurchase)
    {

        PurchaseEntity savedNewPurchase = purchaseRepository.save(newPurchase);
        return savedNewPurchase;
    }

    public List<PurchaseEntity> findAllPurchases()
    {
        List<PurchaseEntity> found = purchaseRepository.findAll();

        return found;
    }

    public List<PurchaseEntity> findAllByUserId(Integer id)
    {
        return purchaseRepository.findByUserId(id);
    }

    public List<PurchaseEntity> findAllByProgramId(Integer program_id)
    {
        return purchaseRepository.findByFitnessProgramId(program_id);

    }

    public void removeById(Integer id)
    {
        purchaseRepository.deleteById(id);
    }
}
