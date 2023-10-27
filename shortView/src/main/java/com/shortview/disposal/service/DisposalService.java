package com.shortview.disposal.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 10:27
 * @Version 1.0
 */
public interface DisposalService {
    //上传视频
    String upload(@RequestParam("file") MultipartFile file);
    String uploadLocalVideo();
    //下载视频
    List<String> download();
}
