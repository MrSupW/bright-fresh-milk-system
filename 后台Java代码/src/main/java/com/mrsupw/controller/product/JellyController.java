package com.mrsupw.controller.product;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsupw.entity.Jelly;
import com.mrsupw.entity.OrderProductRef;
import com.mrsupw.entity.SaleItem;
import com.mrsupw.mapper.JellyMapper;
import com.mrsupw.mapper.OrderProductRefMapper;
import com.mrsupw.mapper.SaleItemMapper;
import com.mrsupw.param.product.jelly.*;
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
public class JellyController {
    @Autowired
    private JellyMapper jellyMapper;

    @Autowired
    private SaleItemMapper saleItemMapper;

    @Autowired
    private OrderProductRefMapper orderProductRefMapper;

    @CrossOrigin
    @RequestMapping(value = "/getProducts/jelly", method = RequestMethod.POST)
    public Map<String,Object> getAllProducts(@RequestBody GetProductsParam getProductsParam){
        Map<String,Object>back_data = new HashMap<>();
        String msg= "";
        int code = 0;
        int pageSize = getProductsParam.getPageSize();
        int currentPageIndex = getProductsParam.getCurrentPageIndex();
        System.out.println(pageSize + " " + currentPageIndex);
        Page<Jelly> jellyPage = new Page<>(currentPageIndex,pageSize);
        QueryWrapper<Jelly> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        try {
            jellyMapper.selectPage(jellyPage, queryWrapper);
            msg = "Get Success!";
        }catch (Exception e){
            e.printStackTrace();
            msg ="Get Error!";
            code = 1;
        }
        back_data.put("code",code);
        back_data.put("msg",msg);
        back_data.put("jellyList",jellyPage.getRecords());
        back_data.put("total",jellyPage.getTotal());
        return back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "/editProduct/jelly",method = RequestMethod.POST)
    public Map<String,Object> editProduct(@RequestBody EditProductParam editProductParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        System.out.println(editProductParam.getId());
        int code = 0;
        String msg = "";
        try {
            Jelly jelly = jellyMapper.selectById(editProductParam.getId());
            jelly.setCode(editProductParam.getCode());
            jelly.setDescription(editProductParam.getDescription());
            jelly.setPrice(editProductParam.getPrice());
            jelly.setProductionDate(editProductParam.getProductionDate());
            jelly.setShelfLife(editProductParam.getShelfLife());
            jelly.setFlavor(editProductParam.getFlavor());
            jellyMapper.updateById(jelly);
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
    @RequestMapping(value = "/deleteProduct/jelly",method = RequestMethod.POST)
    public Map<String,Object> deleteProduct(@RequestBody DeleteProductParam deleteProductParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        int code = 0;
        String msg = "";
        try {
            jellyMapper.deleteById(deleteProductParam.getId());
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
    @RequestMapping(value = "/searchProducts/jelly",method = RequestMethod.POST)
    public Map<String,Object> searchProduct(@RequestBody SearchProductsParam searchProductsParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String searchContent = searchProductsParam.getSearchContent();
        int currentPageIndex = searchProductsParam.getCurrentPageIndex();
        int pageSize = searchProductsParam.getPageSize();
        QueryWrapper<Jelly> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        queryWrapper.like("code",searchContent).eq("deleted",0).or()
                .like("price",searchContent).eq("deleted",0).or()
                .like("description",searchContent).eq("deleted",0).or()
                .like("shelf_life",searchContent).eq("deleted",0).or()
                .like("flavor",searchContent).eq("deleted",0).or();
        Page<Jelly> page = new Page<>(currentPageIndex,pageSize);
        try {
            jellyMapper.selectPage(page,queryWrapper);
            msg = "Search Successfully!";
        }catch (Exception e){
            e.printStackTrace();
            msg = "Search Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        back_data.put("total",page.getTotal());
        back_data.put("jellyList",page.getRecords());
        return  back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "addNewProduct/jelly", method = RequestMethod.POST)
    public Map<String,Object> addNewProduct(@RequestBody AddNewProductParam addNewProductParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        Jelly jelly = new Jelly();
        jelly.setCode(addNewProductParam.getCode());
        jelly.setDescription(addNewProductParam.getDescription());
        jelly.setPrice(addNewProductParam.getPrice());
        jelly.setProductionDate(addNewProductParam.getProductionDate());
        jelly.setShelfLife(addNewProductParam.getShelfLife());
        jelly.setFlavor(addNewProductParam.getFlavor());
        try {
            jellyMapper.insert(jelly);
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

