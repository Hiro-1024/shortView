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

import java.util.UUID;

/**
 * @Author wanghui
 * @Date 2023/10/25 0025 10:28
 * @Version 1.0
 */
@Service
public class DisposalServiceImpl implements DisposalService {
    @Autowired
    private QiniuConfiguration qiniuConfiguration;
    // 获取配置数据
    String bucket = qiniuConfiguration.getBucket();
    String accessKey = qiniuConfiguration.getAccessKey();
    String secretKey = qiniuConfiguration.getSecretKey();
    Auth auth = Auth.create(accessKey, secretKey);

    @Override
    public String upload() {

        // 1、构造一个带指定 Region 对象的配置类。指定存储区域，和存储空间选择的区域一致
        Configuration cfg = new Configuration(Region.region0());
        // ...其他参数参考类注释
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
            String localFilePath = "D:\\TempFiles\\足球11.jpg";
            String key = UUID.randomUUID() + "足球file.jpg";
            Response response = uploadManager.put(localFilePath, key, upToken);
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

    @Override
    public void download() {
        // 1、构造一个带指定 Region 对象的配置类。
        Configuration cfg = new Configuration(Region.huadong());
        // ...其他参数参考类注释
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
        while (fileListIterator.hasNext()) {
            // 处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                System.out.println(item.key);
                System.out.println(item.hash);
                System.out.println(item.fsize);
                System.out.println(item.mimeType);
                System.out.println(item.putTime);
                System.out.println(item.endUser);
            }
        }
    }
}
