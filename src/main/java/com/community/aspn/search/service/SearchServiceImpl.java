package com.community.aspn.search.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.community.aspn.community.mapper.CommunityMapper;
import com.community.aspn.pojo.community.Community;
import com.community.aspn.pojo.sys.Search;
import com.community.aspn.pojo.sys.SearchHis;
import com.community.aspn.search.mapper.SearchHisMapper;
import com.community.aspn.search.mapper.SearchMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{

    @Resource
    SearchMapper searchMapper;

    @Resource
    SearchHisMapper searchHisMapper;


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
        this.insertSearchHis(search); //insert search his
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
     * @Description // search history insert
     * @Date 16:57 2021/5/7
     * @Param [search]
     * @return void
     **/
    private void insertSearchHis(Search search){
        SearchHis searchHis = new SearchHis();
        searchHis.setContent(search.getContent());
        searchHis.setTy(search.getTy());
        searchHis.setRegisterTime(new Date());
        searchHisMapper.insert(searchHis);
    }

    /**
     * @Author nanguangjun
     * @Description // search insert
     * @Date 8:59 2021/5/8
     * @Param [search]
     * @return void
     **/
    private void insertSearch(Search search){
        QueryWrapper<Search> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("content",search.getContent());
        queryWrapper.eq("ty",search.getTy());
        Search s = searchMapper.selectOne(queryWrapper);
        //if it search content is exist than update this search
        if(s != null){
            s.setUpdateTime(new Date());
            s.setUpdateId(1);
            searchMapper.updateSearch(s);
        }else { //if not exist than insert new search
            search.setCnt(0);
            searchMapper.insert(search);
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
