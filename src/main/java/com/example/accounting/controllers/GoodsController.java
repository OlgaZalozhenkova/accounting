package com.example.accounting.controllers;

import com.example.accounting.models.Good;
import com.example.accounting.models.GoodsObject;
import com.example.accounting.models.Supplier;
import com.example.accounting.services.GoodsService;
import com.example.accounting.services.SupplierService;
import com.example.accounting.util.GoodNotCreatedException;
import com.example.accounting.util.GoodNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class GoodsController {
    public final GoodsService goodsService;
    public final SupplierService supplierService;

    @Autowired
    public GoodsController(GoodsService goodsService, SupplierService supplierService) {
        this.goodsService = goodsService;
        this.supplierService = supplierService;
    }

    @GetMapping("/goods")
    public List<Good> findAll() {
        return goodsService.findAll();
    }

    @GetMapping("/goods/name")
    public List<Good> findGoodByName(@RequestParam("item") String item) {
        List<Good> good = goodsService.findByItem(item);
        if (good != null) {
            return good;
        } else {
            throw new GoodNotFoundException();
        }
    }

    @GetMapping("/goods/{id}")
//    @GetMapping("/{id}")
    public Good getById(@PathVariable("id") int id) {

        return goodsService.getGoodsById(id).orElseThrow(
                () -> new GoodNotFoundException()
        );
    }

    @GetMapping("/supplier/goods/{id}")
    public List<Good> findSupplierGoods(@PathVariable("id") int id) {
         return goodsService.findSupplierGoods(id);
    }

////    пробный метод
//    @GetMapping("/good/query/{id}")
//    public Good getGoodByIdQuery(@PathVariable("id") int id) {
//        return goodsService.getGoodByIdQuery(id);
//    }

//    //    пробный метод
//    @GetMapping("/good/query")
//    public Good getGoodByIdQuery() {
//        return goodsService.getGoodByIdQuery();
//    }

//    вернуть обратно
    @GetMapping("findId/{id}/{id1}")
    public String findId(@PathVariable("id") int id,@PathVariable("id1") int id1) {
        System.out.println(id1);
        Good good = goodsService.getGoodsById(id).orElse(null);
        if (good != null) {

            return Integer.toString(good.getId());
        } else return null;
    }

    @GetMapping("/category")
    List<Good> findByCategory(@RequestParam("name") String name) {
        return goodsService.findByCategory(name);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Good good,
                                             BindingResult bindingResult) {
//        log.debug("Creating a new good");
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors
            ) {
                errorMsg.append(error.getField()).append("-")
                        .append(error.getDefaultMessage()).append(";");
            }
            throw new GoodNotCreatedException(errorMsg.toString());
        }

        List<Supplier> suppliers = good.getSuppliers();
        Supplier supplier = suppliers.get(0);
        supplierService.saveSupplier(supplier);
        goodsService.save(good);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<HttpStatus> update(@RequestBody Good good) {
        goodsService.getGoodsById(good.getId())
                .orElseThrow(
                        () -> new GoodNotFoundException()
                );
        goodsService.save(good);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        goodsService.getGoodsById(id)
                .orElseThrow(
                        () -> new GoodNotFoundException()
                );
        goodsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/group")
    public List<Good> deleteGroup(@RequestBody List<Integer> id) {
        return goodsService.deleteGroup(id);
    }

//    @DeleteMapping("/delete/group")
//    public void deleteGroup(@RequestBody List<Integer> id) {
//        goodsService.deleteGroup(id);
//    }

    @GetMapping("/total")
    public String getTotalSum() {
        return goodsService.getTotalSum();
    }

    //    меняем данные на сервере, поэтому Post запрос
//    пока без валидации
//    либо добавляем новый либо меняем
//    как послать список
//    @PostMapping("make/inventory")
//    public ResponseEntity<HttpStatus> makeInventory(@RequestBody Good good) {
//        goodsService.save(good);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }


//    @PostMapping("make/inventory")
//    public List<Good> makeInventory(@RequestBody GoodsObject goodsObject) {
//       return goodsService.save(goodsObject);
//    }

    @PostMapping("make/inventory")
    public GoodsObject makeInventory(@RequestBody GoodsObject goodsObject) {
        return new GoodsObject(goodsService.saveGoodsObject(goodsObject));
    }

    @PostMapping("make/inventory/one")
    public Good makeInventoryOne(@RequestBody Good good) {
        goodsService.save(good);
        return good;
    }

    @PostMapping("/supply/goods")
    public GoodsObject supplyGoods(@RequestBody GoodsObject goodsObject) {
        return new GoodsObject(goodsService.supplyGoods(goodsObject));
    }

    @PostMapping("/make/purchase")
    public GoodsObject makePurchase(@RequestBody GoodsObject goodsObject) {
        return new GoodsObject(goodsService.makePurchase(goodsObject));
    }

////    пробный метод
//    @GetMapping("/period")
//    public List<Good> getGoodsByPeriod(@RequestParam("dateFrom") Date dateFrom) {
//        return goodsService.findAll();
//    }
}
