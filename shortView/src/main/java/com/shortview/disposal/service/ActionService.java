package com.shortview.disposal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shortview.disposal.entity.Action;
import com.shortview.disposal.response.Result;

/**
 * @Author wanghui
 * @Date 2023/11/4 0004 17:11
 * @Version 1.0
 */
public interface ActionService extends IService<Action> {
    String likeVideo(Long userId, Long videoId);

    String unlikeVideo(Long userId, Long videoId);
}
