package com.example.demo.service;

import com.example.demo.entity.SupplyEntity;
import com.example.demo.exceptions.NoDemandException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SupplyService {
    SupplyEntity supply1 = new SupplyEntity("Product1", 10.0);
    SupplyEntity supply2 = new SupplyEntity("Product2", 5.0);

    SupplyEntity demand1 = new SupplyEntity("Product1", 2.0);
    SupplyEntity demand2 = new SupplyEntity("Product2", 5.0);

    List<SupplyEntity> supply = Arrays.asList(supply1, supply2);
    List<SupplyEntity> demand = Arrays.asList(demand1, demand2);

    public SupplyEntity getAvailability(String prodId) throws NoDemandException{
       return supply.stream().filter(supplyEntity -> supplyEntity.getProductId().equals(prodId))
                .map(supplyEntity -> {
                    SupplyEntity totalDemand = demand.stream()
                            .filter(demandEntity -> demandEntity.getProductId().equals(prodId))
                            .reduce(
                                    (x,y) ->
                                        new SupplyEntity(x.getProductId(), x.getQuantity() + y.getQuantity())

                            ).orElse(null);

                    Double availability = supplyEntity.getQuantity() - totalDemand.getQuantity();
                return availability > 0 ? new SupplyEntity(prodId, availability) : null;

                }).filter(supplyEntity -> supplyEntity != null).findFirst().orElseThrow(NoDemandException::new);
    }
}
