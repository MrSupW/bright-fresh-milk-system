package com.mrsupw.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsupw.entity.MilkOrder;
import com.mrsupw.mapper.MilkOrderMapper;
import com.mrsupw.param.order.currentorder.*;
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
 * @since 2020-10-06
 */
@RestController
public class CurrentMilkOrderController {

    @Autowired
    private MilkOrderMapper milkOrderMapper;

    @CrossOrigin
    @RequestMapping(value = "getOrders/currentOrder", method = RequestMethod.POST)
    public Map<String,Object> addNewProduct(@RequestBody GetCurrentOrdersParam getCurrentOrdersParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        int pageSize = getCurrentOrdersParam.getPageSize();
        int currentPageIndex = getCurrentOrdersParam.getCurrentPageIndex();
        Page<MilkOrder> page = new Page<>(currentPageIndex,pageSize);
        QueryWrapper<MilkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code").eq("is_history",0);
        try {
            milkOrderMapper.selectPage(page,queryWrapper);
            msg = "Get Success!";
        } catch (Exception e) {
            e.printStackTrace();
            code = -1;
            msg = "Get Failed!";
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        back_data.put("currentOrderList",page.getRecords());
        back_data.put("total",page.getTotal());
        return back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "editOrder/currentOrder", method = RequestMethod.POST)
    public Map<String,Object> editOrder(@RequestBody EditCurrentOrderParam editCurrentOrderParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        MilkOrder milkOrder = milkOrderMapper.selectById(editCurrentOrderParam.getId());
        milkOrder.setDescription(editCurrentOrderParam.getDescription());
        milkOrder.setCode(editCurrentOrderParam.getCode());
        try {
            milkOrderMapper.updateById(milkOrder);
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
    @RequestMapping(value = "addNewOrder/currentOrder", method = RequestMethod.POST)
    public Map<String,Object> addNewOrder(@RequestBody AddNewCurrentOrderParam addNewCurrentOrderParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        MilkOrder milkOrder = new MilkOrder();
        milkOrder.setCode(addNewCurrentOrderParam.getCode());
        milkOrder.setDescription(addNewCurrentOrderParam.getDescription());
        try {
            milkOrderMapper.insert(milkOrder);
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

    @CrossOrigin
    @RequestMapping(value = "deleteOrder/currentOrder", method = RequestMethod.POST)
    public Map<String,Object> deleteOrder(@RequestBody DeleteCurrentOrderParam deleteCurrentOrderParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        try {
            milkOrderMapper.deleteById(deleteCurrentOrderParam.getId());
            msg = "Delete Successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Delete Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "searchOrders/currentOrder", method = RequestMethod.POST)
    public Map<String,Object> searchOrders(@RequestBody SearchCurrentOrdersParam searchCurrentOrdersParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String searchContent = searchCurrentOrdersParam.getSearchContent();
        int currentPageIndex = searchCurrentOrdersParam.getCurrentPageIndex();
        int pageSize = searchCurrentOrdersParam.getPageSize();
        QueryWrapper<MilkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        queryWrapper.like("code",searchContent).eq("deleted",0).eq("is_history",0).or()
                .like("description",searchContent).eq("deleted",0).eq("is_history",0).or()
                .like("total_price",searchContent).eq("deleted",0).eq("is_history",0);
        Page<MilkOrder> page = new Page<>(currentPageIndex,pageSize);
        try {
            milkOrderMapper.selectPage(page,queryWrapper);
            msg = "Search Successfully!";
        }catch (Exception e){
            e.printStackTrace();
            msg = "Search Failed!";
            code = -1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        back_data.put("total",page.getTotal());
        back_data.put("currentOrderList",page.getRecords());
        return back_data;
    }


//    judgeCodeValidation
    @CrossOrigin
    @RequestMapping(value = "judgeCodeValidation/currentOrder", method = RequestMethod.POST)
    public Map<String,Object> editOrder(@RequestBody JudgeCodeValidationParam judgeCodeValidationParam){
        Map<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String nowCode = judgeCodeValidationParam.getNowCode();
        String targetCode = judgeCodeValidationParam.getTargetCode();
        if(targetCode.equals(nowCode)){
            msg = "Allowed!";
            back_data.put("code",code);
            back_data.put("msg",msg);
            return back_data;
        }
        QueryWrapper<MilkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",targetCode);
        List<MilkOrder> milkOrders = milkOrderMapper.selectList(queryWrapper);
        if(milkOrders.size() > 0){
            code = -1;
            msg = "Refuse! The Code has been occupied!";
        }
        back_data.put("code",code);
        back_data.put("msg",msg);
        return  back_data;
    }

    @CrossOrigin
    @RequestMapping(value = "submitOrder/currentOrder", method = RequestMethod.POST)
    public Map<String,Object> submitOrder(@RequestBody SubmitOrderParam submitOrderParam) {
        Map<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String orderId = submitOrderParam.getOrderId();
        System.out.println("orderId" + orderId);
        try {
            MilkOrder milkOrder = milkOrderMapper.selectById(orderId);
            milkOrder.setIsHistory(1);
            milkOrderMapper.updateById(milkOrder);
            msg = "Submit Successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Submit Failed!";
            code = -1;
        }
        back_data.put("code", code);
        back_data.put("msg", msg);
        return back_data;
    }

}

