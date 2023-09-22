package com.example.accounting.services;

import com.example.accounting.models.Good;
import com.example.accounting.models.GoodsObject;
import com.example.accounting.models.Operation;
import com.example.accounting.models.Supplier;
import com.example.accounting.repositories.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final SupplierService supplierService;
    private final OperationService operationService;


    @Autowired
    public GoodsService(GoodsRepository goodsRepository,
                        SupplierService supplierService,
                        OperationService operationService) {
        this.goodsRepository = goodsRepository;
        this.supplierService = supplierService;
        this.operationService = operationService;
    }

    public List<Good> findAll() {
        return goodsRepository.findAll();
//        return goodsRepository.orderByItem();
    }

    public Optional<Good> getGoodsById(int id) {
        return goodsRepository.findById(id);
    }


    public List<Good> findByItem(String item) {
        return goodsRepository.findByItem(item);
    }

    public List<Good> findByCategory(String name) {
        return goodsRepository.findByCategoryOrderByItemAsc(name);
    }

    @Transactional
    public void save(Good good) {
        goodsRepository.save(good);
    }

    @Transactional
    public void delete(int id) {
        goodsRepository.deleteById(id);
    }

    @Transactional
    public List<Good> deleteGroup(List<Integer> id) {
        List<Good> deletedGoods = new ArrayList<>();
        for (Integer goodId : id
        ) {
            deletedGoods.add(goodsRepository.findById(goodId).orElse(null));
            goodsRepository.deleteById(goodId);
        }
        return deletedGoods;
    }

//    @Transactional
//    public void deleteGroup(List<Integer> id) {
//        goodsRepository.deleteById(id);
//           }

    public String getTotalSum() {
        List<Good> goods = goodsRepository.findAll();
        Double totalSum = 0.0;
        for (Good good : goods
        ) {
            totalSum += good.getPrice() * good.getQuantity();
        }
        return Double.toString(totalSum);
    }

    @Transactional
    public List<Good> saveGoodsObject(GoodsObject goodsObject) {
        List<Good> goods = goodsObject.getGoods();
        return goodsRepository.saveAll(goods);
    }

    @Transactional
    public List<Good> supplyGoods(GoodsObject goodsObject) {
        List<Good> goods = goodsObject.getGoods();

// получаю первый товар в списке, чтобы получить поставщика
        Good good = goods.get(0);
        //  получаю первого поставщика в списке, так как поставщик на одну
//  поставку один и тот же
        Supplier supplier = good.getSuppliers().get(0);

        supplierService.saveSupplier(supplier);
        Operation operation = new Operation("supply", new Date(), goods);
        operationService.save(operation);

// TODO: 18.09.2023 пересчет стоимости и количества
        return goodsRepository.saveAll(goods);
    }

    @Transactional
    public List<Good> makePurchase(GoodsObject goodsObject) {
        List<Good> goods = goodsObject.getGoods();
        return goodsRepository.saveAll(goods);
    }

    public List<Good> findSupplierGoods(int id) {
        return goodsRepository.getGoodsBySupplierId(id);
    }





    //    только поставка
    public List<Good> getGoodsByPeriod(LocalDateTime start, LocalDateTime end) {
        return goodsRepository.findAll();
    }

//    public Good getGoodByIdQuery() {
//        return goodsRepository.getGoodByIdQuery();
//    }
}
