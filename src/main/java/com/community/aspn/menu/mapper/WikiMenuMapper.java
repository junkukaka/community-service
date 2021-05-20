package com.community.aspn.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.sys.WikiMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface WikiMenuMapper extends BaseMapper<WikiMenu> {
    ArrayList<WikiMenu> selectMenuByTier(Integer tier);
    ArrayList<Integer> selectUnderFirstMenu (Integer menuId);
}
