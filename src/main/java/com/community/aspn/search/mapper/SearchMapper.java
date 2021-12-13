package com.community.aspn.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.sys.Search;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper extends BaseMapper<Search> {
    List<Map<String,Object>> wikiSearch(@Param("content") String content,@Param("option") String option,
                                        @Param("menus") List<Integer> menus, @Param("members") List<Integer> members,@Param("page") Integer page,@Param("size") Integer size);
    List<Map<String,Object>> communitySearch(@Param("content") String content,@Param("option") String option,
                                             @Param("menus") List<Integer> menus, @Param("members") List<Integer> members,@Param("page") Integer page,@Param("size") Integer size);
}
