package com.community.aspn.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.sys.CommunityMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CommunityMenuMapper extends BaseMapper<CommunityMenu> {
    CommunityMenu selectMenuById2(Integer id);
    ArrayList<CommunityMenu> selectMenuByTier(Integer tier);
    ArrayList<CommunityMenu> selectByFather(Integer father);

}
