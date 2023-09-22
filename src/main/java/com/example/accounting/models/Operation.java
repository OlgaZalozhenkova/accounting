package com.example.accounting.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "operations")
public class Operation {
    @Id
    @Column(name = "id")
    int id;

//    Сделать ENUM
    @Column(name = "operation_type")
    String operationType;

    @Column(name = "date")
    Date date;

    @JsonManagedReference
    @ManyToMany(mappedBy = "operations")
    private List<Good> goods;

    public Operation(String operationType, Date date, List<Good> goods) {
        this.operationType = operationType;
        this.date = date;
        this.goods = goods;
    }
}
