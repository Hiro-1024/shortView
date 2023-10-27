package com.shortview.disposal.service.Impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.shortview.disposal.configuration.QiniuConfiguration;
import com.shortview.disposal.service.DisposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 10:28
 * @Version 1.0
 */
@Service
public class DisposalServiceImpl implements DisposalService {
    private  QiniuConfiguration qiniuConfiguration;
    @Autowired
    public DisposalServiceImpl(QiniuConfiguration qiniuConfiguration) {
        this.qiniuConfiguration = qiniuConfiguration;
    }
    // 获取配置数据
    String bucket = qiniuConfiguration.getBucket();
    String accessKey = qiniuConfiguration.getAccessKey();
    String secretKey = qiniuConfiguration.getSecretKey();
    Auth auth = Auth.create(accessKey, secretKey);


    //视频文件上传--文件上传表单上传的文件（MultipartFile）上传到七牛云存储
    @Override
    public String upload(@RequestParam("file") MultipartFile file) {

        // 1、构造一个带指定 Region 对象的配置类。指定存储区域，和存储空间选择的区域一致
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        // 2、认证
        // 认证通过后得到 token（令牌）认证凭证
        String upToken = auth.uploadToken(bucket);

        try {
            /**
             * 3、文件上传 localFilePath：文件路径
             * String key：文件名，
             * - 默认不指定key的情况下，以文件内容的hash值作为文件名
             * - 默认文件名存在桶中时，不覆盖
             * String token：认证凭证
             */
            String key = UUID.randomUUID() + "足球file.jpg";
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            // 4、解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println("文件名：" + putRet.key);
            System.out.println("文件内容的hash值：" + putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return upToken;
    }
    @Override
    public String uploadLocalVideo() {
        // 1、构造一个带指定 Region 对象的配置类。指定存储区域，和存储空间选择的区域一致
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);

        // 2、认证
        String upToken = auth.uploadToken(bucket);

        try {
            // 3、本地视频文件路径
            String localVideoFilePath = "D:/Videos/myvideo.mp4"; // 指定本地视频文件路径
            String key = UUID.randomUUID() + "myvideo.mp4"; // 指定文件名
            Response response = uploadManager.put(localVideoFilePath, key, upToken);
            // 4、解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println("文件名：" + putRet.key);
            System.out.println("文件内容的hash值：" + putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                throw new RuntimeException(ex);
            }
        }
        return upToken;
    }


    //视频文件下载
    @Override
    public List<String> download() {
        // 1、构造一个带指定 Region 对象的配置类。
        Configuration cfg = new Configuration(Region.huadong());
        // 2、认证
        BucketManager bucketManager = new BucketManager(auth, cfg);
        /**
         * 3、列举空间文件列表
         * String bucket：桶名
         * String prefix：文件名前缀
         * int limit：每次迭代的长度限制，最大1000，推荐值 1000
         * String delimiter：指定目录分隔符，列出所有公共前缀。缺省值为空字符串
         */
        String prefix = "";
        int limit = 1000;
        String delimiter = "";
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        List<String> videoInfoList = new ArrayList<>();
        while (fileListIterator.hasNext()) {
            // 处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                String info = item.key + ", " + item.hash + ", " + item.fsize + ", " + item.mimeType + ", " + item.putTime + ", " + item.endUser;
                videoInfoList.add(info);
            }
        }
        return videoInfoList;
    }
}
