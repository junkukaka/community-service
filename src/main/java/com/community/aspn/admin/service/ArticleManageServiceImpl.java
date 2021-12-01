package com.community.aspn.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.admin.mapper.ArticleManageMapper;
import com.community.aspn.pojo.wiki.WikiHis;
import com.community.aspn.util.mino.MinoIOComponent;
import com.community.aspn.wiki.mapper.WikiHisMapper;
import com.community.aspn.wiki.mapper.WikiMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleManageServiceImpl implements ArticleManageService{

    @Resource
    ArticleManageMapper articleManageMapper;

    @Resource
    MinoIOComponent minoIOComponent;

    @Resource
    WikiHisMapper wikiHisMapper;

    @Resource
    WikiMapper wikiMapper;


    /**
     * @Author nanguangjun
     * @Description // 커뮤니티 관리
     * @Date 14:32 2021/11/24
     * @Param [params]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public Map<String, Object> selectCommunities(Map<String, Object> params) {

        Map<String, Object> result = new HashMap<>(); //最后返回值
        Integer size = Integer.parseInt(params.get("itemsPerPage").toString());
        Integer page = Integer.parseInt(params.get("page").toString());
        Map<String, Object> totalMapArgs = new HashMap<>();
        if(params.get("searchTitle") != null){
            totalMapArgs.put("searchTitle",params.get("searchTitle"));
        }
        if(params.get("members") != null){
            totalMapArgs.put("members",params.get("members"));
        }
        Integer total = articleManageMapper.selectCommunitiesCnt(totalMapArgs); //select total
        int pages = total % size == 0 ? total / size : total / size + 1;

        //分页查询传参
        Map<String, Object> args = new HashMap<>();
        args.put("page", (page - 1) * size);
        args.put("size", size);
        if(params.get("searchTitle") != null){
            args.put("searchTitle",params.get("searchTitle"));
        }
        if(params.get("members") != null){
            args.put("members",params.get("members"));
        }
        List<Map<String, Object>> list = articleManageMapper.selectCommunities(args);

        result.put("communities", list); //数据
        result.put("page", page); //当前页面
        result.put("pages", pages); //总页数
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 관리 조회
     * @Date 15:38 2021/11/26
     * @Param [params]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> selectWikis(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>(); //最后返回值
        Integer size = Integer.parseInt(params.get("itemsPerPage").toString());
        Integer page = Integer.parseInt(params.get("page").toString());
        Map<String, Object> totalMapArgs = new HashMap<>();
        if(params.get("searchTitle") != null){
            totalMapArgs.put("searchTitle",params.get("searchTitle"));
        }
        if(params.get("members") != null){
            totalMapArgs.put("members",params.get("members"));
        }
        Integer total = articleManageMapper.selectWikisCnt(totalMapArgs); //select total
        int pages = total % size == 0 ? total / size : total / size + 1;

        //分页查询传参
        Map<String, Object> args = new HashMap<>();
        args.put("page", (page - 1) * size);
        args.put("size", size);
        if(params.get("searchTitle") != null){
            args.put("searchTitle",params.get("searchTitle"));
        }
        if(params.get("members") != null){
            args.put("members",params.get("members"));
        }
        List<Map<String, Object>> list = articleManageMapper.selectWikis(args);

        result.put("wikis", list); //数据
        result.put("page", page); //当前页面
        result.put("pages", pages); //总页数
        return result;
    }


    /**
     * @Author nanguangjun
     * @Description // 위키 history 조회
     * @Date 16:18 2021/11/29
     * @Param [wikiId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectWikiHis(Map<String,Object> params) {
        List<Map<String, Object>> result = articleManageMapper.selectWikiHis(Integer.parseInt(params.get("wikiId").toString()));
        //图片处理
        String p = "";
        for (int i = 0; i < result.size(); i++) {
            if(result.get(i).get("picture") != null){
                p = minoIOComponent.afterGetContentFromDBToFront(result.get(i).get("picture").toString(),
                        params.get("remoteAddr").toString());
                result.get(i).put("picture",p);
            }
        }
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // 우키 history 삭제
     * @Date 14:05 2021/11/30
     * @Param [id]
     * @return void
     **/
    @Override
    public void deleteWikiHis(Integer id) {
        wikiHisMapper.deleteById(id);
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 삭제
     * @Date 14:54 2021/11/30
     * @Param [id]
     * @return void
     **/
    @Override
    public void deleteWiki(Integer id) {
        QueryWrapper<WikiHis> wikiHisQueryWrapper = new QueryWrapper<>();
        wikiHisQueryWrapper.eq("wiki_id",id);
        //위키 his 삭제
        wikiHisMapper.delete(wikiHisQueryWrapper);
        //위키 삭제
        wikiMapper.deleteById(id);
    }
}
