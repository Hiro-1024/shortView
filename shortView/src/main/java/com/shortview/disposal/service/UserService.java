package com.shortview.disposal.service;

import com.shortview.disposal.dto.req.UserLoginReqDTO;
import com.shortview.disposal.dto.resp.UserLoginRespDTO;

public interface UserService {
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);


}
