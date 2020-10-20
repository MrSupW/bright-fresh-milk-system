package com.mrsupw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrsupw.entity.Jelly;
import com.mrsupw.entity.MilkDrink;
import com.mrsupw.entity.PureMilk;
import com.mrsupw.entity.Yogurt;
import com.mrsupw.mapper.JellyMapper;
import com.mrsupw.mapper.MilkDrinkMapper;
import com.mrsupw.mapper.PureMilkMapper;
import com.mrsupw.mapper.YogurtMapper;
import com.mrsupw.param.publicfunction.JudgeCodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PublicFunctionController {

    @Autowired
    private JellyMapper jellyMapper;

    @Autowired
    private MilkDrinkMapper milkDrinkMapper;

    @Autowired
    private PureMilkMapper pureMilkMapper;

    @Autowired
    private YogurtMapper yogurtMapper;

    @CrossOrigin
    @RequestMapping(value = "/publicFunction/judgeCodeValidation", method = RequestMethod.POST)
    public Map<String,Object> judgeCodeValidation(@RequestBody JudgeCodeParam judgeCodeParam){
        HashMap<String, Object> back_data = new HashMap<>();
        String msg = "";
        int code = 0;
        String nowCode = judgeCodeParam.getNowCode();
        String targetCode = judgeCodeParam.getTargetCode();
        if(targetCode.equals(nowCode)){
            msg = "Allow!!";
            back_data.put("msg",msg);
            back_data.put("code",code);
            return back_data;
        }
        QueryWrapper<Jelly> queryWrapper1 = new QueryWrapper<>();
        QueryWrapper<MilkDrink> queryWrapper2 = new QueryWrapper<>();
        QueryWrapper<PureMilk> queryWrapper3 = new QueryWrapper<>();
        QueryWrapper<Yogurt> queryWrapper4 = new QueryWrapper<>();
        queryWrapper1.eq("code",targetCode);
        queryWrapper2.eq("code",targetCode);
        queryWrapper3.eq("code",targetCode);
        queryWrapper4.eq("code",targetCode);
        List<Jelly> jellies = null;
        List<MilkDrink> milkDrinks = null;
        List<PureMilk> pureMilks = null;
        List<Yogurt> yogurts = null;
        try {
            jellies = jellyMapper.selectList(queryWrapper1);
            milkDrinks = milkDrinkMapper.selectList(queryWrapper2);
            pureMilks = pureMilkMapper.selectList(queryWrapper3);
            yogurts = yogurtMapper.selectList(queryWrapper4);
            System.out.println("-------------------------");
            System.out.println(jellies.size() +" "+ milkDrinks.size()+" "+ pureMilks.size() +" "+ yogurts.size());
            if(jellies.size() == 0  && milkDrinks.size() == 0  && pureMilks.size() == 0 && yogurts.size() == 0){
                msg = "Allow!!";
            }
            else{
                msg = "Refuse! The Code has been occupied!";
                code = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Refuse! The Database failed!";
            code = 1;
        }
        back_data.put("msg",msg);
        back_data.put("code",code);
        return back_data;
    }
}
