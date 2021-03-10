package com.community.aspn.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.sys.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    Menu selectMenuById2(Integer id);
    List<Menu> selectMenuByTier(Integer tier);
    List<Menu> selectByFather(Integer father);

}
