package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.model.entities.SubscriptionEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.SubscriptionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {


    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {this.subscriptionRepository = subscriptionRepository;}

    public SubscriptionEntity addSubscription(SubscriptionEntity newSubscription) {

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

    public Optional<SubscriptionEntity> subscriptionExists(Integer userId, Integer programTypeId)
    {
         Optional<SubscriptionEntity> foundOptional = subscriptionRepository.findAll()
                .stream()
                .filter(subscriptionEntity -> subscriptionEntity.getUserId().equals(userId))
                .filter(subscriptionEntity -> subscriptionEntity.getFitnessProgramTypeId().equals(programTypeId)).findFirst();

        return foundOptional;
    }

}