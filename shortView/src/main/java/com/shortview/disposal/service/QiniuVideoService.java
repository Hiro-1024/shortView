package com.shortview.disposal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shortview.disposal.entity.QiniuVideo;

/**
 * @Author wanghui
 * @Date 2023/11/4 0004 11:01
 * @Version 1.0
 */
public interface QiniuVideoService extends IService<QiniuVideo> {
    String getBucketById(Long id);

    String getPathById(Long id);
}
