package com.yuan.cxf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.cxf.annotation.AutoPublishWS;
import com.yuan.cxf.dao.AdministratorMapper;
import com.yuan.cxf.dao.ComplaintMapper;
import com.yuan.cxf.pojo.Administrator;
import com.yuan.cxf.pojo.Complaint;
import com.yuan.cxf.service.AdministratorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


@AutoPublishWS(publishPath = "administrator")
@Service
@WebService
public class AdministratorServiceImpl implements AdministratorService {

    @Resource
    AdministratorMapper administratorMapper;

    @WebMethod
    @WebResult(name = "AdminstratorInfo")
    @Override
    public String verifyAdministrator(String email , String password) {

        System.out.println("===========");
        System.out.println("administrtor "+email+"   " + password);
        System.out.println("===========");


        //构造查询器
        QueryWrapper<Administrator> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        queryWrapper.eq("password",password);

        //获取管理员的信息
        Administrator administrator= administratorMapper.selectOne(queryWrapper);

        System.out.println("===========");
        System.out.println(administrator);
        System.out.println("===========");


        if (administrator != null){
            return "true";
        }else{
            return "false";
        }


    }

}
