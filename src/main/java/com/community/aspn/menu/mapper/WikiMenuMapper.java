package com.community.aspn.menu.mapper;

import com.community.aspn.pojo.sys.CommunityMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface WikiMenuMapper {
    ArrayList<CommunityMenu> selectMenuByTier(Integer tier);
}
