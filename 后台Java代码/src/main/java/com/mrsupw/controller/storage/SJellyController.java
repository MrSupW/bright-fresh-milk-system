package com.mrsupw.controller.storage;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsupw.entity.Jelly;
import com.mrsupw.mapper.JellyMapper;
import com.mrsupw.param.storage.jelly.*;
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
public class SJellyController {
    @Autowired
    private JellyMapper jellyMapper;

    @CrossOrigin
    @RequestMapping(value = "/getStorage/jelly", method = RequestMethod.POST)
    public Map<String,Object> getAllStorage(@RequestBody GetStorageParam getStorageParam){
        Map<String,Object>back_data = new HashMap<>();
        String msg= "";
        int code = 0;
        int pageSize = getStorageParam.getPageSize();
        int currentPageIndex = getStorageParam.getCurrentPageIndex();
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
    @RequestMapping(value = "/editStorage/jelly",method = RequestMethod.POST)
    public Map<String,Object> editStorage(@RequestBody EditStorageParam editStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        System.out.println(editStorageParam.getId());
        int code = 0;
        String msg = "";
        try {
            Jelly jelly = jellyMapper.selectById(editStorageParam.getId());
            jelly.setCode(editStorageParam.getCode());
            jelly.setDescription(editStorageParam.getDescription());
            jelly.setPrice(editStorageParam.getPrice());
            jelly.setProductionDate(editStorageParam.getProductionDate());
            jelly.setShelfLife(editStorageParam.getShelfLife());
            jelly.setFlavor(editStorageParam.getFlavor());
            jelly.setQuantity(editStorageParam.getQuantity());
            jellyMapper.updateById(jelly);
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
    @RequestMapping(value = "/deleteStorage/jelly",method = RequestMethod.POST)
    public Map<String,Object> deleteStorage(@RequestBody DeleteStorageParam deleteStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        int code = 0;
        String msg = "";
        try {
            jellyMapper.deleteById(deleteStorageParam.getId());
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
    @RequestMapping(value = "/searchStorage/jelly",method = RequestMethod.POST)
    public Map<String,Object> searchStorage(@RequestBody SearchStorageParam searchStorageParam) {
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String searchContent = searchStorageParam.getSearchContent();
        int currentPageIndex = searchStorageParam.getCurrentPageIndex();
        int pageSize = searchStorageParam.getPageSize();
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
    @RequestMapping(value = "addNewStorage/jelly", method = RequestMethod.POST)
    public Map<String,Object> addNewStorage(@RequestBody AddNewStorageParam addNewStorageParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        Jelly jelly = new Jelly();
        jelly.setCode(addNewStorageParam.getCode());
        jelly.setDescription(addNewStorageParam.getDescription());
        jelly.setPrice(addNewStorageParam.getPrice());
        jelly.setProductionDate(addNewStorageParam.getProductionDate());
        jelly.setShelfLife(addNewStorageParam.getShelfLife());
        jelly.setFlavor(addNewStorageParam.getFlavor());
        jelly.setQuantity(addNewStorageParam.getQuantity());
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

