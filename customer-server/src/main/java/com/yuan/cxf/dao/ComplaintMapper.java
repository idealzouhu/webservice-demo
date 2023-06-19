package com.yuan.cxf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.cxf.pojo.Complaint;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 投诉信息 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-03-16
 */
public interface ComplaintMapper extends BaseMapper<Complaint> {

    @Select("SELECT * FROM Complaint")
    List<Complaint> seclectAllComplaint();

}
