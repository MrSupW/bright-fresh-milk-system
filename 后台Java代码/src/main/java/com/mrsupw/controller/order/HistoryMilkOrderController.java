package com.mrsupw.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrsupw.entity.MilkOrder;
import com.mrsupw.mapper.MilkOrderMapper;
import com.mrsupw.param.order.currentorder.GetCurrentOrdersParam;
import com.mrsupw.param.order.currentorder.SearchCurrentOrdersParam;
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
 * @since 2020-10-06
 */
@RestController
public class HistoryMilkOrderController {

    @Autowired
    private MilkOrderMapper milkOrderMapper;

    @CrossOrigin
    @RequestMapping(value = "getOrders/historyOrder", method = RequestMethod.POST)
    public Map<String,Object> addNewProduct(@RequestBody GetCurrentOrdersParam getCurrentOrdersParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        int pageSize = getCurrentOrdersParam.getPageSize();
        int currentPageIndex = getCurrentOrdersParam.getCurrentPageIndex();
        Page<MilkOrder> page = new Page<>(currentPageIndex,pageSize);
        QueryWrapper<MilkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code").eq("is_history",1);
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
        back_data.put("historyOrderList",page.getRecords());
        back_data.put("total",page.getTotal());
        return back_data;
    }


    @CrossOrigin
    @RequestMapping(value = "searchOrders/historyOrder", method = RequestMethod.POST)
    public Map<String,Object> searchOrders(@RequestBody SearchCurrentOrdersParam searchCurrentOrdersParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String searchContent = searchCurrentOrdersParam.getSearchContent();
        int currentPageIndex = searchCurrentOrdersParam.getCurrentPageIndex();
        int pageSize = searchCurrentOrdersParam.getPageSize();
        QueryWrapper<MilkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        queryWrapper.like("code",searchContent).eq("deleted",0).eq("is_history",1).or()
                .like("description",searchContent).eq("deleted",0).eq("is_history",1).or()
                .like("total_price",searchContent).eq("deleted",0).eq("is_history",1);
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
        back_data.put("historyOrderList",page.getRecords());
        return back_data;
    }

}

