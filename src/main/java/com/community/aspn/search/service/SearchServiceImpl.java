package com.community.aspn.search.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.pojo.sys.Search;
import com.community.aspn.search.mapper.SearchMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SearchServiceImpl implements SearchService{

    @Resource
    SearchMapper searchMapper;

    /**
     * @Author nanguangjun
     * @Description // search content
     * @Date 9:11 2021/5/8
     * @Param [search]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> search(Search search) {
        String ty = search.getTy();
        List<Map<String, Object>> result = null;
        if("WIKI".equals(ty)){
            result = this.wikiSearch(search);
        }else if("COMMUNITY".equals(ty)){
            result = this.communitySearch(search);
        }
        this.insertSearch(search); //insert search
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // select search
     * @Date 10:39 2021/5/8
     * @Param [content]
     * @return java.util.List<com.community.aspn.pojo.sys.Search>
     **/
    @Override
    public List<Search> getSearchContent(String content) {
        QueryWrapper<Search> searchQueryWrapper = new QueryWrapper<>();
        searchQueryWrapper.like("content",content);
        return searchMapper.selectList(searchQueryWrapper);
    }


    /**
     * @Author nanguangjun
     * @Description // search insert
     * @Date 8:59 2021/5/8
     * @Param [search]
     * @return void
     **/
    private void insertSearch(Search search){
        String substring = UUID.randomUUID().toString().substring(0,7);
        String yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String id = yyyyMMddHHmm+substring;
        search.setId(id);
        search.setRegisterTime(new Date());
        try {
            searchMapper.insert(search);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * @Author nanguangjun
     * @Description // wiki Search
     * @Date 16:26 2021/5/7
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String,Object>> wikiSearch(Search search){
        List<Map<String, Object>> maps = searchMapper.wikiSearch(search.getContent());
        return maps;
    }

    /**
     * @Author nanguangjun
     * @Description // community 查询
     * @Date 16:36 2021/5/7
     * @Param [search]
     * @return java.util.List<com.community.aspn.pojo.community.Community>
     **/
    private List<Map<String,Object>> communitySearch(Search search){
        List<Map<String, Object>> maps = searchMapper.communitySearch(search.getContent());
        return maps;
    }



}
