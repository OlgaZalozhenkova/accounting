package com.example.accounting.services;

import com.example.accounting.models.Operation;
import com.example.accounting.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OperationService {
    OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Transactional
    public void save(Operation operation) {
        operationRepository.save(operation);
    }
}
