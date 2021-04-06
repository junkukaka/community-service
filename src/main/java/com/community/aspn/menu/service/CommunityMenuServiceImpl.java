package com.community.aspn.menu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.menu.mapper.CommunityMenuMapper;
import com.community.aspn.pojo.sys.CommunityMenu;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.*;

@Service
public class CommunityMenuServiceImpl implements CommunityMenuService {

    @Resource
    CommunityMenuMapper communityMenuMapper;

    
    /**
     * @Author nanguangjun
     * @Description //新增菜单 
     * @Date 15:59 2020/12/24
     * @Param [menu]
     * @return int
     **/
    @Override
    public int insertMenu(CommunityMenu communityMenu) {
        communityMenu.setRegisterTime(new Date());
        int insert = communityMenuMapper.insert(communityMenu);
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
    public int updateMenu(CommunityMenu communityMenu) {
        communityMenu.setUpdateTime(new Date());
        return communityMenuMapper.updateById(communityMenu);
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
        communityMenuMapper.deleteById(id);
    }
    
    /**
     * @Author nanguangjun
     * @Description //根据ID查询
     * @Date 16:00 2020/12/24
     * @Param [id]
     * @return com.community.aspn.pojo.sys.Menu
     **/
    @Override
    public CommunityMenu getMenuById(Integer id) {
        return communityMenuMapper.selectById(id);
    }


    /**
     * @Author nanguangjun
     * @Description //条件查询
     * @Date 14:07 2021/1/13
     * @Param [menu]
     * @return java.util.List<com.community.aspn.pojo.sys.Menu>
     **/
    @Override
    public List<CommunityMenu> getMenusByCondition(CommunityMenu communityMenu) {
        QueryWrapper<CommunityMenu> query = new QueryWrapper<>();
        //菜单等级查询
        if(communityMenu.getTier() != null && !"".equals(communityMenu.getTier())){
            query.eq("tier", communityMenu.getTier());
        //添加父目录条件
        }
        if(communityMenu.getFather() != null && !"".equals(communityMenu.getFather())){
            query.eq("father", communityMenu.getFather());
        //使用状态
        }
        if(communityMenu.getUseYn() != null && !"".equals(communityMenu.getUseYn())){
            query.eq("use_yn", communityMenu.getUseYn());
        }

        return communityMenuMapper.selectList(query);


    }


    /**
     * @Author nanguangjun
     * @Description // MenuTree
     * @Date 9:51 2021/1/18
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public List<Map<String,Object>> getMenuTree(){
        List<CommunityMenu> first = communityMenuMapper.selectMenuByTier(1);
        List<CommunityMenu> second = communityMenuMapper.selectMenuByTier(2);
        List<CommunityMenu> third = communityMenuMapper.selectMenuByTier(3);
        //临时2级菜单
        List<Map<String,Object>> secondTmp = new ArrayList<>();
        //2级菜单封装
        for (int i = 0; i < second.size(); i++) {
            //单个对象
            Map<String,Object> mapTmp = new HashMap<>(); //2级临时菜单
            List<CommunityMenu> thirdTem= new ArrayList<>(); //3级临时菜单
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
            finalList.add(mapTmp2);
        }

        return finalList;
    }

}
