package com.yuan.cxf.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.cxf.annotation.AutoPublishWS;
import com.yuan.cxf.dao.AdministratorMapper;
import com.yuan.cxf.dao.CustomerMapper;
import com.yuan.cxf.pojo.Administrator;
import com.yuan.cxf.pojo.Customer;
import com.yuan.cxf.service.AdministratorService;
import com.yuan.cxf.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

//客户添加以及查询服务
@AutoPublishWS(publishPath = "customer")
@Service
@WebService
public class CustomerServiceImpl implements CustomerService {

    @Resource
    CustomerMapper customerMapper;

    @WebMethod
    @WebResult(name = "customerInfo")
    @Override
    public String verifyCustomer(String email , String password) {

        System.out.println("===========");
        System.out.println("customer "+email+"   " + password);
        System.out.println("===========");

        //构造查询器
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        queryWrapper.eq("password",password);

        //获取管理员的信息
        Customer customer = customerMapper.selectOne(queryWrapper);

        System.out.println("===========");
        System.out.println(customer);
        System.out.println("===========");

        if (customer != null){
            return "true";
        }else{
            return "false";
        }

    }

    //
    @WebMethod
    @WebResult(name = "customerInfo")
    @Override
    public String getCustomer(String email , String password) {

        System.out.println("===========");
        System.out.println("customer "+email+"   " + password);
        System.out.println("===========");

        //构造查询器
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",email);
        queryWrapper.eq("password",password);

        //获取管理员的信息
        Customer customer = customerMapper.selectOne(queryWrapper);
        System.out.println("===========");
        System.out.println(customer);
        System.out.println("===========");

        //将获取到的customer转换成json字符串
        String json = JSON.toJSONString(customer);
        System.out.println("===========");
        System.out.println("json  "+ json);
        System.out.println("===========");


        return json;
    }

    @WebMethod
    @WebResult(name = "addCustomer")
    @Override
    public String addCustomer(String customerjson) {

        //将获取到的JSON格式的Complaint转换成相应对象
        Customer customer = JSON.parseObject(customerjson,Customer.class);

        //将获取的更新到数据库
        customerMapper.insert(customer);

        return "true";
    }

}

