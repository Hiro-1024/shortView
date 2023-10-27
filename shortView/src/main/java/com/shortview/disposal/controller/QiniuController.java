package com.shortview.disposal.controller;

import com.shortview.disposal.service.DisposalService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 9:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/disposal")
public class QiniuController {
    @Resource
    DisposalService disposalService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    //前端表单上传视频
    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            // 上传视频并获取文件ID
            String videoId = disposalService.upload(file);
            // 返回文件ID和成功状态码
            return new ResponseEntity<>(videoId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // 返回失败状态码和错误信息
            return new ResponseEntity<>("上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //获取视频
    @GetMapping("/download")
    public ResponseEntity<List<String>> download() {
        List<String> videoList = disposalService.download();
        return new ResponseEntity<>(videoList,HttpStatus.OK);
    }

    //后端测试本地上传视频
    @PostMapping("/uploadLocal")
    public ResponseEntity<String> uploadLocalVideo() {
        String upToken = disposalService.uploadLocalVideo(); // 调用服务方法上传本地视频文件
        return ResponseEntity.ok("Video uploaded successfully. Token: " + upToken);
    }
}
