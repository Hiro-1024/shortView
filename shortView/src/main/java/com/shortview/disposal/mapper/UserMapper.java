package com.shortview.disposal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shortview.disposal.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT COUNT(*) FROM user WHERE  id = #{userId} ")
    int checkUser(Long userId);
    // 可以在此添加自定义的查询方法
}
