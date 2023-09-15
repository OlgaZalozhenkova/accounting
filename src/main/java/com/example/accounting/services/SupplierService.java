package com.example.accounting.services;

import com.example.accounting.models.Good;
import com.example.accounting.models.Supplier;
import com.example.accounting.repositories.SupplierRepository;
import com.example.accounting.util.SupplierNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SupplierService {

    SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public void saveSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public List<Good> getGoodsBySupplierId(int id) {
        log.debug("Finding supplier by id");
        Supplier supplier = supplierRepository.findById(id).orElse(null);
        log.debug("Before getting goods");
        if (supplier == null) {
            throw new SupplierNotFoundException();
        } else {
            return supplier.getGoods();
        }
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public String getName(String name) {
        Supplier suppler = supplierRepository.findByName(name);
        if (suppler != null) {
            return suppler.getName();
        } else throw new SupplierNotFoundException();
    }
}

