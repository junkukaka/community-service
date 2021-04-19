package com.community.aspn.member.service;

import com.community.aspn.pojo.member.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MemberService {
    public Map<String,String> insertMember(Member member);
    public Map<String,String> updateMember(Member member);
    public void deleteMember(Integer id);
    public Member getMemberById(Integer id,HttpServletRequest request);
    public Member login(Member member, HttpServletRequest request);
    public List<Member> getAllMember();
    public List<Map<String,Object>> getDepartment();
}
