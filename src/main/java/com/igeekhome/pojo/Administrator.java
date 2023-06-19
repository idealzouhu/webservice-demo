package com.igeekhome.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author ${author}
 * @since 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Administrator {

    /**
     * 密码
     */
    private String password;
    

    @TableField("email")
    private String email;

    /**
     * 主键
     */
    @TableId(value = "administratorId", type = IdType.AUTO)
    private Integer administratorId;

}
