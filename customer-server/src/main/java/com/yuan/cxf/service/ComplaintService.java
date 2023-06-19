package com.yuan.cxf.service;

import com.yuan.cxf.pojo.Complaint;

public interface ComplaintService {

    String getAllComplaint();
    String getOneComplaint(Integer complaintId);
    String getUserComplaint(Integer complaintId);


}
