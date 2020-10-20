package com.mrsupw.controller.product;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsupw.entity.MilkDrink;
import com.mrsupw.entity.OrderProductRef;
import com.mrsupw.entity.SaleItem;
import com.mrsupw.mapper.MilkDrinkMapper;
import com.mrsupw.mapper.OrderProductRefMapper;
import com.mrsupw.mapper.SaleItemMapper;
import com.mrsupw.param.product.milkdrink.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-09-28
 */
@RestController
public class MilkDrinkController {

    @Autowired
    private MilkDrinkMapper milkDrinkMapper;

    @Autowired
    private SaleItemMapper saleItemMapper;

    @Autowired
    private OrderProductRefMapper orderProductRefMapper;

    @CrossOrigin
    @RequestMapping(value = "/getProducts/milkDrink", method = RequestMethod.POST)
    public Map<String,Object> getAllProducts(@RequestBody GetProductsParam getProductsParam){
        Map<String,Object>back_data = new HashMap<>();
        String msg= "";
        int code = 0;
        int pageSize = getProductsParam.getPageSize();
        int currentPageIndex = getProductsParam.getCurrentPageIndex();
        System.out.println(pageSize + " " + currentPageIndex);
        Page<MilkDrink> milkDrinkPage = new Page<>(currentPageIndex,pageSize);
        QueryWrapper<MilkDrink> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        try {
            milkDrinkMapper.selectPage(milkDrinkPage, queryWrapper);
            msg = "Get Success!";
        }catch (Exception e){
            e.printStackTrace();
            msg ="Get Error!";
            code = 1;
        }
        back_data.put("code",code);
        back_data.put("msg",msg);
        back_data.put("milkDrinkList",milkDrinkPage.getRecords());
        back_data.put("total",milkDrinkPage.getTotal());
        return back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "/editProduct/milkDrink",method = RequestMethod.POST)
    public Map<String,Object> editProduct(@RequestBody EditProductParam editProductParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        System.out.println(editProductParam.getId());
        int code = 0;
        String msg = "";
        try {
            MilkDrink milkDrink = milkDrinkMapper.selectById(editProductParam.getId());
            milkDrink.setCode(editProductParam.getCode());
            milkDrink.setDescription(editProductParam.getDescription());
            milkDrink.setPrice(editProductParam.getPrice());
            milkDrink.setProductionDate(editProductParam.getProductionDate());
            milkDrink.setShelfLife(editProductParam.getShelfLife());
            milkDrink.setFlavor(editProductParam.getFlavor());
            milkDrink.setSugar(editProductParam.getSugar());
            milkDrinkMapper.updateById(milkDrink);
            msg = "Edit Success!";
        }catch (Exception e) {
            e.printStackTrace();
            msg = "Edit Error!";
            code = -1;
        }
        // 查询saleItem中是否有这个产品 如果有 则同步进行更新
        try{
            QueryWrapper<SaleItem> saleItemQueryWrapper = new QueryWrapper<>();
            saleItemQueryWrapper.eq("code",editProductParam.getCode());
            List<SaleItem> saleItems = saleItemMapper.selectList(saleItemQueryWrapper);
            if (saleItems.size() > 0 ){
                // 说明查询到了 需要更新
                SaleItem saleItem = new SaleItem();
                saleItem.setCode(editProductParam.getCode());
                saleItem.setDescription(editProductParam.getDescription());
                saleItem.setPrice(editProductParam.getPrice());
                saleItem.setProductionDate(editProductParam.getProductionDate());
                saleItem.setShelfLife(editProductParam.getShelfLife());
                saleItemMapper.update(saleItem,saleItemQueryWrapper);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return  back_data;
    }


    @CrossOrigin
    @RequestMapping(value = "/deleteProduct/milkDrink",method = RequestMethod.POST)
    public Map<String,Object> deleteProduct(@RequestBody DeleteProductParam deleteProductParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        int code = 0;
        String msg = "";
        try {
            milkDrinkMapper.deleteById(deleteProductParam.getId());
            msg  = "Delete Successfully!";
        }catch (Exception e){
            e.printStackTrace();
            msg = "Delete Failed!";
            code= -1;
        }
        // 查询saleItem中是否有这个产品 如果有 则同步进行删除
        try{
            QueryWrapper<SaleItem> saleItemQueryWrapper = new QueryWrapper<>();
            saleItemQueryWrapper.eq("code",deleteProductParam.getCode());
            List<SaleItem> saleItems = saleItemMapper.selectList(saleItemQueryWrapper);
            System.out.println("saleItems.size():"+saleItems.size());
            System.out.println("deleteProductParam.getCode():"+ deleteProductParam.getCode());
            if (saleItems.size() > 0 ){
                // 说明查询到了 需要删除
                saleItemMapper.delete(saleItemQueryWrapper);
                //  同时删除关系表中的数据
                saleItems.forEach(saleItem -> {
                    QueryWrapper<OrderProductRef> orderProductRefQueryWrapper = new QueryWrapper<>();
                    orderProductRefQueryWrapper.eq("sale_item_id",saleItem.getId());
                    orderProductRefMapper.delete(orderProductRefQueryWrapper);
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return  back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "/searchProducts/milkDrink",method = RequestMethod.POST)
    public Map<String,Object> searchProduct(@RequestBody SearchProductsParam searchProductsParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String searchContent = searchProductsParam.getSearchContent();
        int currentPageIndex = searchProductsParam.getCurrentPageIndex();
        int pageSize = searchProductsParam.getPageSize();
        QueryWrapper<MilkDrink> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        queryWrapper.like("code",searchContent).eq("deleted",0).or()
                .like("price",searchContent).eq("deleted",0).or()
                .like("description",searchContent).eq("deleted",0).or()
                .like("shelf_life",searchContent).eq("deleted",0).or()
                .like("flavor",searchContent).eq("deleted",0).or()
                .like("sugar",searchContent).eq("deleted",0);
        Page<MilkDrink> page = new Page<>(currentPageIndex,pageSize);
        try {
            milkDrinkMapper.selectPage(page,queryWrapper);
            msg = "Search Successfully!";
        }catch (Exception e){
            e.printStackTrace();
            msg = "Search Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        back_data.put("total",page.getTotal());
        back_data.put("milkDrinkList",page.getRecords());
        return  back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "addNewProduct/milkDrink", method = RequestMethod.POST)
    public Map<String,Object> addNewProduct(@RequestBody AddNewProductParam addNewProductParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        MilkDrink milkDrink = new MilkDrink();
        milkDrink.setCode(addNewProductParam.getCode());
        milkDrink.setDescription(addNewProductParam.getDescription());
        milkDrink.setPrice(addNewProductParam.getPrice());
        milkDrink.setProductionDate(addNewProductParam.getProductionDate());
        milkDrink.setShelfLife(addNewProductParam.getShelfLife());
        milkDrink.setFlavor(addNewProductParam.getFlavor());
        milkDrink.setSugar(addNewProductParam.getSugar());
        try {
            milkDrinkMapper.insert(milkDrink);
            msg = "Add Successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Add Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return back_data;
    }
}

