package com.shortview.disposal.controller;

import com.shortview.disposal.entity.Video;
import com.shortview.disposal.response.Result;
import com.shortview.disposal.service.Impl.VideoServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author wanghui
 * @Date 2023/11/4 0004 16:30
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class Userontroller {
    @Resource
    VideoServiceImpl videoService;

    //查看个人视频列表
    @RequestMapping(value = "/pervideos",method = RequestMethod.GET)
    public Result getPersonalVideos(@RequestParam("uid") Long uid) {
        List<Video> personalVideos = videoService.getPersonalVideos(uid);

        if (personalVideos != null && !personalVideos.isEmpty()) {
            return Result.ok().data(personalVideos);
        } else {
            return Result.error().message("个人视频列表为空");
        }
    }
}
