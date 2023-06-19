package com.yuan.cxf.config;

import com.yuan.cxf.annotation.AutoPublishWS;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 自动发布
 *
 * @author:jinshengyuan
 * @date: 2022-1-29
 */
@Slf4j
@Component
@Order(1)
public class AutoPublishWSEndpoint implements ApplicationRunner {
    //取出WS的根路径
    @Value("${cxf.path}")
    public String cxfPath;

    //取出AppName
    @Value("${spring.application.name}")
    public String appName;

    //注入CXF Bus
    @Autowired
    private Bus bus;

    //注入Spring Web中Servlet上下文Web容器
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("AutoPublishWSEndpoint===发布开始");
        //根据注解获取beanNames
        String[] beanNames = webApplicationContext.getBeanNamesForAnnotation(AutoPublishWS.class);
        EndpointImpl endpoint;
        List<String> cxfPathList = new ArrayList<>();
        for (String beanName : beanNames) {
            //根据beanName获取属性名
            String publishPath = webApplicationContext.getType(beanName).getAnnotation(AutoPublishWS.class).publishPath();
            //发布WebService接口
            endpoint = new EndpointImpl(bus, webApplicationContext.getBean(beanName));
            endpoint.publish(publishPath);
            cxfPathList.add(publishPath);
            log.info(String.format("%s", publishPath));
        }
        log.info("AutoPublishWSEndpoint===发布结束");

        Environment environment = webApplicationContext.getEnvironment();
        address(environment, cxfPathList);
    }

    /**
     * description: 打印访问路径
     *
     * @param environment 运行环境
     * @param cxfPathList 发布的WS路径
     * @return String
     * @author:jinshengyuan
     * @date: 2022-1-29
     */
    public void address(Environment environment, List<String> cxfPathList) {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = environment.getProperty("server.port");
            String path = environment.getProperty("server.servlet.context-path");
            if (path == null) {
                path = "";
            }
            log.info("\n----------------------------------------------------------\n\t" +
                    "Application " + appName + " is running! Access URLs:\n\t" +
                    "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                    "External: \thttp://" + ip + ":" + port + path + "/" +
                    "\n----------------------------------------------------------");
            if (cxfPathList.size() > 0) {
                StringBuilder logStr = new StringBuilder();
                logStr.append("\n\tWSDL URLS:\n\t");
                String finalPath = path;
                cxfPathList.forEach(s -> {
                    logStr.append("\t\thttp://localhost:" + port + finalPath + cxfPath + "/" + s + "?wsdl\n\t");
                    logStr.append("\t\thttp://" + ip + ":" + port + finalPath + cxfPath + "/" + s + "?wsdl\n\t");
                });
                log.info(logStr.toString());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
