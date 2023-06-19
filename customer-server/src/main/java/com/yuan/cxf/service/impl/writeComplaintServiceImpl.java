package com.yuan.cxf.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuan.cxf.annotation.AutoPublishWS;
import com.yuan.cxf.dao.ComplaintMapper;
import com.yuan.cxf.pojo.Complaint;
import com.yuan.cxf.service.writeComplaintService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;


//填写投诉服务
@AutoPublishWS(publishPath = "writeComplaint")
@Service
@WebService
public class writeComplaintServiceImpl implements writeComplaintService {

    @Resource
    ComplaintMapper complaintMapper;

    @WebMethod
    @WebResult(name = "addComplaint")
    @Override
    //获取客户端传来的创建的对象complaintjson ,然后添加到数据库里
    public String addComplaint(String complaintjson){

        //将获取到的JSON格式的Complaint转换成相应对象
        Complaint complaint = JSON.parseObject(complaintjson,Complaint.class);
        System.out.println("===========");
        System.out.println("JSON complaint   "+ complaint);
        System.out.println("===========");

        //将获取的更新到数据库
        complaintMapper.insert(complaint);

        return "true";

    }

}
