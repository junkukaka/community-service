package com.community.aspn.search.controller;

import com.community.aspn.pojo.sys.Search;
import com.community.aspn.search.service.SearchService;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    SearchService searchService;

    /**
     * @Author nanguangjun
     * @Description // comprehensiveSearch wiki , community and all
     * @Date 9:38 2021/5/8
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/comprehensive")
    public @ResponseBody AjaxResponse comprehensiveSearch(@RequestBody Map<String,Object> search){
        List<Map<String, Object>> result = searchService.search(search);
        return AjaxResponse.success(result);
    }



}
