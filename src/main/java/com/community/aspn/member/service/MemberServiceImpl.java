package com.community.aspn.member.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.pojo.member.Member;
import com.community.aspn.member.mapper.MemberMapper;
import com.community.aspn.util.mino.MinIOProperties;
import com.community.aspn.util.mino.MinoIOComponent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author nanguangjun
 * @Description //用户模块
 * @Date 9:22 2020/12/25
 * @Param
 * @return
 **/
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    MemberMapper memberMapper;

    @Resource
    MinoIOComponent minoIOComponent;

    /**
     * @Author nanguangjun
     * @Description // 注册 ， 新增用户使用
     * @Date 9:22 2020/12/25
     * @Param [Member]
     * @return int
     **/
    @Override
    public Map<String,String> insertMember(Member member) {
        Map<String, String> msg = checkMemberOne(member);
        //회원 핸드폰 메일 체크
        if("0".equals(msg.get("code"))){
            return msg;
        }
        member.setRegisterTime(new Date());
        memberMapper.insert(member);
        msg.put("code","1");
        msg.put("msg","회원가입 성공");
        return msg;
    }

    /**
     * @Author nanguangjun
     * @Description // 跟新用户信息
     * @Date 9:23 2020/12/25
     * @Param [Member]
     * @return int
     **/
    @Override
    public Map<String,String> updateMember(Member member) {
        member.setUpdateTime(new Date());
        //如果包含图片的话
        if(member.getPicture()!= null){
            String url = minoIOComponent.beForeFileSaveInDB(member.getPicture());
            member.setPicture(url);
        }
        memberMapper.updateMemberDynamic(member);
        Map<String, String> msg = new HashMap<>();
        msg.put("code","1");
        msg.put("msg","회원정보 수정 성공");
        return msg;
    }

    /**
     * @Author nanguangjun
     * @Description //회원 핸드폰 메일 체크
     * @Date 11:09 2021/2/19
     * @Param [Member]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public Map<String,String> checkMemberOne(Member member){
        Map<String, String> msg = new HashMap<>();
        //loginId 验证
        Integer loginId = memberMapper.selectCount(new QueryWrapper<Member>().eq("login_id", member.getLoginId()));
        if(loginId> 0){
            msg.put("code","0");
            msg.put("msg","중복된 login 입니다.");
            return msg;
        }

        //检查邮箱
        Integer email = memberMapper.selectCount(new QueryWrapper<Member>().eq("email", member.getEmail()));
        if(email> 0){
            msg.put("code","0");
            msg.put("msg","중복된 메일 주소 입니다.");
            return msg;
        }
        //检查手机
        Integer phone = memberMapper.selectCount(new QueryWrapper<Member>().eq("phone", member.getPhone()));
        if(phone> 0){
            msg.put("code","0");
            msg.put("msg","중복된 핸드폰 번호 입니다.");
            return msg;
        }
        return msg;
    }

    /**
     * @Author nanguangjun
     * @Description //删除用户
     * @Date 9:24 2020/12/25
     * @Param [id]
     * @return void
     **/
    @Override
    public void deleteMember(Integer id) {
        memberMapper.deleteById(id);
    }

    /**
     * @Author nanguangjun
     * @Description //获取用户信息
     * @Date 9:25 2020/12/25
     * @Param [id]
     * @return com.community.aspn.pojo.Member.Member
     **/
    @Override
    public Member getMemberById(Integer id,HttpServletRequest request) {
        Member member = memberMapper.selectById(id);
        if(member.getPicture() != null){
            String url = minoIOComponent.afterGetContentFromDBToFront(member.getPicture(),request.getRemoteAddr());
            member.setPicture(url);
        }
        return member;
    }

    /**
     * @Author nanguangjun
     * @Description // 用户登录
     * @Date 9:25 2020/12/25
     * @Param [Member]
     * @return com.community.aspn.pojo.Member.Member
     **/
    @Override
    public Member login(Member member, HttpServletRequest request) {
        QueryWrapper<Member> query = new QueryWrapper<>();
        //用户名，密码验证
        query.eq("login_id", member.getLoginId())
                .eq("password", member.getPassword());
        Member m = memberMapper.selectOne(query);
        if(m == null){
            return null;
        }
        if(m.getPicture() != null){
            String url = minoIOComponent.afterGetContentFromDBToFront(m.getPicture(), request.getRemoteAddr());
            m.setPicture(url);
        }
        return m;
    }

    /**
     * @Author nanguangjun
     * @Description // 获取所有用户
     * @Date 9:36 2020/12/25
     * @Param []
     * @return java.util.List<com.community.aspn.pojo.Member.Member>
     **/
    @Override
    public List<Member> getAllMember() {
        return memberMapper.selectList(null);
    }

    @Override
    public List<Map<String,Object>> getDepartment() {
        return memberMapper.getDepartment();
    }
}
