package com.yuan.cxf.service;

public interface writeComplaintService {

    //获取客户端传来的创建的对象complaintjson ,然后添加到数据库里
    String addComplaint(String complaintjson);

}
