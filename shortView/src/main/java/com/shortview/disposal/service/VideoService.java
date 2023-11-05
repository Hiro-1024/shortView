package com.shortview.disposal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shortview.disposal.entity.QiniuVideo;
import com.shortview.disposal.entity.Video;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 10:27
 * @Version 1.0
 */
public interface VideoService extends IService<Video> {

    //上传视频到七牛云存储中
    String uploadVideo(MultipartFile multipartFile,String className,String context,String title,int status);

    //获取下一个视频url,context,title
    List<Video> getNextVideoUrl(Long id);

    boolean remove(String bucket, String fileKey);

    List<String> getClassVideoInfo(String className);

    int getStatus(Long id);

    List<Video> searchVideos(String query);



    List<Video> getPersonalVideos(Long uid);
}
