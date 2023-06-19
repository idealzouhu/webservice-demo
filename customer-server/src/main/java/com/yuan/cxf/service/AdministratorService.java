package com.yuan.cxf.service;

import com.yuan.cxf.pojo.Complaint;

import javax.jws.WebParam;

public interface AdministratorService {

    String verifyAdministrator(String email , String password);

}
