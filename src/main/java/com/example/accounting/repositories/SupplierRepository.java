package com.example.accounting.repositories;

import com.example.accounting.models.Good;
import com.example.accounting.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findByName(String name);

    //Пробный запрос
    @Query("select s from Supplier s where s.id = :id")
    Supplier getSupplierByIdQuery(int id);
}
