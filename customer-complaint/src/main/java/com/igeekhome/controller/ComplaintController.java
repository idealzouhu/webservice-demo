package com.igeekhome.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.igeekhome.pojo.Complaint;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * <p>
 * 客户信息 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2021-03-16
 */
@Controller
@RequestMapping("/complaint")
public class ComplaintController {

    //创建新客服
    @RequestMapping("/add")
    public String add(HttpSession session, Model model,Complaint complaint){
        model.addAttribute("main","创建投诉");
        model.addAttribute("email", session.getAttribute("email"));

        return "addcomplaint";
    }

    //当前端填写表单后，创建成功
    @RequestMapping("/addVerify")
    public String addVerify(HttpSession session, Model model, Complaint complaint, @RequestParam("current") Integer current, @RequestParam("size") Integer size){

        //自动填充投诉的一些信息，例如投诉填写时间，投诉人之类的
        complaint.setDateAndState(session);

        //将获取到的complaints转换成json字符串,然后转成Object对象以便调用服务
        String complaintjson = JSON.toJSONString(complaint);
        Object complaintObject = complaintjson;

        //动态调用服务，向数据库中添加新创建的投诉
        DynamicClientFactory clientFactory = DynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:8102/ws/writeComplaint?wsdl");
        Object[] objects;
        String result = "asaddfaafaf";
        try {
            objects = client.invoke("addComplaint", complaintObject);
            if (objects != null) {
                result = (String) objects[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }

    // 客户查看投诉进度页面，分页显示
    @RequestMapping("/view")
    public String view(HttpSession session, Model model, @RequestParam("current") Integer current, @RequestParam("size") Integer size){

        initcomplaintServiceUser(session, model,current,size);
        model.addAttribute("main","查看投诉进度");
        return "customercomplaint";
    }

    //客服查看客户投诉页面,分页显示
    @RequestMapping("/message")
    public String index(Model model, @RequestParam("current") Integer current, @RequestParam("size") Integer size){

        initcomplaintService(model,current,size);
        model.addAttribute("main","客服管理主页");
        return "complaint";
    }


    //得到客服列表中的要修改的对象信息，然后跳转到修改页面
    @RequestMapping("/updateAll")
    public String update(Model model, Complaint complaint, @RequestParam("current") Integer current, @RequestParam("size") Integer size){

        //获取要修改的comlaint的id
        Integer complaintId = complaint.getComplaintid();
        Object complaintIdObject = complaintId;

        //动态调用服务，获取指定id的complaint
        DynamicClientFactory clientFactory = DynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:8102/ws/complaint?wsdl");
        Object[] objects;
        String result = "asaddfaafaf";
        try {
            objects = client.invoke("getOneComplaint", complaintIdObject);
            if (objects != null) {
                result = (String) objects[0];

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将获取到的JSON格式的Complaint转换成相应对象
        Complaint complaint1 = JSON.parseObject(result,Complaint.class);

        model.addAttribute("cs",complaint1);
        model.addAttribute("current",current);
        model.addAttribute("size",size);
        model.addAttribute("main","投诉处理");
        return "updateallcomplaint";

    }

    //修改完成,并跳转到客服管理界面
    @RequestMapping("/updateAllVerify")
    public String updateVerify(Complaint complaint, Model model,@RequestParam("current") Integer current, @RequestParam("size") Integer size){

        //将获取到的complaints转换成json字符串,然后转成Object对象以便调用服务
        String complaintjson = JSON.toJSONString(complaint);
        Object complaintObject = complaintjson;

        //动态调用服务，修改complaint
        DynamicClientFactory clientFactory = DynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:8102/ws/manageComplaint?wsdl");
        Object[] objects;
        try {
            objects = client.invoke("updateComplaint", complaintObject);
            if (objects != null) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initcomplaintService(model,current,size);
        model.addAttribute("main","客服管理主页");
        return "complaint";

    }

    //显示客户User只能看到的自己所写的投诉
    public void initcomplaintServiceUser(HttpSession session,Model model, Integer current, Integer size)
    {

        long pagesCount = 1;

        //获取当前浏览器登录用户的userId
        com.igeekhome.pojo.Customer user = (com.igeekhome.pojo.Customer)session.getAttribute("customer");
        Integer userId = user.getCustomerid();

        Object userIdObject = userId;


        //动态调用服务，显示数据库中的所有complaint给客服
        DynamicClientFactory clientFactory = DynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:8102/ws/complaint?wsdl");
        Object[] objects;
        String result = "asaddfaafaf";
        try {
            objects = client.invoke("getUserComplaint", userIdObject);
            if (objects != null) {
                result = (String) objects[0];

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将获取到的Complain表中所有记录的json字符串转换成 列表
        List<Complaint> list  = JSON.parseArray(result,Complaint.class);

        model.addAttribute("list",list);
        model.addAttribute("pagesCount",pagesCount);
        model.addAttribute("main","客户管理页面");
    }


    //获取所有complaint的函数
    public void initcomplaintService(Model model, Integer current, Integer size)
    {

        IPage<Complaint> page=new Page<>(current,size);
        long pagesCount = 1;

        //动态调用服务，显示数据库中的所有complaint给客服
        DynamicClientFactory clientFactory = DynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:8102/ws/complaint?wsdl");
        Object[] objects;
        String result = "asaddfaafaf";
        try {
            objects = client.invoke("getAllComplaint", new Object[]{});
            if (objects != null) {
                result = (String) objects[0];

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将获取到的Complain表中所有记录的json字符串转换成 列表
        List<Complaint> list  = JSON.parseArray(result,Complaint.class);


        model.addAttribute("list",list);
        model.addAttribute("pagesCount",pagesCount);
        model.addAttribute("main","客户管理页面");
    }

}
