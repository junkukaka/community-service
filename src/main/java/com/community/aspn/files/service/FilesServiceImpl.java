package com.community.aspn.files.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.community.mapper.CommunityMapper;
import com.community.aspn.files.mapper.FilesMapper;
import com.community.aspn.pojo.community.Community;
import com.community.aspn.pojo.sys.Files;
import com.community.aspn.util.mino.MinIOFileUtil;
import com.community.aspn.util.mino.MinIOProperties;
import com.community.aspn.util.mino.MinIOTemplate;
import com.community.aspn.util.mino.MinoIOComponent;
import io.minio.ObjectWriteResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;


@Service
public class FilesServiceImpl implements FilesService {


    @Resource
    MinIOProperties minIOProperties;

    @Resource
    MinIOTemplate minIOTemplate;

    @Resource
    FilesMapper filesMapper;

    @Resource
    MinoIOComponent minoIOComponent;
    
    @Resource
    CommunityMapper communityMapper;

    /**
     * @Author nanguangjun
     * @Description //TODO 
     * @Date 14:23 2021/6/16
     * @Param [request]
     * @return java.util.List<com.community.aspn.pojo.sys.FilesItem>
     **/
    @Override
    public List<Files> uploadFile(MultipartHttpServletRequest request) {
        Iterator<String> itr  = request.getFileNames();
        MultipartFile multipartFile ;
        ObjectWriteResponse objectWriteResponse;
        System.out.println(request.getParameter("docId") );
        String docId = "".equals(request.getParameter("docId")) ? MinIOFileUtil.getUUID() : request.getParameter("docId");
        String url;
        List<Files> result = new ArrayList<>();
        while (itr.hasNext()){
            String str;
            str = itr.next();
            multipartFile = request.getFile(str);
            try {
                InputStream inputStream = multipartFile.getInputStream();
                //获取文件名称和存储路径
                String originalName = multipartFile.getOriginalFilename();
                String fileName = MinIOFileUtil.getFileName(originalName);
                if(this.isExistFile(docId,originalName)){
                    //获取文件类型 contentType
                    String contentType = MinIOFileUtil.getContentType(originalName);
                    String bucket = minIOProperties.getUploadBucket();
                    objectWriteResponse = minIOTemplate.putObject(fileName,bucket, inputStream, contentType);
                    url = minIOProperties.getFilePath(bucket) + objectWriteResponse.object();
                    Files files = this.insertFiles(url,originalName,docId,request.getParameter("flag"));
                    result.add(files);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * @Author nanguangjun
     * @Description // insert file path to DB
     * @Date 15:31 2021/6/16
     * @Param [url, originalName, docId]
     * @return void
     **/
    private Files insertFiles(String url,String originalName,String docId,String flag){
        Files files = new Files();
        files.setDocId(docId);
        files.setOriginalName(originalName);
        files.setFilePath(minoIOComponent.beForeFileSaveInDB(url));
        files.setFlag(flag);
        files.setRegisterTime(new Date());
        filesMapper.insert(files);
        files.setId(files.getId());
        return files;
    }

    /**
     * @Author nanguangjun
     * @Description // select if file is exist depends on doc_id and originalName
     * @Date 10:38 2021/6/17
     * @Param [docId, originalName]
     * @return java.lang.Boolean
     **/
    private Boolean isExistFile(String docId,String originalName){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doc_id",docId).eq("original_name",originalName);
        Integer integer = filesMapper.selectCount(queryWrapper);
        if(integer > 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * @Author nanguangjun
     * @Description // delete file by id
     * @Date 15:43 2021/6/17
     * @Param [id]
     * @return java.util.List<com.community.aspn.pojo.sys.Files>
     **/
    @Override
    public List<Files> deleteFileById(Integer id,String remoteAddr) {
        Files files = filesMapper.selectById(id);
        filesMapper.deleteById(id); //delete by id from db
        minoIOComponent.deleteFile(files.getFilePath()); // delete file from minio
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doc_id",files.getDocId());
        List<Files> list = filesMapper.selectList(queryWrapper);
        return this.getFilesListToFront(list,remoteAddr);
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 10:47 2021/6/17
     * @Param [docId]
     * @return java.util.List<com.community.aspn.pojo.sys.Files>
     **/
    @Override
    public List<Files> selectFilesList(String docId, String remoteAddr) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doc_id",docId);
        List<Files> files = filesMapper.selectList(queryWrapper);
        this.getFilesListToFront(files,remoteAddr);
        return files;
    }

    /**
     * @Author nanguangjun
     * @Description //
     * @Date 9:35 2021/6/18
     * @Param [docId, remoteAddr]
     * @return java.util.List<com.community.aspn.pojo.sys.Files>
     **/
    @Override
    public List<Files> getDetailPageList(Map<String,String> map, String remoteAddr) {
        List<Files> result = null;
        if ("C".equals(map.get("flag"))){
            Community community = communityMapper.selectById(Integer.parseInt(map.get("id")));
            if(community.getDocId() != null || !"".equals(community.getDocId())){
                List filesList = filesMapper.selectList(new QueryWrapper<Files>().eq("doc_id",community.getDocId()));
                result = this.getFilesListToFront(filesList,remoteAddr);
            }
        }
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // get file list to front
     * @Date 16:07 2021/6/17
     * @Param [files, remoteAddr]
     * @return java.util.List<com.community.aspn.pojo.sys.Files>
     **/
    private List<Files> getFilesListToFront(List<Files> files,String remoteAddr){
        for (int i = 0; i < files.size(); i++) {
            String filePath = files.get(i).getFilePath();
            String fromFilePath = minoIOComponent.afterGetContentFromDBToFront(filePath, remoteAddr);
            files.get(i).setFilePath(fromFilePath);
        }
        return files;
    }
}
