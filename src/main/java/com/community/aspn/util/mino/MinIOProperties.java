package com.community.aspn.util.mino;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinIOProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String communityBucket;
    private String wikiBucket;
    private String sysBucket;
    private String path;


    /**
     * @Author nanguangjun
     * @Description //获取文件存储路径
     * @Date 16:36 2021/3/30
     * @Param [bucketName]
     * @return java.lang.String
     **/
    public String getFilePath(String bucketName){
        return this.getPath() + "/" + bucketName + "/";
    }
}