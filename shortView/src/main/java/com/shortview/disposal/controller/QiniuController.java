package com.shortview.disposal.controller;

import com.shortview.disposal.entity.Video;
import com.shortview.disposal.response.Result;
import com.shortview.disposal.service.Impl.QiniuVideoServiceImpl;
import com.shortview.disposal.service.Impl.VideoServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 9:36
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/video")
public class QiniuController {
    @Resource
    VideoServiceImpl videoService;
    @Resource
    QiniuVideoServiceImpl qiniuVideoService;

    //上传视频到云存储
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam("className") String className, @RequestParam("context") String context, @RequestParam("title") String title, @RequestParam("status") int status) {
        log.info("执行了上传视频方法");
        try {
            // 尝试上传视频
            String path = videoService.uploadVideo(file, className, context, title, status);
            if (path != null) {
                return Result.ok().data(path).message("上传成功！");
            } else {
                throw new Exception("上传失败的错误消息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("上传失败：" + e.getMessage());
        }
    }


    //删除视频
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result removeVideo(@RequestParam("id") Long id) {
        // 根据存储服务 SDK，执行删除视频的操作
        String bucket = qiniuVideoService.getBucketById(id);
        String path = qiniuVideoService.getPathById(id);
        boolean result = videoService.remove(bucket, path);
        if (result) {
            // 删除成功
            return Result.ok().data(result);
        } else {
            // 删除失败
            return Result.error().message("删除失败！");
        }
    }


    //获取下一个视频
    @RequestMapping(value = "/nextVideo/{id}", method = RequestMethod.GET)
    public Result getNextVideo(@PathVariable Long id) {
        List<Video> nextVideoUrl = videoService.getNextVideoUrl(id);

        if (nextVideoUrl != null && !nextVideoUrl.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            int status = videoService.getStatus( videoService.getNextId(id));
            if (status != 0) {
                // 有权限，返回下一个视频的URL
                return Result.ok().data(nextVideoUrl.get(0).getUrl()).message("videoUrl");
            } else {
                // 没有权限，返回412状态码和错误信息
                return Result.error().message("没有视频访问权限");
            }
        } else {
            // 没有找到下一个视频，返回412状态码和错误信息
            return Result.error().message("视频到底了!");

        }
    }


    //根据类名分类获取视频
    @RequestMapping(value = "/classVideo", method = RequestMethod.GET)
    public Result classGetVideo(@RequestParam("className") String className) {
        List<String> videoUrl = videoService.getClassVideoInfo(className);
        if (videoUrl != null && !videoUrl.isEmpty()) {
            return Result.ok().data(videoUrl);
        } else {
            return Result.error().message("该类查找不到相关视频");
        }
    }

    // 搜索视频接口
    @GetMapping("/search")
    public Result searchVideos(@RequestParam("query") String query) {
        List<Video> searchResults = videoService.searchVideos(query);
        if (!searchResults.isEmpty()) {
            // 返回搜索结果列表
            return Result.ok().data(searchResults);
        } else {
            // 返回 204
            return Result.error().message("搜索结果不存在");

        }
    }
}
