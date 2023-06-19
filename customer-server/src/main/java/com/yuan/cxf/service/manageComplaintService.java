package com.yuan.cxf.service;

public interface manageComplaintService {

    //获取客户端传来的需要修改的对象complaintjson ,然后将修改传到到数据库里
    String updateComplaint(String complaintjson);
}
