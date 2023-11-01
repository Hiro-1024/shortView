package com.shortview.disposal.configuration;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.shortview.disposal.entity.QiniuVideo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 15:49
 * @Version 1.0
 */
@Component
public class QiniuConfiguration {
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.url}")
    private String url;

    //七牛文件上传管理器
    private UploadManager uploadManager;
    //七牛文件管理器
    private BucketManager bucketManager;
    // 七牛云认证工具
    private Auth auth;
    // 七牛云上传的token
    private String token;

    @PostConstruct
    private void init() {
        uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
        auth = Auth.create(accessKey, secretKey);
        bucketManager = new BucketManager(auth, new Configuration(Zone.autoZone()));
        token = auth.uploadToken(bucket);
    }

    //上传视频文件到七牛云存储
    public QiniuVideo uploadVideoQiniu(MultipartFile multipartFile) {
        try {
            // 1、获取文件上传的流
            byte[] fileBytes = multipartFile.getBytes();

            // 2、创建日期目录分隔
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());

            //解析为Data日期对象
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
            Date create_time = dateFormat1.parse(datePath);

            // 3、获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;

            // 获取文件扩展名
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 生成新的文件名
            String fileKey = UUID.randomUUID().toString().replace("-", "");
            String bucket = this.bucket;

            // 文件在bucket下的存储目录
            String path = datePath + "/" + fileKey + suffix;

            // 4.上传视频至七牛云
            uploadManager.put(fileBytes, path, token);

            // 创建QiniuVideo对象，赋值相关字段
            return new QiniuVideo(bucket, fileKey, url+"/"+path, path,create_time,originalFilename);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //删除存储中的视频
    public boolean removeVideo(String bucket,String path) {
        try {
            bucketManager.delete(bucket,path);
            return true;
        } catch (Exception e) {
            System.err.println( "发生异常"+ e.getMessage());
            return false;
        }
    }
}
