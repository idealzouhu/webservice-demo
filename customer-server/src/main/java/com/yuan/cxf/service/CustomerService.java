package com.yuan.cxf.service;

public interface CustomerService {

    String verifyCustomer(String email , String password);

    String getCustomer(String email , String password);

    String addCustomer(String customerjson);

}
