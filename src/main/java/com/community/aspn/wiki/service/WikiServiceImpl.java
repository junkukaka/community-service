package com.community.aspn.wiki.service;

import com.community.aspn.pojo.wiki.Wiki;
import com.community.aspn.pojo.wiki.WikiHis;
import com.community.aspn.wiki.mapper.WikiHisMapper;
import com.community.aspn.wiki.mapper.WikiMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class WikiServiceImpl implements WikiService{

    @Resource
    WikiMapper wikiMapper;

    @Resource
    WikiHisMapper wikiHisMapper;

    /**
     * @Author nanguangjun
     * @Description // save wiki or wiki history
     * @Date 17:21 2021/4/19
     * @Param [params]
     * @return int
     **/
    @Override
    public int saveWiki(Map<String,Object> params) {
        return 0;
    }

    /**
     * @Author nanguangjun
     * @Description // select wiki history by wiki history_id
     * @Date 17:29 2021/4/19
     * @Param [wikiHis]
     * @return com.community.aspn.pojo.wiki.WikiHis
     **/
    @Override
    public WikiHis selectWikiHisByID(WikiHis wikiHis) {
        return null;
    }

    /**
     * @Author nanguangjun
     * @Description //wiki list select
     * @Date 17:20 2021/4/19
     * @Param [params]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Wiki> wikiList(Wiki wiki,HttpServletRequest request) {
        return null;
    }

    /**
     * @Author nanguangjun
     * @Description // main page wiki list select
     * @Date 17:26 2021/4/19
     * @Param [params, request]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Wiki> wikiListMain(Map<String, Object> params, HttpServletRequest request) {
        return null;
    }

    /**
     * @Author nanguangjun
     * @Description // select wiki by id
     * @Date 17:22 2021/4/19
     * @Param [wiki]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> selectWikiDetail(Wiki wiki, HttpServletRequest request) {
        return null;
    }
}
