package com.community.aspn.util.mino;

import com.community.aspn.util.AjaxResponse;
import io.minio.ObjectWriteResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;


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
            String fileName = MinIOFileUtil.getCommunityFileName(multipartFile.getOriginalFilename());
            MultipartFile mpf = request.getFile(str);
            //获取文件类型 contentType
            String contentType = MinIOFileUtil.getContentType(multipartFile.getOriginalFilename());
            InputStream inputStream = mpf.getInputStream();
            //保存
            objectWriteResponse = minIOTemplate.putObject(fileName, inputStream, contentType);
            url = minIOProperties.getUrl() + objectWriteResponse.object();
        }
        return AjaxResponse.success(url);
    }


}