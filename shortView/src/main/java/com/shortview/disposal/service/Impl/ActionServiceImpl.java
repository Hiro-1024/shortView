package com.shortview.disposal.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shortview.disposal.entity.Action;
import com.shortview.disposal.entity.Video;
import com.shortview.disposal.mapper.ActionMapper;
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


    //点赞动作
    @Override
    public Result likeVideo(Long userId, Long videoId) {
        // 检查是否已经点赞，防止重复点赞
        if (!actionMapper.existsByUserIdAndVideoId(userId, videoId)) {
            Action action = new Action();
            action.setUser_id(userId);
            action.setVideo_id(videoId);
            action.setAction_type("like");
            actionMapper.save(action);

            Video video = new Video();
            int likecount = getLike(videoId);
            video.setLikes(likecount);
            return Result.ok().message("点赞成功！");
        } else {
            return Result.error().message("您已经点赞过该视频");
        }
    }
    //统计点赞数
    private int getLike(Long videoId) {
        int count = actionMapper.getCount(videoId);
        return count;
    }

    //取消点赞动作
    @Override
    public void unlikeVideo(Long userId, Long videoId) {
        if (actionMapper.existsByUserIdAndVideoId(userId, videoId)){
            actionMapper.deleteByUserIdAndVideoId(userId, videoId);
        }
        else {
            throw new RuntimeException("未找到点赞记录，无法取消点赞。");
        }
    }
}
