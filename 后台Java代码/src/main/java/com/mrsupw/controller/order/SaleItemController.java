package com.mrsupw.controller.order;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrsupw.entity.*;
import com.mrsupw.mapper.*;
import com.mrsupw.param.currentorderdetail.AddNewSaleItemParam;
import com.mrsupw.param.currentorderdetail.DeleteSaleItemParam;
import com.mrsupw.param.currentorderdetail.EditSaleItemParam;
import com.mrsupw.param.currentorderdetail.GetAllSaleItemsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-10-06
 */
@RestController
public class SaleItemController {
    @Autowired
    private SaleItemMapper saleItemMapper;

    @Autowired
    private OrderProductRefMapper orderProductRefMapper;

    @Autowired
    private JellyMapper jellyMapper;

    @Autowired
    private MilkDrinkMapper milkDrinkMapper;

    @Autowired
    private PureMilkMapper pureMilkMapper;

    @Autowired
    private YogurtMapper yogurtMapper;

    @CrossOrigin
    @RequestMapping(value = "getSaleItems/saleItem", method = RequestMethod.POST)
    public Map<String,Object> getAllSaleItems(@RequestBody GetAllSaleItemsParam getAllSaleItemsParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        System.out.println("getAllSaleItemsParam.getOrderId()"+getAllSaleItemsParam.getOrderId());
        String orderId = getAllSaleItemsParam.getOrderId();
        QueryWrapper<OrderProductRef> orderProductRefQueryWrapper = new QueryWrapper<>();
        orderProductRefQueryWrapper.eq("order_id",orderId);

        List<SaleItem> saleItemList = new ArrayList<>();
        try {
            List<OrderProductRef> orderProductRefs = orderProductRefMapper.selectList(orderProductRefQueryWrapper);
            if (orderProductRefs.size() > 0){
                //  通过关系表检索出的结果 获得所有saleItem的id
                List<String> saleItemIds = new ArrayList<>();
                orderProductRefs.forEach(orderProductRef -> {
                    saleItemIds.add(orderProductRef.getSaleItemId());
                });
                // 通过saleItemIds返回SaleItems
                saleItemList = saleItemMapper.selectBatchIds(saleItemIds);
                msg = "Get Successfully!";
            }else{
                msg = "No saleItem!";
                code = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Get Failed!";
            code = -2;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        back_data.put("total",saleItemList.size());
        back_data.put("saleItemList",saleItemList);
        return back_data;
    }


    @CrossOrigin
    @RequestMapping(value = "addNewSaleItem/saleItem" , method = RequestMethod.POST)
    public  Map<String,Object> addNewSaleItem(@RequestBody AddNewSaleItemParam addNewSaleItemParam ){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String orderId = addNewSaleItemParam.getOrderId();
        System.out.println("orderId: " + orderId);
        String product_code = addNewSaleItemParam.getCode();
        Integer quantity = addNewSaleItemParam.getQuantity();
        // 通过产品的code进行全局查找
        QueryWrapper<Jelly> jellyQueryWrapper = new QueryWrapper<>();
        QueryWrapper<MilkDrink> milkDrinkQueryWrapper = new QueryWrapper<>();
        QueryWrapper<PureMilk> pureMilkQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Yogurt> yogurtQueryWrapper = new QueryWrapper<>();
        jellyQueryWrapper.eq("code", product_code);
        milkDrinkQueryWrapper.eq("code", product_code);
        pureMilkQueryWrapper.eq("code", product_code);
        yogurtQueryWrapper.eq("code", product_code);
        List<Jelly> jellies = jellyMapper.selectList(jellyQueryWrapper);
        if(jellies.size() >= 1){
            Jelly jelly = jellies.get(0);
            SaleItem saleItem = new SaleItem();
            saleItem.setCode(jelly.getCode());
            saleItem.setDescription(jelly.getDescription());
            saleItem.setPrice(jelly.getPrice());
            saleItem.setProductionDate(jelly.getProductionDate());
            saleItem.setShelfLife(jelly.getShelfLife());
            saleItem.setQuantity(addNewSaleItemParam.getQuantity());
            saleItemMapper.insert(saleItem);
            OrderProductRef orderProductRef = new OrderProductRef();
            orderProductRef.setOrderId(orderId);
            orderProductRef.setSaleItemId(saleItem.getId());
            orderProductRefMapper.insert(orderProductRef);
            msg = "Add Successfully!";
        }
        List<MilkDrink> milkDrinks = milkDrinkMapper.selectList(milkDrinkQueryWrapper);
        if(milkDrinks.size() >= 1){
            MilkDrink milkDrink = milkDrinks.get(0);
            SaleItem saleItem = new SaleItem();
            saleItem.setCode(milkDrink.getCode());
            saleItem.setDescription(milkDrink.getDescription());
            saleItem.setPrice(milkDrink.getPrice());
            saleItem.setProductionDate(milkDrink.getProductionDate());
            saleItem.setShelfLife(milkDrink.getShelfLife());
            saleItem.setQuantity(addNewSaleItemParam.getQuantity());
            saleItemMapper.insert(saleItem);
            OrderProductRef orderProductRef = new OrderProductRef();
            orderProductRef.setOrderId(orderId);
            orderProductRef.setSaleItemId(saleItem.getId());
            orderProductRefMapper.insert(orderProductRef);
            msg = "Add Successfully!";
        }
        List<PureMilk> pureMilks = pureMilkMapper.selectList(pureMilkQueryWrapper);
        if(pureMilks.size() >= 1){
            PureMilk pureMilk = pureMilks.get(0);
            SaleItem saleItem = new SaleItem();
            saleItem.setCode(pureMilk.getCode());
            saleItem.setDescription(pureMilk.getDescription());
            saleItem.setPrice(pureMilk.getPrice());
            saleItem.setProductionDate(pureMilk.getProductionDate());
            saleItem.setShelfLife(pureMilk.getShelfLife());
            saleItem.setQuantity(addNewSaleItemParam.getQuantity());
            saleItemMapper.insert(saleItem);
            OrderProductRef orderProductRef = new OrderProductRef();
            orderProductRef.setOrderId(orderId);
            orderProductRef.setSaleItemId(saleItem.getId());
            orderProductRefMapper.insert(orderProductRef);
            msg = "Add Successfully!";
        }
        List<Yogurt> yogurts = yogurtMapper.selectList(yogurtQueryWrapper);
        if(yogurts.size() >= 1){
            Yogurt yogurt = yogurts.get(0);
            SaleItem saleItem = new SaleItem();
            saleItem.setCode(yogurt.getCode());
            saleItem.setDescription(yogurt.getDescription());
            saleItem.setPrice(yogurt.getPrice());
            saleItem.setProductionDate(yogurt.getProductionDate());
            saleItem.setShelfLife(yogurt.getShelfLife());
            saleItem.setQuantity(addNewSaleItemParam.getQuantity());
            saleItemMapper.insert(saleItem);
            OrderProductRef orderProductRef = new OrderProductRef();
            orderProductRef.setOrderId(orderId);
            orderProductRef.setSaleItemId(saleItem.getId());
            orderProductRefMapper.insert(orderProductRef);
            msg = "Add Successfully!";
        }
        if (msg.equals("")) {
            msg = "Add Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "editSaleItem/saleItem" , method = RequestMethod.POST)
    public  Map<String,Object> editSaleItem(@RequestBody EditSaleItemParam editSaleItemParam ){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String saleItemId = editSaleItemParam.getSaleItemId();
        Integer quantity = editSaleItemParam.getQuantity();
        SaleItem saleItem = saleItemMapper.selectById(saleItemId);
        saleItem.setQuantity(quantity);
        try {
            saleItemMapper.updateById(saleItem);
            msg = "Edit Successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Edit Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "deleteSaleItem/saleItem" , method = RequestMethod.POST)
    public  Map<String,Object> deleteSaleItem(@RequestBody DeleteSaleItemParam deleteSaleItemParam ){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String orderId = deleteSaleItemParam.getOrderId();
        String saleItemId = deleteSaleItemParam.getSaleItemId();
        System.out.println(orderId + " " + saleItemId);
        try {
            saleItemMapper.deleteById(saleItemId);
            QueryWrapper<OrderProductRef> orderProductRefQueryWrapper = new QueryWrapper<>();
            orderProductRefQueryWrapper.eq("order_id",orderId).eq("sale_item_id",saleItemId);
            orderProductRefMapper.delete(orderProductRefQueryWrapper);
            msg = "Delete Successfully!";
        }catch(Exception e){
            e.printStackTrace();
            msg = "Delete Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return back_data;
    }
}

