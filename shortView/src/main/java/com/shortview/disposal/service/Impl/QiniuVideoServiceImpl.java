package com.shortview.disposal.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shortview.disposal.entity.QiniuVideo;
import com.shortview.disposal.mapper.QiniuVideoMapper;
import com.shortview.disposal.service.QiniuVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wanghui
 * @Date 2023/11/4 0004 11:02
 * @Version 1.0
 */
@Service
public class QiniuVideoServiceImpl extends ServiceImpl<QiniuVideoMapper, QiniuVideo> implements QiniuVideoService {
    @Autowired
    private QiniuVideoMapper qiniuVideoMapper;
    //通过视频id获取获取桶名
    @Override
    public String getBucketById(Long id) {
        String bucket = qiniuVideoMapper.getBucketById(id);
        return bucket;
    }

    @Override
    public String getPathById(Long id) {
        String path = qiniuVideoMapper.getPathById(id);
        return path;
    }
}
