package com.community.aspn.util.mino;

import io.minio.ObjectWriteResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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



    /**
     * @Author nanguangjun
     * @Description // 包含图片信息的内容保存到数据库之前处理，把图片的IP地址替换成占位符。
     * 占位符：minio.patterAddr
     * @Date 16:04 2021/4/8
     * @Param [content, ip]
     * @return java.lang.String
     **/
    public String beForeFileSaveInDB(String content){
        String pattern = minIOProperties.getPatternAddr();
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(content);
        String replace = content;
        while (matcher.find()){
            String group = matcher.group();
            replace = replace.replace(group, minIOProperties.getRealAddr());
            System.out.println(replace);
        }
        return replace;
    }

    /**
     * @Author nanguangjun
     * @Description // 在Editor 页面 插入图片以后展示给前端
     * @Date 16:04 2021/4/8
     * @Param [content, ip]
     * @return java.lang.String
     **/
    public String afterMinIOSave(String content,String ip){
        String pattern = minIOProperties.getPatternAddr();
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(content);
        String replaced = "";
        //if ip is local addr  getLocalIpPrefix : 192.168
        if(ip.substring(0,7).equals(minIOProperties.getLocalIpPrefix())){
            while (matcher.find()){
                String group = matcher.group();
                replaced = content.replace(group, minIOProperties.getLocalAddr());
            }
        }else { //ip is remoteAddr
            while (matcher.find()) {
                String group = matcher.group();
                replaced = content.replace(group, minIOProperties.getRemoteAddr());
            }
        }
        return replaced;
    }

    /**
     * @Author nanguangjun
     * @Description //从数据库取出包含图片的内容发送给前端
     * @Date 10:16 2021/4/9
     * @Param [content, ip]
     * @return java.lang.String
     **/
    public String afterGetContentFromDBToFront(String content,String ip){
        String replaced = "";
        //if ip is local addr  getLocalIpPrefix : 192.168
        if(ip.substring(0,7).equals(minIOProperties.getLocalIpPrefix())){
            replaced = content.replace(minIOProperties.getRealAddr(),minIOProperties.getLocalAddr());
        }else { //ip is remoteAddr
            replaced = content.replace(minIOProperties.getRealAddr(),minIOProperties.getRemoteAddr());
        }
        return replaced;
    }


}
