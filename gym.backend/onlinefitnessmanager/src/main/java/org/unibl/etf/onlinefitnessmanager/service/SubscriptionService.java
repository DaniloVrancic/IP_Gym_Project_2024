package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.model.entities.SubscriptionEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.SubscriptionRepository;

import java.util.List;

@Service
public class SubscriptionService {


    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {this.subscriptionRepository = subscriptionRepository;}

    public SubscriptionEntity addSubscription(SubscriptionEntity newSubscription)
    {

        SubscriptionEntity savedNewSubscribe = subscriptionRepository.save(newSubscription);
        return savedNewSubscribe;
    }

    public List<SubscriptionEntity> findAllSubs()
    {
        return subscriptionRepository.findAll();
    }

    public List<SubscriptionEntity> findAllByUserId(Integer id)
    {
        return subscriptionRepository.findByUserId(id);
    }

    public List<SubscriptionEntity> findAllByProgramTypeId(Integer programType_id)
    {
        return subscriptionRepository.findByFitnessProgramTypeId(programType_id);

    }

    public void removeById(Integer id)
    {
        subscriptionRepository.deleteById(id);
    }
}