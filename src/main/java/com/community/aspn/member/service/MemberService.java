package com.community.aspn.member.service;

import com.community.aspn.pojo.member.Member;
import com.community.aspn.pojo.member.MemberApp;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MemberService {
    Map<String, String> insertMember(Member member);

    Map<String, String> updateMember(Member member);

    Member getMemberById(Integer id, HttpServletRequest request);

    Member login(Member member, HttpServletRequest request);

    Member checkSession(Member member, HttpServletRequest request);

    List<Map<String, Object>> getDepartment();

    int changPassword(Map<String, Object> pw);

    Map<String, String> memberApplication(MemberApp memberApp);

    List<Map<String,Object>> getAllAppMember();

    List<Map<String,Object>> getAllMemberByAdmin();

    void appMemberToRealMember(List<Integer> ids);

    void appMemberDelete(List<Integer> ids);

    void iniPassword(Integer id);

    List<Map<String,Object>> reportWCMemberCount(HashMap<String,String> params);

    List<Member> getMembersSearchByName(HashMap<String,String> params);
}
