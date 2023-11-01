package com.shortview.disposal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shortview.disposal.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author wanghui
 * @Date 2023/10/29 0029 10:11
 * @Version 1.0
 */
@Mapper
public interface ClassMapper extends BaseMapper<Class> {
    //查询类名对应的id
    @Select("SELECT class_id FROM class WHERE className = #{className}")
    int getClassByName(@Param("className") String className);
}
