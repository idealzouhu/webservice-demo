package com.igeekhome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 客户投诉表
 * </p>
 *
 * @author ${author}
 * @since 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Complaint implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "complaintId", type = IdType.AUTO)
    private Integer complaintid;

    @TableField("customerId")
    private Integer customerid;

    /**
     * 投诉原因
     */
    private String reason;

    /**
     * 投诉客户姓名
     */
    @TableField("name")
    private String name;

    /**
     * 投诉处理状态
     */
    @TableField("state")
    private String state;

    /**
     * 投诉处理完成时间
     */
    @TableField("date")
    private String date;


    /**
     * 投诉处理操作
     */
    @TableField("operation")
    private String operation;

    public void setDateAndState(HttpSession session)
    {
        // Set State
        state = "等待处理";

        // Set Date
        Date now_date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = sdf.format(now_date);

        // Set customer ID
        com.igeekhome.pojo.Customer user = (com.igeekhome.pojo.Customer)session.getAttribute("customer");
        Integer userId = user.getCustomerid();
        customerid = userId;
    }

}
