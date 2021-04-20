package com.community.aspn.wiki.service;

import com.community.aspn.pojo.wiki.Wiki;
import com.community.aspn.pojo.wiki.WikiHis;
import com.community.aspn.util.mino.MinoIOComponent;
import com.community.aspn.wiki.mapper.WikiHisMapper;
import com.community.aspn.wiki.mapper.WikiMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WikiServiceImpl implements WikiService{

    @Resource
    WikiMapper wikiMapper;

    @Resource
    WikiHisMapper wikiHisMapper;

    @Resource
    MinoIOComponent minoIOComponent;

    /**
     * @Author nanguangjun
     * @Description // save wiki history and if it active is ture save wiki and active wikiHis
     * @Date 17:21 2021/4/19
     * @Param [params]
     * @return int
     **/
    @Override
    public WikiHis saveWikiHis(WikiHis wikiHis, Boolean flag) {
        Integer response = 0;
        WikiHis savedWikiHis = null;
        //if it has wikiHis ID update
        if(wikiHis.getId()!= null){
            wikiHis.setUpdateTime(new Date());
            wikiHis.setUpdateId(wikiHis.getMenuId());
            wikiHisMapper.updateById(wikiHis);
            savedWikiHis = wikiHis;
        }else {
            wikiHis.setRegisterId(wikiHis.getMenuId());
            wikiHis.setRegisterTime(new Date());
            Wiki wiki = this.insertWiki(wikiHis);// insert wiki before wikiHis
            wikiHis.setWikiId(wiki.getId()); // set wikiId from wiki master
            wikiHisMapper.insert(wikiHis); //save wikiHis
            savedWikiHis = wikiHis;
        }
        //active wiki his
        if(flag){ //if this is new wiki
            this.activeWikiHis(savedWikiHis);
        }
        return savedWikiHis;
    }

    /**
     * @Author nanguangjun
     * @Description // save wiki master
     * @Date 14:26 2021/4/20
     * @Param [wikiHis]
     * @return com.community.aspn.pojo.wiki.Wiki
     **/
    @Override
    public Wiki insertWiki(WikiHis wikiHis) {
        Wiki wiki = new Wiki();
        wiki.setMenuId(wikiHis.getMenuId());
        wiki.setPicture(wikiHis.getPicture());
        wiki.setTitle(wikiHis.getTitle());
        wiki.setHisId(wikiHis.getId());
        wiki.setRegisterTime(new Date());
        wiki.setRegisterId(wikiHis.getMemberId());
        Integer i = wikiMapper.addWiki(wiki);
        if(i >0){
            Integer id = wiki.getId();
            wiki.setId(id);
        }
        return wiki;

    }

    /**
     * @Author nanguangjun
     * @Description // active wikiHis
     * @Date 10:07 2021/4/20
     * @Param [wikiHis]
     * @return com.community.aspn.pojo.wiki.Wiki
     **/
    @Override
    public Wiki activeWikiHis(WikiHis wikiHis){
        Wiki wiki = new Wiki();
        Wiki wikiResponse = null;
        wiki.setTitle(wikiHis.getTitle());
        wiki.setMenuId(wikiHis.getMenuId());
        wiki.setHisId(wikiHis.getId());
        if(wikiHis.getPicture() != null ){
            wiki.setPicture(wikiHis.getPicture());
        }
        wikiHis.setHisYn(1);
        wikiHisMapper.updateById(wikiHis); //update wikiHis his_yn
        //if it has wiki id update wiki else new wiki
        if(wikiHis.getWikiId() != null){
            wiki.setId(wikiHis.getWikiId());
            wiki.setUpdateId(wikiHis.getMemberId());
            wiki.setUpdateTime(new Date());
            wikiMapper.updateById(wiki); //update wiki his_id
            wikiResponse = wikiMapper.selectById(wikiHis.getWikiId());
        }
        return wikiResponse;
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
    public List<Map<String, Object>> wikiList(Wiki wiki, String remoteAddr) {
        String p = "";
        List<Map<String, Object>> wikis = wikiMapper.selectWikiList(wiki);
        //图片处理
        for (int i = 0; i < wikis.size(); i++) {
            if(wikis.get(i).get("picture") != null){
                p = minoIOComponent.afterGetContentFromDBToFront(wikis.get(i).get("picture").toString(), remoteAddr);
                wikis.get(i).put("picture",p);
            }
        }
        return wikis;
    }

    /**
     * @Author nanguangjun
     * @Description // wiki main page
     * @Date 16:26 2021/4/21
     * @Param [count, remoteAddr]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> wikiMainList(Integer count, String remoteAddr) {
        List<Map<String, Object>> wikis = wikiMapper.selectWikiMainList(count);
        String p = "";
        //图片处理
        for (int i = 0; i < wikis.size(); i++) {
            if(wikis.get(i).get("picture") != null){
                p = minoIOComponent.afterGetContentFromDBToFront(wikis.get(i).get("picture").toString(), remoteAddr);
                wikis.get(i).put("picture",p);
            }
        }
        return wikis;
    }


    /**
     * @Author nanguangjun
     * @Description // wiki detail page return wikiHis
     * @Date 13:56 2021/4/21
     * @Param [id is wiki id, remoteAddr]
     * @return com.community.aspn.pojo.wiki.WikiHis
     **/
    @Override
    public WikiHis selectWikiDetail(Integer id, String remoteAddr) {
        Wiki wiki = wikiMapper.selectById(id);
        WikiHis wikiHis = wikiHisMapper.selectById(wiki.getHisId());
        String content = minoIOComponent.afterGetContentFromDBToFront(wikiHis.getContent(), remoteAddr);
        wikiHis.setContent(content);
        return wikiHis;
    }


}
