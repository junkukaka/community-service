package com.community.aspn.files.controller;


import com.community.aspn.files.service.FilesService;
import com.community.aspn.pojo.sys.Files;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FilesController {

    @Resource
    FilesService filesService;

    /**
     * @Author nanguangjun
     * @Description // 上传文件
     * @Date 14:15 2021/6/16
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @RequestMapping("/upload")
    public @ResponseBody AjaxResponse uploadFile(MultipartHttpServletRequest request){
        List<Files> files;
        try {
            files = filesService.uploadFile(request);
        }catch (Exception e){
            files = null;
            e.printStackTrace();
        }
        return AjaxResponse.success(files);
    }

    /**
     * @Author nanguangjun
     * @Description // select files by doc_id
     * @Date 10:46 2021/6/17
     * @Param [docId]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/list/{docId}")
    public @ResponseBody AjaxResponse selectFilesList(@PathVariable String docId, HttpServletRequest request){
        List<Files> files = filesService.selectFilesList(docId,request.getRemoteAddr());
        return AjaxResponse.success(files);
    }

    /**
     * @Author nanguangjun
     * @Description // 删除文件
     * @Date 14:15 2021/6/16
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/delete/{id}")
    public @ResponseBody AjaxResponse deleteFileById(@PathVariable Integer id, HttpServletRequest request){
        List<Files> files = filesService.deleteFileById(id,request.getRemoteAddr());
        return AjaxResponse.success(files);
    }

    @GetMapping("/getDetailPageList")
    public @ResponseBody AjaxResponse getDetailPageList(HttpServletRequest request){
        String id = request.getParameter("id");
        String flag = request.getParameter("flag");
        Map<String, String> map = new HashMap<>();
        map.put("id",id);
        map.put("flag",flag);
        List<Files> result = filesService.getDetailPageList(map, request.getRemoteAddr());
        return AjaxResponse.success(result);
    }

}
