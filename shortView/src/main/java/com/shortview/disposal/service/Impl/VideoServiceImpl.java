package com.shortview.disposal.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shortview.disposal.configuration.QiniuConfiguration;
import com.shortview.disposal.entity.Action;
import com.shortview.disposal.entity.QiniuVideo;
import com.shortview.disposal.entity.Video;
import com.shortview.disposal.mapper.ActionMapper;
import com.shortview.disposal.mapper.ClassMapper;
import com.shortview.disposal.mapper.QiniuVideoMapper;
import com.shortview.disposal.mapper.VideoMapper;
import com.shortview.disposal.service.VideoService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 10:28
 * @Version 1.0
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private  QiniuConfiguration qiniuConfiguration;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private QiniuVideoMapper qiniuVideoMapper;
    @Autowired
    private ActionMapper actionMapper;
    @Resource
    private RedisTemplate<String, List<Video>> redisTemplate;

    @Override
    public String uploadVideo(MultipartFile multipartFile, String className,String context,String title,int status) {
        QiniuVideo qiniuVideo = qiniuConfiguration.uploadVideoQiniu(multipartFile);
        if (qiniuVideo != null) {
            //保存视频信息到数据库video
            int class_id = getClassByName(className);
            Video video1 = new Video();
            video1.setUrl(qiniuVideo.getUrl());
            video1.setClass_id(class_id);
            video1.setUser_id(1);
            video1.setCreate_time(qiniuVideo.getCreate_time());
            video1.setFilename(qiniuVideo.getOriginalFilename());
            video1.setContext(context);
            video1.setTitle(title);
            video1.setStatus(status);
            video1.setLikes(0);
            videoMapper.insert(video1);
            //保存七牛云存储上的信息
            int video_id = videoMapper.getId(video1.getUrl());
            qiniuVideo.setVideo_id((long) video_id);
            qiniuVideoMapper.Insert(qiniuVideo);
        }
        return qiniuVideo.getUrl();
    }


    //通过类名获取类名id
    private int getClassByName(String className) {
        try {
            int classId = classMapper.getClassByName(className);
            return classId;
        } catch (Exception e) {
            // 当没有匹配的记录时，返回一个默认值或抛出自定义异常
            return -1;
        }
    }

    //滑动获取下一个视频的url
    @Override
    public List<Video> getNextVideoUrl(Long id) {
        List<Video> nextVideoInfo = videoMapper.findNextVideoInfo(id);

        if (nextVideoInfo!= null && !nextVideoInfo.isEmpty()) {
            return nextVideoInfo;
        } else {
            return Collections.emptyList();
        }
    }

    //删除视频信息
    @Override
    public boolean remove(String bucket,String path) {
        boolean flag = qiniuConfiguration.removeVideo(bucket, path);
        Integer video_id = qiniuVideoMapper.getIdByPath(path);
        if (video_id != null) {
            int qiniu_id = qiniuVideoMapper.getQidByVid(video_id);
            if (flag) {
                videoMapper.deleteById(video_id);
                qiniuVideoMapper.deleteById(qiniu_id);
            }
        }
        return flag;
    }


    // 通过类名获取下一个视频的信息
    @Override
    public List<String> getClassVideoInfo(String className) {
        int class_id = getClassByName(className);
        List<String> videoUrls = videoMapper.findClassVideoInfo(class_id);
        return videoUrls;
    }

    //获取视频的状态
    @Override
    public int getStatus(Long id) {
        int status = videoMapper.getStatus(id);
        return status;
    }

    //搜索视频
    @Override
    public List<Video> searchVideos(String query) {
        // 1. 尝试从Redis缓存中获取搜索结果
        String cacheKey = "searchResults:" + query;
        List<Video> cachedResults = redisTemplate.opsForValue().get(cacheKey);

        if (cachedResults != null) {
            // 2. 如果Redis中有缓存数据，直接返回
            return cachedResults;
        } else {
            // 3. 如果Redis中没有缓存数据，执行数据库查询操作
            List<Video> searchResults = videoMapper.findByTitleContaining(query);

            // 4. 将数据库查询结果存入Redis缓存并设置过期时间
            if (!searchResults.isEmpty()) {
                redisTemplate.opsForValue().set(cacheKey, searchResults);
                // 设置缓存过期时间， 1小时
                redisTemplate.expire(cacheKey, 1, TimeUnit.HOURS);
            }
            return searchResults;
        }
    }

    //通过当前id查询下一个id
    public Long getNextId(Long id) {
        Long nid = videoMapper.getNextId(id);
        return nid;
    }

    //获取个人发布视频列表
    @Override
    public List<Video> getPersonalVideos(Long uid) {
        List<Video> perList = videoMapper.getPersonalVideos(uid);
        return perList;
    }

}
