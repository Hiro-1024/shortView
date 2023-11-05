package com.shortview.disposal.controller;

import com.shortview.disposal.dto.req.UserLoginReqDTO;
import com.shortview.disposal.dto.resp.UserLoginRespDTO;
import com.shortview.disposal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        return ResponseEntity.ok(userService.login(userLoginReqDTO));
    }

}
