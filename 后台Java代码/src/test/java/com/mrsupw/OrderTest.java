package com.mrsupw;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsupw.entity.Jelly;
import com.mrsupw.entity.MilkDrink;
import com.mrsupw.entity.SaleItem;
import com.mrsupw.mapper.JellyMapper;
import com.mrsupw.mapper.MilkDrinkMapper;
import com.mrsupw.mapper.SaleItemMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderTest {
    @Autowired
    private SaleItemMapper saleItemMapper;
    @Autowired
    private JellyMapper jellyMapper;
    @Autowired
    private MilkDrinkMapper milkDrinkMapper;

    @Test
    public void addFakeSaleItems(){
        Page<Jelly> jellyPage = new Page<>(1,10);
        jellyMapper.selectPage(jellyPage,null);
        List<Jelly> jellies = jellyPage.getRecords();
        List<SaleItem> saleItems = new ArrayList<>();
        jellies.forEach(jelly -> {
            SaleItem saleItem = new SaleItem();
            saleItem.setCode(jelly.getCode());
            saleItem.setDescription(jelly.getDescription());
            saleItem.setPrice(jelly.getPrice());
            saleItem.setProductionDate(jelly.getProductionDate());
            saleItem.setShelfLife(jelly.getShelfLife());
            saleItem.setQuantity(jelly.getQuantity());
            saleItems.add(saleItem);
        });
        saleItems.forEach(saleItem -> {
            saleItemMapper.insert(saleItem);
        });

        Page<MilkDrink> milkDrinkPage = new Page<>(1,10);
        milkDrinkMapper.selectPage(milkDrinkPage,null);
        List<MilkDrink> milkDrinks = milkDrinkPage.getRecords();
        List<SaleItem> saleItems1 = new ArrayList<>();
        milkDrinks.forEach(milkDrink -> {
            SaleItem saleItem = new SaleItem();
            saleItem.setCode(milkDrink.getCode());
            saleItem.setDescription(milkDrink.getDescription());
            saleItem.setPrice(milkDrink.getPrice());
            saleItem.setProductionDate(milkDrink.getProductionDate());
            saleItem.setShelfLife(milkDrink.getShelfLife());
            saleItem.setQuantity(milkDrink.getQuantity());
            saleItems1.add(saleItem);
        });
        saleItems1.forEach(saleItem -> {
            saleItemMapper.insert(saleItem);
        });
    }
}
