package com.shortview.disposal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shortview.disposal.entity.Action;
import org.apache.ibatis.annotations.*;

/**
 * @Author wanghui
 * @Date 2023/11/4 0004 17:13
 * @Version 1.0
 */
@Mapper
public interface ActionMapper extends BaseMapper<Action> {
    @Insert("INSERT INTO action (user_id, video_id, like_count) VALUES (#{user_id}, #{video_id}, #{like_count})")
    void save(Action action);


    @Select("SELECT CASE" +
            " WHEN COUNT(*) > 0 THEN TRUE" +
            " ELSE FALSE" +
            " END AS 'exists'" +
            " FROM action" +
            " WHERE user_id = #{userId} AND video_id = #{videoId} ;")
    boolean existsByUserIdAndVideoId(@Param("userId") Long userId, @Param("videoId") Long videoId);

    @Delete("DELETE FROM action WHERE user_id = #{userId} AND video_id = #{videoId}")
    void deleteByUserIdAndVideoId(Long userId, Long videoId);

    @Select("SELECT COUNT(*) FROM action WHERE video_id = #{videoId}")
    int getCount(@Param("videoId") Long videoId);
}
