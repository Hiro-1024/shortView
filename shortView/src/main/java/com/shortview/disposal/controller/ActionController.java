package com.shortview.disposal.controller;

import com.shortview.disposal.response.Result;
import com.shortview.disposal.service.ActionService;
import com.shortview.disposal.service.Impl.VideoServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wanghui
 * @Date 2023/11/4 0004 16:50
 * @Version 1.0
 */
@RestController
@RequestMapping("/action")
public class ActionController {
    @Resource
    private ActionService actionService;

    //点赞
    @RequestMapping(value = "/like" ,method = RequestMethod.POST)
    public Result likeVideo(@RequestParam Long userId, @RequestParam Long videoId) {
        String result = actionService.likeVideo(userId, videoId);
        if (result.equals("")){
            return Result.ok().message("点赞成功");
        } else {
            return Result.error().message("点赞失败！").data(result);
        }
    }

    // 取消点赞
    @RequestMapping(value = "/unlike" ,method = RequestMethod.POST)
    public Result unlikeVideo(@RequestParam Long userId, @RequestParam Long videoId) {
        String result = actionService.unlikeVideo(userId, videoId);
        if (result.equals("")){
            return Result.ok().message("取消点赞成功！");
        } else  {
            return Result.error().message("取消点赞失败：").data(result);
        }
    }

}
