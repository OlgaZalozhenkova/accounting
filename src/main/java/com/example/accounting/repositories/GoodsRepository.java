package com.example.accounting.repositories;

import com.example.accounting.models.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Good, Integer> {
    List<Good> findByItem(String item);

    List<Good> findByCategoryOrderByItemAsc(String name);

//    @Query("select g from Good g join fetch g.suppliers s where s.id = :id")
//    List<Good> getGoodsBySupplierId(int id);

//    @Query(value = "select * from goods g join goods_suppliers gs on g.id = gs.goods_id " +
//            "join suppliers s on s.id = gs.suppliers_id where s.id = :id"
//            , nativeQuery = true)

    @Query(value = "select g.item from goods g join goods_suppliers gs on g.id = gs.goods_id join suppliers s on gs.suppliers_id = s.id"
            , nativeQuery = true)
    List<Good> getGoodsBySupplierId();

    @Query("select g from Good g where g.id = :id")
    Optional<Good> getGoodByIdQuery(int id);
}

