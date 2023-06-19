package com.yuan.cxf.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.cxf.annotation.AutoPublishWS;
import com.yuan.cxf.dao.ComplaintMapper;
import com.yuan.cxf.pojo.Administrator;
import com.yuan.cxf.pojo.Complaint;
import com.yuan.cxf.service.ComplaintService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;
import java.util.Objects;

//查询投诉服务
@AutoPublishWS(publishPath = "complaint")
@Service
@WebService
public class ComplaintServiceImpl implements ComplaintService {

    @Resource
    ComplaintMapper complaintMapper;


    //输出数据库中Complaint表中的所有记录
    @WebMethod
    @WebResult(name = "AllComplaintInfo")
    @Override
    public String getAllComplaint() {

        //获取所有Complaint记录
        List<Complaint> complaints = complaintMapper.selectList(null);
        System.out.println("===========");
        System.out.println("complaints   "+complaints);
        System.out.println("===========");

        //将获取到的complaints转换成json字符串
        String json = JSON.toJSONString(complaints);
        System.out.println("===========");
        System.out.println("json  "+complaints);
        System.out.println("===========");




//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject;
//        for (int i = 0; i < 5; i++) {
//            jsonObject = new JSONObject();
//            jsonObject.put("userCode","code"+(i+1));
//            jsonObject.put("userName","用户"+(i+1));
//            jsonArray.add(jsonObject);
//        }


        return json;

    }

    //输出数据库中Complaint表中的一个特定的记录
    @WebMethod
    @WebResult(name = "OneComplaintInfo")
    @Override
    public String getOneComplaint(@WebParam(name = "complaint")Integer complaintId) {



        //构造查询器
        QueryWrapper<Complaint> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("complaintid",complaintId);

        //获取指定的要修改的complaint的信息
        Complaint complaint = complaintMapper.selectOne(queryWrapper);
        System.out.println("===========");
        System.out.println("complaint   "+complaint);
        System.out.println("===========");

        //将Complaint对象转换成JSON字符串
        String json = JSON.toJSONString(complaint);
        System.out.println("===========");
        System.out.println("json  "+complaint);
        System.out.println("===========");

        return json;

    }

    //输出数据库中Complaint表指定用户的记录
    @WebMethod
    @WebResult(name = "userComplaintInfo")
    @Override
    public String getUserComplaint(@WebParam(name = "userId")Integer userId){

        //获取所有Complaint记录
        List<Complaint> complaints = complaintMapper.selectList(null);
        System.out.println("===========");
        System.out.println("complaints   "+complaints);
        System.out.println("===========");

        //删除Complaint中不是该用户的记录
        for (int i = complaints.size() - 1; i >= 0; --i) {
            System.out.printf("index %d: ", i);
            System.out.println(complaints.get(i).getCustomerid());
            if (!Objects.equals(userId, complaints.get(i).getCustomerid())) {
                complaints.remove(i);
            }
        }

        //将Complaints列表转换成JSON字符串
        String json = JSON.toJSONString(complaints);
        System.out.println("===========");
        System.out.println("json  "+complaints);
        System.out.println("===========");

        return json;

    }

}
