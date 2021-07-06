package com.community.aspn.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.sys.Authority;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AuthorityMapper extends BaseMapper<Authority> {
    List<Map<String,Object>> getAuthorityCommunityList(Integer aId);
    List<Map<String,Object>> getAuthorityWikiList(Integer aId);
    List<Map<String,Object>> getDepartments();
}
