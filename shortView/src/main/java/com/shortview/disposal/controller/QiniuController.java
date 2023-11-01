package com.shortview.disposal.controller;

import com.shortview.disposal.entity.QiniuVideo;
import com.shortview.disposal.response.Response;
import com.shortview.disposal.service.Impl.VideoServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //上传视频到云存储
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Response> uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam("className") String className, @RequestParam("context") String context, @RequestParam("title") String title,@RequestParam("status") int status) {
        log.info("执行了上传视频方法");
        QiniuVideo path = videoService.uploadVideo(file, className, context, title,status);
        if (path != null) {
            log.info("七牛云返回路径" + path);
            Response response = new Response();
            response.setSuccess(true);
            response.setMessage("上传成功");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Response response = new Response();
            response.setSuccess(false);
            response.setMessage("上传失败");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //删除视频
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<Response> removeVideo(@RequestBody QiniuVideo qiniuVideo) {
        // 根据存储服务 SDK，执行删除视频的操作
        boolean result = videoService.remove(qiniuVideo.getBucket(), qiniuVideo.getPath());
        Response response = new Response();
        if (result) {
            // 删除成功
            response.setSuccess(true);
            response.setMessage("删除成功");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // 删除失败
            response.setSuccess(false);
            response.setMessage("删除失败,视频不存在！");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //获取下一个视频
    @RequestMapping(value = "/nextVideo/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getNextVideo(@PathVariable Long id) {
        String nextVideoUrl = videoService.getNextVideoUrl(id);

        if (nextVideoUrl != null) {
            Map<String, String> response = new HashMap<>();
            int status = videoService.getStatus(id + 1);
            if (status != 0){
                response.put("videoUrl", nextVideoUrl);
            } else {
                response.put("error","没有视频访问权限");
            }

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "视频到底了: "));

        }
    }

    //根据类名分类获取视频
    @RequestMapping(value = "/classVideo", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> classGetVideo(@RequestParam("className") String className) {
        List<String> videoUrl = videoService.getClassVideoUrl(className);
        Map<String, Object> response = new HashMap<>();
        if (videoUrl != null && !videoUrl.isEmpty()) {
            response.put("urls", videoUrl);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "该类查找不到相关视频");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
