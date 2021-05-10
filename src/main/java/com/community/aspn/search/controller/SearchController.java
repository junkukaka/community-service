package com.community.aspn.search.controller;

import com.community.aspn.pojo.sys.Search;
import com.community.aspn.search.service.SearchService;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public @ResponseBody AjaxResponse comprehensiveSearch(@RequestBody Search search){
        if( search.getContent() == null || "".equals(search.getContent()) ){
            return AjaxResponse.success(0);
        }else {
            return AjaxResponse.success(searchService.search(search));
        }
    }

    /**
     * @Author nanguangjun
     * @Description // get search content
     * @Date 9:46 2021/5/8
     * @Param [content]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/content/{content}")
    public @ResponseBody AjaxResponse getSearchContent(@PathVariable String content){
        if("".equals(content) || content == null){
            return AjaxResponse.success();
        }
        List<Search> search = searchService.getSearchContent(content);
        return AjaxResponse.success(search);
    }

}
