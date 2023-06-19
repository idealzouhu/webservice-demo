package com.igeekhome.controller;

import com.alibaba.fastjson.JSON;
import com.igeekhome.pojo.Customer;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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

    //客户和客服登录
    @RequestMapping("/login")
    public String checkCustomerServiceNameAndPwd(com.igeekhome.pojo.Customer customer, Model model, HttpSession session) throws Exception {
        String path = null;

        //表示输入信息的正确性
        String master =  "false";  //客服
        String custom = "false";   //客户

        //获取登录人员输入的email、password
        String email = customer.getEmail();
        String password = customer.getPassword();
        session.setAttribute("email", customer.getEmail());  //后面填写投诉用到


        //将登录人员输入的email、password转为 Object对象
        Object emailObject = email;
        Object passwordObject = password;


        //将登录客户是否存在的判断custom放在session里
        DynamicClientFactory clientFactory = DynamicClientFactory.newInstance();
        Client client1 = clientFactory.createClient("http://localhost:8102/ws/customer?wsdl");
        Object[] objects1;
        try {
            objects1 = client1.invoke("verifyCustomer", emailObject, passwordObject);
            if (objects1 != null) {
                custom = (String) objects1[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将管理员是否存在的判断master放在session里
        Client client = clientFactory.createClient("http://localhost:8102/ws/administrator?wsdl");
        Object[] objects;
        try {
            objects = client.invoke("verifyAdministrator", emailObject, passwordObject);
            if (objects != null) {
                master = (String) objects[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("master",master);  //添加到session里


        //判断是否允许登录
        if( custom.equals("false") ){
            System.out.println("===cs is null===");
            path = "redirect:/login";//forward从当前路径转发，一次请求
            session.setAttribute("msg","用户名或密码不正确");

        }else{
            System.out.println("===cs is not null===");

            //登录正确的跳转
            session.setAttribute("cs",custom);
            path = "forward:/index";   //redirect从跟目录转发，二次请求


            //动态调用服务，获取到用户的信息, 即用户的entity
            Client client2 = clientFactory.createClient("http://localhost:8102/ws/customer?wsdl");
            Object[] objects2;
            String result = "asaddfaafaf";  //保存调用服务返回的的JSON字符串
            try {
                objects = client2.invoke("getCustomer", emailObject, passwordObject);
                if (objects != null) {
                    result = (String) objects[0];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //将获取到的JSON格式的customer转换成相应对象
            Customer user = JSON.parseObject(result, Customer.class);

            session.setAttribute("customer", user);

        }
        return path;
    }




    //将注册账号数据传输到数据库，然后跳回到登录页面(单个账号)
    @RequestMapping("/register")
    public String registerCustomer(com.igeekhome.pojo.Customer customer, Model model, HttpSession session)
    {

        //将获取到的customer转换成json字符串,然后转成Object对象以便调用服务
        String customerjson = JSON.toJSONString(customer);
        Object customerObject = customerjson;

        //动态调用服务，向数据库中添加新创建的投诉
        DynamicClientFactory clientFactory = DynamicClientFactory.newInstance();
        Client client = clientFactory.createClient("http://localhost:8102/ws/customer?wsdl");
        Object[] objects;
        String result = "asaddfaafaf";
        try {
            objects = client.invoke("addCustomer", customerObject);
            if (objects != null) {
                result = (String) objects[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login";

    }


    @RequestMapping("/index")
    public String index(Model model, @RequestParam("current") Integer current, @RequestParam("size") Integer size){

        return "index";
    }

}


