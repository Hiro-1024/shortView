package com.shortview.disposal.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shortview.disposal.common.TokenGenerator;
import com.shortview.disposal.dto.req.UserLoginReqDTO;
import com.shortview.disposal.dto.resp.UserLoginRespDTO;
import com.shortview.disposal.entity.User;
import com.shortview.disposal.mapper.UserMapper;
import com.shortview.disposal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    @Override
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", userLoginReqDTO.getUsername());
        User user = userMapper.selectOne(userQueryWrapper);
        if(user.equals(userLoginReqDTO.getPassword())) {
            UserLoginRespDTO userLoginRespDTO = new UserLoginRespDTO();
            userLoginRespDTO.setUsername(user.getUsername());
            userLoginRespDTO.setToken(TokenGenerator.generateToken(user.getUsername()));
            return userLoginRespDTO;
        }
        return null;
    }
}
