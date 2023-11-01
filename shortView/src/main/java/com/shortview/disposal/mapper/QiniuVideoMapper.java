package com.shortview.disposal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shortview.disposal.entity.QiniuVideo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @Author wanghui
 * @Date 2023/10/31 0031 10:37
 * @Version 1.0
 */
@Mapper
public interface QiniuVideoMapper extends BaseMapper<QiniuVideo> {
    //添加到qiniuvideo数据库中
    @Insert("INSERT INTO qiniuvideo (video_id, bucket, fileKey, path, url, create_time, originalFilename) " +
            "VALUES (#{video_id}, #{bucket}, #{fileKey}, #{path}, #{url}, #{create_time}, #{originalFilename})")
    int Insert(QiniuVideo qiniuVideo);

    //通过路径去查询对应的video_id
    @Select("SELECT video_id FROM qiniuvideo WHERE path = #{path} ")
    Integer getIdByPath(String path);

    //通过video_id去查询qiniu_id
    @Select("SELECT  qiniu_id FROM  qiniuvideo WHERE  video_id= #{videoId} ")
    int getQidByVid(int videoId);
}
