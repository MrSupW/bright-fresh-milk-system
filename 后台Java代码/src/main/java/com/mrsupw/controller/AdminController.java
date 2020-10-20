package com.mrsupw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrsupw.mapper.AdminMapper;
import com.mrsupw.param.admin.AdminLoginParam;
import com.mrsupw.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;

    private String generateToken(Long id,String username){
        long currentDate = new Date().getTime();
        return id.toString() + username + currentDate;
    }

    @CrossOrigin
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody AdminLoginParam adminLoginParam) {
        Map<String,Object> backData = new HashMap<>();
        String username = adminLoginParam.getUsername();
        String password = adminLoginParam.getPassword();
        System.out.println(username+ "  "+ password);
        String msg = "";
        int code = 0;
        String token = "";
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("username", username);
        List<Admin> admins = adminMapper.selectList(adminQueryWrapper);
        if(admins!=null && admins.size()>=1){
            adminQueryWrapper.eq("password",password);
            List<Admin> admins1 = adminMapper.selectList(adminQueryWrapper);
            if(admins1 != null && admins1.size() >= 1){
                msg = "Login Success!";
                token = generateToken(admins1.get(0).getId(),admins1.get(0).getUsername());
            }
            else{
                msg = "Wrong Password!";
                code  = -1;
            }
        }else{
            msg = "User is not existed!";
            code = 1;
        }
        backData.put("msg",msg);
        backData.put("code",code);
        backData.put("token",token);
        return backData;
    }
}
