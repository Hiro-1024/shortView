package com.shortview.disposal.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author wanghui
 * @Date 2023/10/28 0028 15:09
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("qiniuvideo")
//七牛云服务器中的视频信息
public class QiniuVideo {
    @TableId(type = IdType.AUTO)
    private Long qiniuId;
    //外键
    private Long video_id;
    //视频存储的桶名
    private String bucket;
    //视频文件的唯一标识
    private String fileKey;
    //视频文件的云存储器中的存储路径
    private String path;
    //视频文件的外连接，用于访问视频
    private String url;
    private Date create_time;
    private String originalFilename;
    public QiniuVideo(String bucket, String fileKey, String url, String path, Date createTime,String originalFilename) {
        // 初始化 QiniuVideo 对象
        this.bucket = bucket;
        this.fileKey = fileKey;
        this.url = url;
        this.path = path;
        this.create_time = createTime;
        this.originalFilename = originalFilename;
    }
}
