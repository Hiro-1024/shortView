package com.shortview.disposal.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shortview.disposal.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author wanghui
 * @Date 2023/10/27 0027 11:23
 * @Version 1.0
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    // 获取下一个视频的url
    @Select("SELECT url, title, context FROM video WHERE id = (SELECT MIN(id) FROM video WHERE id > #{id})")
    List<Video> findNextVideoInfo(@Param("id") Long id);

    // 通过url获取 Id
    @Select("select id FROM  video WHERE url = #{url} ")
    int getId(String url);
    
    //查看视频状态
    @Select("SELECT status FROM  video WHERE  id = #{id} ")
    int getStatus(Long id);

    //通过关键字搜索视频
    @Select("SELECT url, title, context FROM video " +
            "WHERE title LIKE '%${query}%' " +
            "OR context LIKE '%${query}%'")
    List<Video> findByTitleContaining(@Param("query") String query);

    //通过classId获取视频的信息
    @Select("SELECT url, title, context FROM video  WHERE class_id= #{classId}")
    List<String> findClassVideoInfo(int classId);

    //基于当前id查询下一个id
    @Select("SELECT COALESCE((SELECT MIN(id) FROM video WHERE id > #{id}), 0)")
    Long getNextId(@Param("id") Long id);

    @Select("SELECT url,context,title FROM  video WHERE  user_id =#{uid} ")
    List<Video> getPersonalVideos(Long uid);


    @Update("UPDATE video SET likes = likes + 1 WHERE id = #{videoId}")
    void updateLikeCount(@Param("videoId") Long videoId);

    @Select("SELECT COUNT(*) FROM video WHERE  id = #{videoId} ")
    int  chenckVideo(Long videoId);

    @Update("UPDATE video SET likes = likes - 1 WHERE id = #{videoId}")
    void updateLikeCountLose(Long videoId);
}


