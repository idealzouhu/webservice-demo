package com.igeekhome.dao;

import com.igeekhome.pojo.Complaint;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Wrapper;

/**
 * <p>
 * 投诉信息 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-03-16
 */
public interface ComplaintMapper extends BaseMapper<Complaint> {
}
