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
    private String localAddr;
    private String remoteAddr;
    private String realAddr;
    private String patternAddr;
    private String localIpPrefix;


    /**
     * @Author nanguangjun
     * @Description //获取文件存储路径
     * @Date 16:36 2021/3/30
     * @Param [bucketName]
     * @return java.lang.String
     **/
    public String getFilePath(String bucketName){
        return this.getLocalAddr() + "/" + bucketName + "/";
    }

    /**
     * @Author nanguangjun
     * @Description // in the office environment
     * @Date 9:27 2021/4/9
     * @Param [bucketName]
     * @return java.lang.String
     **/
    public String getLocalFilePath(String bucketName){
        return this.getLocalAddr() + "/" + bucketName + "/";
    }

    /**
     * @Author nanguangjun
     * @Description //in the remote addr
     * @Date 9:29 2021/4/9
     * @Param [bucketName]
     * @return java.lang.String
     **/
    public String getRemoteFilePath(String bucketName){
        return this.getRemoteAddr() + "/" + bucketName + "/";
    }


}