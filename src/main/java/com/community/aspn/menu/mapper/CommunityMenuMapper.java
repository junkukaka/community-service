package com.community.aspn.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.sys.CommunityMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CommunityMenuMapper extends BaseMapper<CommunityMenu> {
    ArrayList<CommunityMenu> selectMenuByTier(Integer tier);
    ArrayList<Integer> selectUnderFirstMenu (Integer menuId);
    ArrayList<Map<String,Object>> getDashboardTier2(Integer menuId);
    ArrayList<Map<String,Object>> getDashboardTier3(Integer menuId);
}
