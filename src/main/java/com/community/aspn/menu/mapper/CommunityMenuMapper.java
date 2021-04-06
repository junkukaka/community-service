package com.community.aspn.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.sys.CommunityMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMenuMapper extends BaseMapper<CommunityMenu> {
    CommunityMenu selectMenuById2(Integer id);
    List<CommunityMenu> selectMenuByTier(Integer tier);
    List<CommunityMenu> selectByFather(Integer father);

}
