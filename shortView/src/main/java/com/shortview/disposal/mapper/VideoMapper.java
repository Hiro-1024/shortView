package com.shortview.disposal.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shortview.disposal.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author wanghui
 * @Date 2023/10/27 0027 11:23
 * @Version 1.0
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    // 获取下一个视频的url
    @Select("SELECT url FROM video WHERE id = (SELECT MIN(id) FROM video WHERE id > #{id})")
    String findNextVideoUrl(@Param("id") Long id);

    // 通过url获取 Id
    @Select("select id FROM  video WHERE url = #{url} ")
    int getId(String url);

    //通过类名id获取视频的url
    @Select("SELECT url FROM  video WHERE  class_id = #{class_id} ")
    List<String> findClassVideoUrls(int class_id);

    @Select("SELECT status FROM  video WHERE  id = #{id} ")
    int getStatus(Long id);
}


