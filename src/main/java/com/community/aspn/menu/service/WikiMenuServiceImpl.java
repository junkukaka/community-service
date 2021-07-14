package com.community.aspn.menu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.authority.mapper.AuthorityWikiMapper;
import com.community.aspn.menu.mapper.WikiMenuMapper;
import com.community.aspn.pojo.sys.AuthorityCommunity;
import com.community.aspn.pojo.sys.AuthorityWiki;
import com.community.aspn.pojo.sys.WikiMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WikiMenuServiceImpl implements WikiMenuService {

    @Resource
    WikiMenuMapper wikiMenuMapper;

    @Resource
    AuthorityWikiMapper authorityWikiMapper;

    /**
     * @Author nanguangjun
     * @Description //新增菜单
     * @Date 15:59 2020/12/24
     * @Param [menu]
     * @return int
     **/
    @Override
    public int insertMenu(WikiMenu wikiMenu) {
        wikiMenu.setRegisterTime(new Date());
        int insert = wikiMenuMapper.insert(wikiMenu);
        return insert;
    }

    /**
     * @Author nanguangjun
     * @Description // 更新菜单
     * @Date 15:59 2020/12/24
     * @Param [menu]
     * @return int
     **/
    @Override
    public int updateMenu(WikiMenu wikiMenu) {
        wikiMenu.setUpdateTime(new Date());
        return wikiMenuMapper.updateById(wikiMenu);
    }

    /**
     * @Author nanguangjun
     * @Description //删除菜单
     * @Date 16:00 2020/12/24
     * @Param [id]
     *
     * @return void
     **/
    @Override
    public void deleteMenuById(Integer id) {
        wikiMenuMapper.deleteById(id);
    }

    /**
     * @Author nanguangjun
     * @Description //根据ID查询
     * @Date 16:00 2020/12/24
     * @Param [id]
     * @return com.community.aspn.pojo.sys.Menu
     **/
    @Override
    public WikiMenu getMenuById(Integer id) {
        return wikiMenuMapper.selectById(id);
    }


    /**
     * @Author nanguangjun
     * @Description //条件查询
     * @Date 14:07 2021/1/13
     * @Param [menu]
     * @return java.util.List<com.community.aspn.pojo.sys.Menu>
     **/
    @Override
    public List<WikiMenu> getMenusByCondition(WikiMenu wikiMenu) {
        QueryWrapper<WikiMenu> query = new QueryWrapper<>();
        //菜单等级查询
        if(wikiMenu.getTier() != null && !"".equals(wikiMenu.getTier())){
            query.eq("tier", wikiMenu.getTier()).orderByAsc("sort");
        //添加父目录条件
        }
        if(wikiMenu.getFather() != null && !"".equals(wikiMenu.getFather())){
            query.eq("father", wikiMenu.getFather()).orderByAsc("sort");
        //使用状态
        }
        if(wikiMenu.getUseYn() != null && !"".equals(wikiMenu.getUseYn())){
            query.eq("use_yn", wikiMenu.getUseYn()).orderByAsc("sort");
        }
        return wikiMenuMapper.selectList(query);
    }


    /**
     * @Author nanguangjun
     * @Description // MenuTree
     * @Date 9:51 2021/1/18
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String,Object>> getMenuTree(Integer authorityId){
        List<WikiMenu> first = wikiMenuMapper.selectMenuByTier(1);
        List<WikiMenu> second = wikiMenuMapper.selectMenuByTier(2);
        List<WikiMenu> third = wikiMenuMapper.selectMenuByTier(3);
        ArrayList<Integer> authorityMenus = this.getAuthorityMenus(authorityId);
        List<Map<String, Object>> getSecondTmpMenus = this.getSecondTmpMenus(second, third,authorityMenus);
        List<Map<String, Object>> finalMenuList = this.getFinalMenuList(first, getSecondTmpMenus,authorityMenus);
        return finalMenuList;
    }


    /**
     * @Author nanguangjun
     * @Description // get final menuList
     * @Date 9:31 2021/7/9
     * @Param [first, secondTmp]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String,Object>> getFinalMenuList(List<WikiMenu> first,
                                                      List<Map<String, Object>> secondTmp,List<Integer> authorityMenus){
        //最终菜单
        List<Map<String,Object>> finalList = new ArrayList<>();
        //1级菜单封装
        for (int i = 0; i < first.size(); i++) {
            //单个对象
            Map<String,Object> mapTmp2 = new HashMap<>();
            List<Map<String,Object>> sTem= new ArrayList<>(); //3级临时菜单
            int firstId = first.get(i).getId();
            //如果在权限里存在的话
            if(authorityMenus.contains(firstId)){
                for(Map<String,Object> s: secondTmp){
                    int father = (int) s.get("father");
                    if(firstId == father){
                        sTem.add(s);
                    }
                }
                if(sTem.size() > 0){
                    mapTmp2.put("children",sTem);
                }
                mapTmp2.put("id",firstId);
                mapTmp2.put("tier",first.get(i).getTier());
                mapTmp2.put("name",first.get(i).getName());
                mapTmp2.put("father",first.get(i).getFather());
                mapTmp2.put("sort",first.get(i).getSort());
                finalList.add(mapTmp2);
            }
        }
        return finalList;
    }

    /**
     * @Author nanguangjun
     * @Description // getSecond TmpMenus
     * @Date 9:26 2021/7/9
     * @Param [second, third]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    private List<Map<String,Object>> getSecondTmpMenus(List<WikiMenu> second,
                                                       List<WikiMenu> third,List<Integer> authorityMenus){
        //临时2级菜单
        List<Map<String,Object>> secondTmp = new ArrayList<>();
        //2级菜单封装
        for (int i = 0; i < second.size(); i++) {
            //单个对象
            Map<String,Object> mapTmp = new HashMap<>(); //2级临时菜单
            List<WikiMenu> thirdTem= new ArrayList<>(); //3级临时菜单
            int secondId = second.get(i).getId();
            //如果在权限里存在的话
            if(authorityMenus.contains(secondId)){
                for (int j = 0; j < third.size(); j++) {
                    int thirdFather = third.get(j).getFather();
                    if(secondId == thirdFather){
                        //如果在权限里存在的话
                        if(authorityMenus.contains(third.get(j).getId())){
                            thirdTem.add(third.get(j));
                        }
                    }
                }
                //在2级菜单添加 3级菜单
                if(thirdTem.size()>0){
                    mapTmp.put("children",thirdTem);
                }
                mapTmp.put("id",secondId);
                mapTmp.put("tier",second.get(i).getTier());
                mapTmp.put("name",second.get(i).getName());
                mapTmp.put("father",second.get(i).getFather());
                mapTmp.put("sort",second.get(i).getSort());
                secondTmp.add(mapTmp);
            }
        }
        return secondTmp;
    }

    /**
     * @Author nanguangjun
     * @Description // 获取Authority_community 里的menuId
     * @Date 9:20 2021/7/9
     * @Param [authorityId]
     * @return java.util.ArrayList<java.lang.Integer>
     **/
    @Override
    public ArrayList<Integer> getAuthorityMenus(Integer authorityId){
        QueryWrapper<AuthorityWiki> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a_id",authorityId);
        queryWrapper.eq("view_yn",1);
        List<AuthorityWiki> authorityWikis = authorityWikiMapper.selectList(queryWrapper);
        ArrayList<Integer> list = new ArrayList<>();
        for (AuthorityWiki authorityWiki : authorityWikis) {
            list.add(authorityWiki.getMenuId());
        }
        return list;
    }

    /**
     * @Author nanguangjun
     * @Description // get dashboard by menuId
     * @Date 14:05 2021/6/1
     * @Param [menuId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> getDashboard(Integer menuId) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        WikiMenu wikiMenu = wikiMenuMapper.selectById(menuId);
        if(wikiMenu.getTier() == 1){
            map.put("menuId",wikiMenu.getId());
            map.put("text",wikiMenu.getName());
            resultList.add(map);
            return resultList;
        }else if(wikiMenu.getTier() == 2){
            return wikiMenuMapper.getDashboardTier2(menuId);
        }else {
            return wikiMenuMapper.getDashboardTier3(menuId);
        }
    }
    
    
    /**
     * @Author nanguangjun
     * @Description // 获取所有菜单 
     * @Date 14:53 2021/7/9
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> getAllMenuTree() {
        List<WikiMenu> first = wikiMenuMapper.selectMenuByTier(1);
        List<WikiMenu> second = wikiMenuMapper.selectMenuByTier(2);
        List<WikiMenu> third = wikiMenuMapper.selectMenuByTier(3);
        //临时2级菜单
        List<Map<String,Object>> secondTmp = new ArrayList<>();
        //2级菜单封装
        for (int i = 0; i < second.size(); i++) {
            //单个对象
            Map<String,Object> mapTmp = new HashMap<>(); //2级临时菜单
            List<WikiMenu> thirdTem= new ArrayList<>(); //3级临时菜单
            int secondId = second.get(i).getId();
            for (int j = 0; j < third.size(); j++) {
                int thirdFather = third.get(j).getFather();
                if(secondId == thirdFather){
                    thirdTem.add(third.get(j));
                }
            }
            //在2级菜单添加 3级菜单
            if(thirdTem.size()>0){
                mapTmp.put("children",thirdTem);
            }
            mapTmp.put("id",secondId);
            mapTmp.put("tier",second.get(i).getTier());
            mapTmp.put("name",second.get(i).getName());
            mapTmp.put("father",second.get(i).getFather());
            mapTmp.put("sort",second.get(i).getSort());
            secondTmp.add(mapTmp);
        }
        //最终菜单
        List<Map<String,Object>> finalList = new ArrayList<>();
        //1级菜单封装
        for (int i = 0; i < first.size(); i++) {
            //单个对象
            Map<String,Object> mapTmp2 = new HashMap<>();
            List<Map<String,Object>> sTem= new ArrayList<>(); //3级临时菜单
            int firstId = first.get(i).getId();
            for(Map<String,Object> s: secondTmp){
                int father = (int) s.get("father");
                if(firstId == father){
                    sTem.add(s);
                }
            }
            if(sTem.size() > 0){
                mapTmp2.put("children",sTem);
            }
            mapTmp2.put("id",firstId);
            mapTmp2.put("tier",first.get(i).getTier());
            mapTmp2.put("name",first.get(i).getName());
            mapTmp2.put("father",first.get(i).getFather());
            mapTmp2.put("sort",first.get(i).getSort());
            finalList.add(mapTmp2);
        }

        return finalList;
    }

}
