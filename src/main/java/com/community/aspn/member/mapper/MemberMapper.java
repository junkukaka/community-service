package com.community.aspn.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.aspn.pojo.member.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    List<Map<String,Object>> getDepartment();
    void updateMemberDynamic(Member member);
    List<Map<String,Object>> getAllAppMember();
    List<Map<String,Object>> getAllMemberByAdmin();
    List<Map<String,Object>> reportWCMemberCount(HashMap<String,String> params);
}
