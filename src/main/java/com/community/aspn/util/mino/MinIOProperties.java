package com.community.aspn.util.mino;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinIOProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    public String getUrl(){
        return this.getEndpoint()+ "/" + this.getBucketName() + "/";
    }
}