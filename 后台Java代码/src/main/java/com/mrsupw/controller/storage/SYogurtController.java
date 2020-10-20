package com.mrsupw.controller.storage;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsupw.entity.Yogurt;
import com.mrsupw.mapper.YogurtMapper;
import com.mrsupw.param.storage.yogurt.*;
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
public class SYogurtController {

    @Autowired
    private YogurtMapper yogurtMapper;

    @CrossOrigin
    @RequestMapping(value = "/getStorage/yogurt", method = RequestMethod.POST)
    public Map<String,Object> getAllStorage(@RequestBody GetStorageParam getStorageParam){
        Map<String,Object>back_data = new HashMap<>();
        String msg= "";
        int code = 0;
        int pageSize = getStorageParam.getPageSize();
        int currentPageIndex = getStorageParam.getCurrentPageIndex();
        System.out.println(pageSize + " " + currentPageIndex);
        Page<Yogurt> yogurtPage = new Page<>(currentPageIndex,pageSize);
        QueryWrapper<Yogurt> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        try {
            yogurtMapper.selectPage(yogurtPage, queryWrapper);
            msg = "Get Success!";
        }catch (Exception e){
            e.printStackTrace();
            msg ="Get Error!";
            code = 1;
        }
        back_data.put("code",code);
        back_data.put("msg",msg);
        back_data.put("YogurtList",yogurtPage.getRecords());
        back_data.put("total",yogurtPage.getTotal());
        return back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "/editStorage/yogurt",method = RequestMethod.POST)
    public Map<String,Object> editStorage(@RequestBody EditStorageParam editStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        System.out.println(editStorageParam.getId());
        int code = 0;
        String msg = "";
        try {
            Yogurt yogurt = yogurtMapper.selectById(editStorageParam.getId());
            yogurt.setCode(editStorageParam.getCode());
            yogurt.setDescription(editStorageParam.getDescription());
            yogurt.setPrice(editStorageParam.getPrice());
            yogurt.setProductionDate(editStorageParam.getProductionDate());
            yogurt.setShelfLife(editStorageParam.getShelfLife());
            yogurt.setType(editStorageParam.getType());
            yogurt.setDiluteConcentration(editStorageParam.getDiluteConcentration());
            yogurt.setQuantity(editStorageParam.getQuantity());
            yogurtMapper.updateById(yogurt);
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
    @RequestMapping(value = "/deleteStorage/yogurt",method = RequestMethod.POST)
    public Map<String,Object> deleteStorage(@RequestBody DeleteStorageParam deleteStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        int code = 0;
        String msg = "";
        try {
            yogurtMapper.deleteById(deleteStorageParam.getId());
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
    @RequestMapping(value = "/searchStorage/yogurt",method = RequestMethod.POST)
    public Map<String,Object> searchStorage(@RequestBody SearchStorageParam searchStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String searchContent = searchStorageParam.getSearchContent();
        int currentPageIndex = searchStorageParam.getCurrentPageIndex();
        int pageSize = searchStorageParam.getPageSize();
        QueryWrapper<Yogurt> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        queryWrapper.like("code",searchContent).eq("deleted",0).or()
                .like("price",searchContent).eq("deleted",0).or()
                .like("description",searchContent).eq("deleted",0).or()
                .like("shelf_life",searchContent).eq("deleted",0).or()
                .like("type",searchContent).eq("deleted",0).or()
                .like("dilute_concentration",searchContent).eq("deleted",0);
        Page<Yogurt> page = new Page<>(currentPageIndex,pageSize);
        try {
            yogurtMapper.selectPage(page,queryWrapper);
            msg = "Search Successfully!";
        }catch (Exception e){
            e.printStackTrace();
            msg = "Search Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        back_data.put("total",page.getTotal());
        back_data.put("YogurtList",page.getRecords());
        return  back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "addNewStorage/yogurt", method = RequestMethod.POST)
    public Map<String,Object> addNewStorage(@RequestBody AddNewStorageParam addNewStorageParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        Yogurt yogurt = new Yogurt();
        yogurt.setCode(addNewStorageParam.getCode());
        yogurt.setDescription(addNewStorageParam.getDescription());
        yogurt.setPrice(addNewStorageParam.getPrice());
        yogurt.setProductionDate(addNewStorageParam.getProductionDate());
        yogurt.setShelfLife(addNewStorageParam.getShelfLife());
        yogurt.setType(addNewStorageParam.getType());
        yogurt.setDiluteConcentration(addNewStorageParam.getDiluteConcentration());
        yogurt.setQuantity(addNewStorageParam.getQuantity());
        try {
            yogurtMapper.insert(yogurt);
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

