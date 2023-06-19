package com.igeekhome.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.igeekhome.biz.AdministratorService;
import com.igeekhome.pojo.Administrator;
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
import javax.swing.plaf.synth.SynthTextAreaUI;
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
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AdministratorService administratorService;

    //客户和客服登录
    @RequestMapping("/login")
    public String checkCustomerServiceNameAndPwd(com.igeekhome.pojo.Customer customer, Model model, HttpSession session){
        String path = null;
        String master = null;

        QueryWrapper<com.igeekhome.pojo.Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",customer.getEmail());
        queryWrapper.eq("password",customer.getPassword());
        com.igeekhome.pojo.Customer cs = customerService.getOne(queryWrapper);  //获取登录客服人员cs的信息

        QueryWrapper<Administrator> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("email",customer.getEmail());
        queryWrapper1.eq("password",customer.getPassword());
        Administrator administrator= administratorService.getOne(queryWrapper1);  //获取管理员的信息

        if(administrator != null){  //将管理员是否存在信息的判断放在session里
            master = "true";
            session.setAttribute("master",master);
        }else{
            master = "false";
            session.setAttribute("master",master);
        }


        if(cs == null){
            System.out.println("===cs is null===");
            path = "redirect:/login";//forward从当前路径转发，一次请求
            session.setAttribute("msg","用户名或密码不正确");

        }else{
            System.out.println("===cs is not null===");

            //登录正确的跳转
            session.setAttribute("cs",cs);
            path = "forward:/index";   //redirect从跟目录转发，二次请求

            QueryWrapper<com.igeekhome.pojo.Customer> queryWrapper_user = new QueryWrapper<>();
            queryWrapper_user.eq("customerId",customer.getCustomerid());
            com.igeekhome.pojo.Customer user = customerService.getOne(queryWrapper);
            session.setAttribute("customer", user);
            session.setAttribute("email", customer.getEmail());  //后面填写投诉用到
        }

        return path;
    }


    //将注册账号数据传输到数据库，然后跳回到登录页面(单个账号)
    @RequestMapping("/register")
    public String registerCustomer(com.igeekhome.pojo.Customer customer, Model model, HttpSession session)
    {
        int result = customerMapper.insert(customer);
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model, @RequestParam("current") Integer current, @RequestParam("size") Integer size){

        return "index";
    }




    public void initcustomerService(Model model, Integer current, Integer size)
    {

        IPage<Customer> page=new Page<>(current,size);
        IPage<Customer> page1 = this.customerService.page(page);

        List<Customer> list = page1.getRecords();
        long pagesCount = page1.getPages();


        model.addAttribute("list",list);
        model.addAttribute("pagesCount",pagesCount);
        model.addAttribute("main","客户管理页面");
    }


}


