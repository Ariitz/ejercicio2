package com.example.demo.controller;

import com.example.demo.entity.RequestEntity;
import com.example.demo.entity.SupplyEntity;
import com.example.demo.exceptions.NoDemandException;
import com.example.demo.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SupplyControler{
    @Autowired
    private SupplyService supplyService;

    @PostMapping("/getAvailability")
    public SupplyEntity getAvailability(@RequestBody RequestEntity prodId) throws NoDemandException {
        System.out.println("prodId");
        System.out.println(prodId);
        return supplyService.getAvailability(prodId.getProductId());
    }
}
