package com.community.aspn.util.mino;

import io.minio.ObjectWriteResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;

/**
 * @Author nanguangjun
 * @Description // minIo上传服务
 * @Date 11:02 2021/2/4
 * @Param
 * @return
 **/
@Component
public class MinoIOComponent {
    @Resource
    MinIOTemplate minIOTemplate;

    @Resource
    MinIOProperties minIOProperties;


    /**
     * @Author nanguangjun
     * @Description // 把base64文件上传到 mino 并返回 文件地址
     * @Date 10:47 2021/2/4
     * @Param [base64Str]
     * @return java.lang.String url
     **/
    public String multipartFileToMino(String base64Str) throws Exception{
        MultipartFile multipartFile = MinIOFileUtil.base64MutipartFile(base64Str);
        String contentType = multipartFile.getContentType();
        InputStream inputStream = multipartFile.getInputStream();
        String communityFileName = MinIOFileUtil.getFileName(multipartFile.getOriginalFilename());
        ObjectWriteResponse objectWriteResponse = minIOTemplate.putObject(communityFileName,minIOProperties.getCommunityBucket(), inputStream, contentType);
        return minIOProperties.getFilePath(minIOProperties.getCommunityBucket()) + objectWriteResponse.object();
    }

    /**
     * @Author nanguangjun
     * @Description // community得内容里的base64字符串转换mino里的Url并返回内容
     * @Date 10:36 2021/2/4
     * @Param [content]
     * @return java.lang.String content
     **/
    public String base64RegexReplace(String content){
        Matcher matcher = MinIOFileUtil.PATTERN.matcher(content);
        String replaceStr = content;
        String base64Str = "";
        String url =" ";
        //循环匹配并操作
        while (matcher.find()){
            base64Str = matcher.group();
            try {
                url = multipartFileToMino(base64Str); //上传图片并返回url
            } catch (Exception e) {
                e.printStackTrace();
                url = "none";
            }
            replaceStr = replaceStr.replace(base64Str,url);
        }
        return replaceStr;
    }
}
