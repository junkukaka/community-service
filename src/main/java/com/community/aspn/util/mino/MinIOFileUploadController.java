package com.community.aspn.util.mino;

import com.community.aspn.util.AjaxResponse;
import io.minio.ObjectWriteResponse;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;


@RestController
@CrossOrigin(origins="*",maxAge=3600)
public class MinIOFileUploadController {


    @Resource
    MinIOTemplate minIOTemplate;

    @Resource
    MinIOProperties minIOProperties;

    /**
     * @Author nanguangjun
     * @Description //图片文件文件上传
     * @Date 14:06 2021/2/3
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/minio/upload")
    public @ResponseBody AjaxResponse uploadFile(MultipartHttpServletRequest request) throws Exception{
        Iterator<String> itr  = request.getFileNames();
        MultipartFile  multipartFile ;
        ObjectWriteResponse objectWriteResponse;
        String url = "";
        while (itr .hasNext()){
            String str = itr.next();
            multipartFile = request.getFile(str);
            //获取文件名称和存储路径
            String fileName = MinIOFileUtil.getFileName(multipartFile.getOriginalFilename());
            MultipartFile mpf = request.getFile(str);
            //获取文件类型 contentType
            String contentType = MinIOFileUtil.getContentType(multipartFile.getOriginalFilename());
            InputStream inputStream = mpf.getInputStream();
            //保存
            String bucket = minIOProperties.getCommunityBucket();
            objectWriteResponse = minIOTemplate.putObject(fileName,bucket, inputStream, contentType);
            url = minIOProperties.getFilePath(bucket) + objectWriteResponse.object();
        }
        return AjaxResponse.success(url);
    }

    /**
     * @Author nanguangjun
     * @Description // community Editor upload image
     * @Date 16:54 2021/3/30
     * @Param [file]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping(value = "/minio/vue_md_Editor")
    public @ResponseBody AjaxResponse communityVueMdEditorUploadImage(MultipartFile file) throws Exception{
        String url = "";
        String contentType = file.getContentType();
        String fileName = MinIOFileUtil.getFileName(file.getOriginalFilename());
        InputStream inputStream = file.getInputStream();
        String bucket = minIOProperties.getCommunityBucket();
        ObjectWriteResponse response = minIOTemplate.putObject(fileName,bucket, inputStream, contentType);
        url = minIOProperties.getFilePath(bucket) + response.object();
        return AjaxResponse.success(url);
    }


    /**
     * @Author nanguangjun
     * @Description // user upload picture
     * @Date 11:12 2021/2/25
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/minio/user")
    public @ResponseBody AjaxResponse uploadUserFile(MultipartHttpServletRequest request) throws Exception{
        Iterator<String> itr  = request.getFileNames();
        MultipartFile  multipartFile ;
        ObjectWriteResponse objectWriteResponse;
        String url = "";
        while (itr .hasNext()){
            String str = itr.next();
            multipartFile = request.getFile(str);
            //获取文件名称和存储路径
            String fileName = MinIOFileUtil.getFileName(multipartFile.getOriginalFilename());
            MultipartFile mpf = request.getFile(str);
            //获取文件类型 contentType
            String contentType = MinIOFileUtil.getContentType(multipartFile.getOriginalFilename());
            InputStream inputStream = mpf.getInputStream();
            //保存
            String sysBucket = minIOProperties.getSysBucket();
            objectWriteResponse = minIOTemplate.putObject(fileName, sysBucket, inputStream, contentType);
            url = minIOProperties.getFilePath(sysBucket) + objectWriteResponse.object();
        }
        return AjaxResponse.success(url);
    }


}