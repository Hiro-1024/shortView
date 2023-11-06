package com.shortview.disposal.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shortview.disposal.entity.Action;
import com.shortview.disposal.mapper.ActionMapper;
import com.shortview.disposal.mapper.UserMapper;
import com.shortview.disposal.mapper.VideoMapper;
import com.shortview.disposal.response.Result;
import com.shortview.disposal.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wanghui
 * @Date 2023/11/4 0004 17:12
 * @Version 1.0
 */
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action> implements ActionService {
    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;

    //点赞动作
    @Override
    public String likeVideo(Long userId, Long videoId) {
        // 检查是否存在 videoid 和 userid
        if (userMapper.checkUser(userId) == 0 || videoMapper.chenckVideo(videoId) == 0) {
            return "指定的用户或视频不存在";
        }
        if (actionMapper.existsByUserIdAndVideoId(userId, videoId) == 0) {
            Action action = new Action();
            action.setUser_id(userId);
            action.setVideo_id(videoId);
            action.setAction_type("like");
            actionMapper.save(action);

            updateVideoLikes(videoId);
            return "";
        } else {
            return "您已经点赞过该视频";
        }
    }


    private void updateVideoLikes(Long videoId) {
        videoMapper.updateLikeCount(videoId);
    }


    //取消点赞动作
    @Override
    public String unlikeVideo(Long userId, Long videoId) {
        if (actionMapper.existsByUserIdAndVideoId(userId, videoId) != 0){
            actionMapper.deleteByUserIdAndVideoId(userId, videoId);
            updateLoseVideoLikes(videoId);
            return "";
        }
        else {
            return "未找到点赞记录，无法取消点赞。";
        }
    }

    private void updateLoseVideoLikes(Long videoId) {
        videoMapper.updateLikeCountLose(videoId);
    }
}
