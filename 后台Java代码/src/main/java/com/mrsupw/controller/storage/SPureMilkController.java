package com.mrsupw.controller.storage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsupw.entity.PureMilk;
import com.mrsupw.mapper.PureMilkMapper;
import com.mrsupw.param.storage.puremilk.*;
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
public class SPureMilkController {

    @Autowired
    private PureMilkMapper pureMilkMapper;

    @CrossOrigin
    @RequestMapping(value = "/getStorage/pureMilk", method = RequestMethod.POST)
    public Map<String,Object> getAllStorage(@RequestBody GetStorageParam getStorageParam){
        Map<String,Object>back_data = new HashMap<>();
        String msg= "";
        int code = 0;
        int pageSize = getStorageParam.getPageSize();
        int currentPageIndex = getStorageParam.getCurrentPageIndex();
        System.out.println(pageSize + " " + currentPageIndex);
        Page<PureMilk> pureMilkPage = new Page<>(currentPageIndex,pageSize);
        QueryWrapper<PureMilk> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        try {
            pureMilkMapper.selectPage(pureMilkPage, queryWrapper);
            msg = "Get Success!";
        }catch (Exception e){
            e.printStackTrace();
            msg ="Get Error!";
            code = 1;
        }
        back_data.put("code",code);
        back_data.put("msg",msg);
        back_data.put("pureMilkList",pureMilkPage.getRecords());
        back_data.put("total",pureMilkPage.getTotal());
        return back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "/editStorage/pureMilk",method = RequestMethod.POST)
    public Map<String,Object> editStorage(@RequestBody EditStorageParam editStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        System.out.println(editStorageParam);
        int code = 0;
        String msg = "";
        try {
            PureMilk pureMilk = pureMilkMapper.selectById(editStorageParam.getId());
            pureMilk.setCode(editStorageParam.getCode());
            pureMilk.setDescription(editStorageParam.getDescription());
            pureMilk.setPrice(editStorageParam.getPrice());
            pureMilk.setProductionDate(editStorageParam.getProductionDate());
            pureMilk.setShelfLife(editStorageParam.getShelfLife());
            pureMilk.setCountryOfOrigin(editStorageParam.getCountryOfOrigin());
            pureMilk.setButterfat(editStorageParam.getButterfat());
            pureMilk.setProtein(editStorageParam.getProtein());
            pureMilk.setQuantity(editStorageParam.getQuantity());
            pureMilkMapper.updateById(pureMilk);
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
    @RequestMapping(value = "/deleteStorage/pureMilk",method = RequestMethod.POST)
    public Map<String,Object> deleteStorage(@RequestBody DeleteStorageParam deleteStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        int code = 0;
        String msg = "";
        try {
            pureMilkMapper.deleteById(deleteStorageParam.getId());
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
    @RequestMapping(value = "/searchStorage/pureMilk",method = RequestMethod.POST)
    public Map<String,Object> searchStorage(@RequestBody SearchStorageParam searchStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String searchContent = searchStorageParam.getSearchContent();
        int currentPageIndex = searchStorageParam.getCurrentPageIndex();
        int pageSize = searchStorageParam.getPageSize();
        QueryWrapper<PureMilk> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        queryWrapper.like("code",searchContent).eq("deleted",0).or()
                .like("price",searchContent).eq("deleted",0).or()
                .like("description",searchContent).eq("deleted",0).or()
                .like("shelf_life",searchContent).eq("deleted",0).or()
                .like("butterfat",searchContent).eq("deleted",0).or()
                .like("country_of_origin",searchContent).eq("deleted",0).or()
                .like("protein",searchContent).eq("deleted",0);
        Page<PureMilk> page = new Page<>(currentPageIndex,pageSize);
        try {
            pureMilkMapper.selectPage(page,queryWrapper);
            msg = "Search Successfully!";
        }catch (Exception e){
            e.printStackTrace();
            msg = "Search Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        back_data.put("total",page.getTotal());
        back_data.put("pureMilkList",page.getRecords());
        return  back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "addNewStorage/pureMilk", method = RequestMethod.POST)
    public Map<String,Object> addNewStorage(@RequestBody AddNewStorageParam addNewStorageParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        PureMilk pureMilk = new PureMilk();
        pureMilk.setCode(addNewStorageParam.getCode());
        pureMilk.setDescription(addNewStorageParam.getDescription());
        pureMilk.setPrice(addNewStorageParam.getPrice());
        pureMilk.setProductionDate(addNewStorageParam.getProductionDate());
        pureMilk.setShelfLife(addNewStorageParam.getShelfLife());
        pureMilk.setCountryOfOrigin(addNewStorageParam.getCountryOfOrigin());
        pureMilk.setButterfat(addNewStorageParam.getButterfat());
        pureMilk.setProtein(addNewStorageParam.getProtein());
        pureMilk.setQuantity(addNewStorageParam.getQuantity());
        try {
            pureMilkMapper.insert(pureMilk);
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