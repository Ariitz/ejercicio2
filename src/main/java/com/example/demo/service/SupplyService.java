package com.example.demo.service;

import com.example.demo.entity.SupplyEntity;
import com.example.demo.exceptions.NoDemandException;
import com.example.demo.exceptions.NoDemandsAdvice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyService {
    SupplyEntity supply1 = new SupplyEntity("Product1", 10.0);
    SupplyEntity supply2 = new SupplyEntity("Product2", 5.0);

    SupplyEntity demand1 = new SupplyEntity("Product1", 2.0);
    SupplyEntity demand2 = new SupplyEntity("Product2", 5.0);

    List<SupplyEntity> supply = Arrays.asList(supply1, supply2);
    List<SupplyEntity> demand = Arrays.asList(demand1, demand2);

    public SupplyEntity getAvailability(String prodId) throws NoDemandException{
       return supply.stream().filter(supplyEntity -> {
                   System.out.println(supplyEntity.getProductId().equals(prodId));
                   System.out.println(supplyEntity.getProductId());
                   System.out.println(prodId);
           return supplyEntity.getProductId().equals(prodId);
               })
                .map(supplyEntity -> {
                    System.out.println(supplyEntity);
                    SupplyEntity totalDemand = demand.stream()
                            .filter(demandEntity -> demandEntity.getProductId().equals(prodId))
                            .reduce(
                                    (x,y) -> {
                                        System.out.println(x + "    " + y);
                                        return new SupplyEntity(x.getProductId(), x.getQuantity() + y.getQuantity());
                                    }
                            ).orElse(null);
                    System.out.println("totalDemand antes de");
                    System.out.println(totalDemand);

                    Double availability = supplyEntity.getQuantity() - totalDemand.getQuantity();
                return availability > 0 ? new SupplyEntity(prodId, availability) : null;

                }).filter(supplyEntity -> supplyEntity != null).findFirst().orElseThrow(NoDemandException::new);
    }
}
