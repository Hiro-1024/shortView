package com.shortview.disposal.controller;

import com.qiniu.util.Auth;
import com.shortview.disposal.configuration.QiniuConfiguration;
import com.shortview.disposal.service.DisposalService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 9:36
 * @Version 1.0
 */
@RestController
public class QiniuController {
    @Resource
    DisposalService disposalService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload() {
        String status = disposalService.upload();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
    @GetMapping("/download")
    public ResponseEntity<String> download() {
        disposalService.download();
        return new ResponseEntity<>("Download successful",HttpStatus.OK);
    }
}
