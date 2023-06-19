package com.igeekhome.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.igeekhome.biz.AdministratorService;
import com.igeekhome.biz.ComplaintService;
import com.igeekhome.dao.ComplaintMapper;
import com.igeekhome.pojo.Administrator;
import com.igeekhome.pojo.Complaint;
import com.igeekhome.pojo.Customer;
import com.igeekhome.dao.CustomerMapper;
import com.igeekhome.dao.CustomerMapper;
import com.igeekhome.biz.CustomerService;


import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private ComplaintMapper complaintMapper;
    @Autowired
    private AdministratorService administratorService;


    //创建新客服
    @RequestMapping("/add")
    public String add(HttpSession session, Model model,Complaint complaint){
        model.addAttribute("main","创建投诉");
        model.addAttribute("email", session.getAttribute("email"));
        return "addcomplaint";
//        return "login";
    }

    //当前端填写表单后，创建成功
    @RequestMapping("/addVerify")
    public String addVerify(HttpSession session, Model model, Complaint complaint, @RequestParam("current") Integer current, @RequestParam("size") Integer size){


        complaint.setDateAndState(session);
        System.out.println("===Test print complaint===");
        System.out.println(complaint);
        complaintMapper.insert(complaint);

//        model.addAttribute("main","客服管理主页");
//        model.addAttribute("current",current);
//        model.addAttribute("size",size);
//        initcomplaintService(model,current,size);
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
        System.out.println("####");
        System.out.println(complaint);
        System.out.println("====");
        System.out.println(complaint.getComplaintid());
        Complaint complaint1= complaintService.getById(complaint.getComplaintid());

//        System.out.println(complaint1.getComplaintid());
        model.addAttribute("cs",complaint1);
        model.addAttribute("current",current);
        model.addAttribute("size",size);
        model.addAttribute("main","投诉处理");
        return "updateallcomplaint";

    }

    //修改完成,并跳转到客服管理界面
    @RequestMapping("/updateAllVerify")
    public String updateVerify(Complaint complaint, Model model,@RequestParam("current") Integer current, @RequestParam("size") Integer size){

        this.complaintService.updateById(complaint);
        initcomplaintService(model,current,size);
        model.addAttribute("main","客服管理主页");
        return "complaint";

    }


    public void initcomplaintServiceUser(HttpSession session,Model model, Integer current, Integer size)
    {
        IPage<Complaint> page=new Page<>(current,size);
        IPage<Complaint> page1 = this.complaintService.page(page);

        List<Complaint> list = page1.getRecords();
        long pagesCount = page1.getPages();

        com.igeekhome.pojo.Customer user = (com.igeekhome.pojo.Customer)session.getAttribute("customer");
        Integer userId = user.getCustomerid();


        System.out.println("===loop===");
        System.out.println(userId);
        System.out.println(list.size());


        // for (int i = 0; i < list.size(); ++i) {
        for (int i = list.size() - 1; i >= 0; --i) {
            System.out.printf("index %d: ", i);
            System.out.println(list.get(i).getCustomerid());
            if (!Objects.equals(userId, list.get(i).getCustomerid())) {
                list.remove(i);
            }
        }



        model.addAttribute("list",list);
        model.addAttribute("pagesCount",pagesCount);
        model.addAttribute("main","客户管理页面");
    }

    public void initcomplaintService(Model model, Integer current, Integer size)
    {

        IPage<Complaint> page=new Page<>(current,size);
        IPage<Complaint> page1 = this.complaintService.page(page);

        List<Complaint> list = page1.getRecords();
        long pagesCount = page1.getPages();


        model.addAttribute("list",list);
        model.addAttribute("pagesCount",pagesCount);
        model.addAttribute("main","客户管理页面");
    }

}
