package com.shortview.disposal.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shortview.disposal.configuration.QiniuConfiguration;
import com.shortview.disposal.entity.QiniuVideo;
import com.shortview.disposal.entity.Video;
import com.shortview.disposal.mapper.ClassMapper;
import com.shortview.disposal.mapper.QiniuVideoMapper;
import com.shortview.disposal.mapper.VideoMapper;
import com.shortview.disposal.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Override
    public QiniuVideo uploadVideo(MultipartFile multipartFile, String className,String context,String title,int status) {
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
            videoMapper.insert(video1);
            //保存七牛云存储上的信息
            int video_id = videoMapper.getId(video1.getUrl());
            qiniuVideo.setVideo_id((long) video_id);
            qiniuVideoMapper.Insert(qiniuVideo);
        }
        return qiniuVideo;
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
    public String getNextVideoUrl(Long id) {
        //通过上一个id，获取下一个视频url
        try {
            String nextVideoUrl = videoMapper.findNextVideoUrl(id);
            return nextVideoUrl;
        }
        catch (Exception e){
            return "视频不存在";
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


    // 通过类名获取下一个视频的url
    @Override
    public List<String> getClassVideoUrl(String className) {
        int class_id = getClassByName(className);
        List<String> videoUrls = videoMapper.findClassVideoUrls(class_id);
        return videoUrls;
    }

    //获取视频的状态
    @Override
    public int getStatus(Long id) {
        int status = videoMapper.getStatus(id);
        return status;
    }
}
