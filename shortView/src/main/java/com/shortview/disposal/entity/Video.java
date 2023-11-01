package com.shortview.disposal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author wanghui
 * @Date 2023/10/27 0027 11:44
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("video")
public class Video {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String url;
    private Integer user_id;
    private Integer count;
    private Integer class_id;
    private Date create_time;
    private String filename;
    private String context;
    private String title;
    private int status;
}
