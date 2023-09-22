package com.example.accounting.controllers;

import com.example.accounting.models.Good;
import com.example.accounting.models.Supplier;
import com.example.accounting.services.SupplierService;
import com.example.accounting.util.GoodNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/goods/{id}")
    public List<Good> getGoods(@PathVariable("id") int id) {
        return supplierService.getGoodsBySupplierId(id);
    }

    @GetMapping("/all")
    public List<Supplier> getAllSuppliers() {
        log.debug("Finding all");
        return supplierService.findAll();
    }

    @GetMapping("/{name}")
    public String getName(@PathVariable("name") String name) {
        return supplierService.getName(name);
    }

    //    пробный метод
    @GetMapping("/goods/query/{id}")
    public Supplier getSupplierByIdQuery(@PathVariable("id") int id) {
        return supplierService.getSupplierByIdQuery(id);
    }
}
