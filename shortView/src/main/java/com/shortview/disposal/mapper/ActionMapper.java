package com.shortview.disposal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shortview.disposal.entity.Action;
import com.shortview.disposal.entity.QiniuVideo;
import com.shortview.disposal.entity.Video;
import org.apache.ibatis.annotations.*;

/**
 * @Author wanghui
 * @Date 2023/11/4 0004 17:13
 * @Version 1.0
 */
@Mapper
public interface ActionMapper extends BaseMapper<Action> {
    @Insert("INSERT INTO action (user_id, video_id, action_type) " +
            "VALUES (#{user_id}, #{video_id}, #{action_type})")
    void save(Action action);


    @Select("SELECT " +
            " COUNT(*) " +
            " FROM action" +
            " WHERE user_id = #{userId} AND video_id = #{videoId} ")
    int existsByUserIdAndVideoId(@Param("userId") Long userId, @Param("videoId") Long videoId);

    @Delete("DELETE FROM action WHERE user_id = #{userId} AND video_id = #{videoId}")
    void deleteByUserIdAndVideoId(Long userId, Long videoId);

}
