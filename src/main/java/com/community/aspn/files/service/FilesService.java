package com.community.aspn.files.service;


import com.community.aspn.pojo.sys.Files;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

public interface FilesService {
    List<Files> uploadFile(MultipartHttpServletRequest request);
    List<Files> deleteFileById(Integer id,String remoteAddr);
    List<Files> selectFilesList(String docId,String remoteAddr);
    List<Files> getDetailPageList(Map<String,String> map, String remoteAddr);
}
