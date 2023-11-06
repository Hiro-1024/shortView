package com.shortview.disposal.controller;

import com.shortview.disposal.dto.req.UserLoginReqDTO;
import com.shortview.disposal.dto.resp.UserLoginRespDTO;
import com.shortview.disposal.entity.Video;
import com.shortview.disposal.response.Result;
import com.shortview.disposal.service.Impl.VideoServiceImpl;
import com.shortview.disposal.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @Resource
    private VideoServiceImpl videoService;
    //登录功能
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseEntity<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        return ResponseEntity.ok(userService.login(userLoginReqDTO));
    }
    
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
