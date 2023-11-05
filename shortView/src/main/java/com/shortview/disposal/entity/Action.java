package com.shortview.disposal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author wanghui
 * @Date 2023/11/4 0004 17:04
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("action")
public class Action {
    @TableId(type = IdType.AUTO)
    private Long aid;
    private Long video_id;
    private Long user_id;
    private String action_type;
}
