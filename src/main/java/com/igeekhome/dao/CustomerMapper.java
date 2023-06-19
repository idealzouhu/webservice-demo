package com.igeekhome.dao;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.igeekhome.pojo.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Wrapper;

/**
 * <p>
 * 客户信息 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-03-16
 */
public interface CustomerMapper extends BaseMapper<Customer> {


}
