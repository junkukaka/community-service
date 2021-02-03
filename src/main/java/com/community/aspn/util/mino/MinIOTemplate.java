package com.community.aspn.util.mino;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Configuration
@EnableConfigurationProperties({MinIOProperties.class})
public class MinIOTemplate {

    private MinIOProperties minIo;

    public MinIOTemplate(MinIOProperties minIo) {
        this.minIo = minIo;
    }

    private MinioClient instance;

    @PostConstruct //minio操作对象实例化
    public void init() {
        instance = MinioClient.builder()
                .endpoint(minIo.getEndpoint())
                .credentials(minIo.getAccessKey(), minIo.getSecretKey())
                .build();
    }

    /**
     * 判断 bucket是否存在
     */
    public boolean bucketExists(String bucketName)
            throws IOException, InvalidKeyException, InvalidResponseException,
            InsufficientDataException, NoSuchAlgorithmException,
            ServerException, InternalException, XmlParserException,
             ErrorResponseException {

        return instance.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建 bucket
     */
    public void makeBucket(String bucketName) throws Exception{

        boolean isExist = this.bucketExists(bucketName);
        if(!isExist) {
            instance.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }
    }

    /**
     * 文件上传
     * @param bucketName  bucket名称
     * @param objectName 对象名称，文件名称
     * @param filepath 文件路径
     */
    public ObjectWriteResponse putObject(String bucketName, String objectName, String filepath)
            throws Exception {

        return instance.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(filepath).build());
    }

    /**
     * 文件上传
     * @param objectName 对象名称，文件名称
     * @param inputStream 文件输入流
     */
    public ObjectWriteResponse putObject(String objectName, InputStream inputStream)
            throws Exception {

        return instance.putObject(
                PutObjectArgs.builder()
                        .bucket(minIo.getBucketName())
                        .object(objectName).stream(
                        inputStream, -1, 10485760)
                        .build());
    }


    /**
     *
     * @param objectName 对象名称，文件名称
     * @param inputStream 文件输入流
     * @param contentTY 文件类型
     * @return
     * @throws Exception
     */
    public ObjectWriteResponse putObject( String objectName, InputStream inputStream,String contentTY) throws Exception{
        return instance.putObject(
                PutObjectArgs.builder()
                        .bucket(minIo.getBucketName())
                        .contentType(contentTY)
                        .object(objectName).stream(
                        inputStream, -1, 10485760)
                        .build());
    }

    /**
     * 删除文件
     * @param bucketName  bucket名称
     * @param objectName  对象名称
     */
    public void removeObject(String bucketName, String objectName)
            throws Exception {

        instance.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());

    }



}