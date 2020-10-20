package com.mrsupw.controller.storage;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsupw.entity.MilkDrink;
import com.mrsupw.mapper.MilkDrinkMapper;
import com.mrsupw.param.storage.milkdrink.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
public class SMilkDrinkController {

    @Autowired
    private MilkDrinkMapper milkDrinkMapper;

    @CrossOrigin
    @RequestMapping(value = "/getStorage/milkDrink", method = RequestMethod.POST)
    public Map<String,Object> getAllStorage(@RequestBody GetStorageParam getStorageParam){
        Map<String,Object>back_data = new HashMap<>();
        String msg= "";
        int code = 0;
        int pageSize = getStorageParam.getPageSize();
        int currentPageIndex = getStorageParam.getCurrentPageIndex();
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
    @RequestMapping(value = "/editStorage/milkDrink",method = RequestMethod.POST)
    public Map<String,Object> editStorage(@RequestBody EditStorageParam editStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        System.out.println(editStorageParam.getId());
        int code = 0;
        String msg = "";
        try {
            MilkDrink milkDrink = milkDrinkMapper.selectById(editStorageParam.getId());
            milkDrink.setCode(editStorageParam.getCode());
            milkDrink.setDescription(editStorageParam.getDescription());
            milkDrink.setPrice(editStorageParam.getPrice());
            milkDrink.setProductionDate(editStorageParam.getProductionDate());
            milkDrink.setShelfLife(editStorageParam.getShelfLife());
            milkDrink.setFlavor(editStorageParam.getFlavor());
            milkDrink.setSugar(editStorageParam.getSugar());
            milkDrink.setQuantity(editStorageParam.getQuantity());
            milkDrinkMapper.updateById(milkDrink);
            msg = "Edit Success!";
        }catch (Exception e) {
            e.printStackTrace();
            msg = "Edit Error!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return  back_data;
    }


    @CrossOrigin
    @RequestMapping(value = "/deleteStorage/milkDrink",method = RequestMethod.POST)
    public Map<String,Object> deleteStorage(@RequestBody DeleteStorageParam deleteStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        int code = 0;
        String msg = "";
        try {
            milkDrinkMapper.deleteById(deleteStorageParam.getId());
            msg  = "Delete Successfully!";
        }catch (Exception e){
            e.printStackTrace();
            msg = "Delete Failed!";
            code= -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return  back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "/searchStorage/milkDrink",method = RequestMethod.POST)
    public Map<String,Object> searchStorage(@RequestBody SearchStorageParam searchStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String searchContent = searchStorageParam.getSearchContent();
        int currentPageIndex = searchStorageParam.getCurrentPageIndex();
        int pageSize = searchStorageParam.getPageSize();
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
    @RequestMapping(value = "addNewStorage/milkDrink", method = RequestMethod.POST)
    public Map<String,Object> addNewStorage(@RequestBody AddNewStorageParam addNewStorageParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        MilkDrink milkDrink = new MilkDrink();
        milkDrink.setCode(addNewStorageParam.getCode());
        milkDrink.setDescription(addNewStorageParam.getDescription());
        milkDrink.setPrice(addNewStorageParam.getPrice());
        milkDrink.setProductionDate(addNewStorageParam.getProductionDate());
        milkDrink.setShelfLife(addNewStorageParam.getShelfLife());
        milkDrink.setFlavor(addNewStorageParam.getFlavor());
        milkDrink.setSugar(addNewStorageParam.getSugar());
        milkDrink.setQuantity(addNewStorageParam.getQuantity());
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

