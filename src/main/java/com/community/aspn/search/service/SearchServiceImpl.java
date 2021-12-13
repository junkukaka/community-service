package com.community.aspn.search.service;


import com.community.aspn.pojo.sys.Search;
import com.community.aspn.search.mapper.SearchMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SearchServiceImpl implements SearchService{

    @Resource
    SearchMapper searchMapper;


    @Override
    public List<Map<String, Object>> search(Map<String,Object> search) {
        List<Map<String, Object>> result = null;
        String ty = search.get("ty").toString();
        if("WIKI".equals(ty)){
            result = this.wikiSearch(search);
        }else if("COMMUNITY".equals(ty)){
            result = this.communitySearch(search);
        }else {
            result = this.wikiAndCommunitySearch(search);
        }

        //검색 이력 table insert
        Search s = new Search();
        s.setContent(search.get("content").toString());
        s.setRegisterId(Integer.parseInt(search.get("registerId").toString()));
        this.insertSearch(s); //insert search
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // 위키와 커뮤니티 종합검색색
    * @Date 9:35 2021/12/10
     * @Param [search]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String, Object>> wikiAndCommunitySearch(Map<String, Object> search) {
        List<Map<String, Object>> wiki = this.wikiSearch(search);
        List<Map<String, Object>> community = this.communitySearch(search);
        List<Map<String, Object>> result = new ArrayList<>();
        result.addAll(wiki);
        result.addAll(community);
        return result;
    }


    /**
     * @Author nanguangjun
     * @Description // 위키 검색
     * @Date 16:26 2021/12/7
     * @Param [search]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String,Object>> wikiSearch(Map<String,Object> search){
        String content = search.get("content").toString();
        String option = search.get("option").toString();
        Integer page = Integer.parseInt(search.get("page").toString());
        Integer size = Integer.parseInt(search.get("size").toString());
        List<Integer> menus = this.castList(search.get("menus"),Integer.class);
        List<Integer> members = this.castList(search.get("members"),Integer.class);
        //如果会员和 content 同时没有值的话就返回null
        if("".equals(content) && members.size() == 0 ){
            return null;
        }
        List<Map<String, Object>> maps = searchMapper.wikiSearch(content,option,menus,members,page,size);
        return maps;
    }

    /**
     * @Author nanguangjun
     * @Description // community 查询
     * @Date 16:36 2021/5/7
     * @Param [search]
     * @return java.util.List<com.community.aspn.pojo.community.Community>
     **/
    private List<Map<String,Object>> communitySearch(Map<String,Object> search){
        String content = search.get("content").toString();
        String option = search.get("option").toString();
        Integer page = Integer.parseInt(search.get("page").toString());
        Integer size = Integer.parseInt(search.get("size").toString());
        List<Integer> menus = this.castList(search.get("menus"),Integer.class);
        List<Integer> members = this.castList(search.get("members"),Integer.class);
        //如果会员和 content 同时没有值的话就返回null
        if("".equals(content) && members.size() == 0 ){
            return null;
        }
        List<Map<String, Object>> maps = searchMapper.communitySearch(content,option,menus,members,page,size);
        return maps;
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

    private <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }



}
