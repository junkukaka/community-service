package com.community.aspn.member.controller;

import com.community.aspn.pojo.member.Member;
import com.community.aspn.member.service.MemberService;
import com.community.aspn.pojo.member.MemberApp;
import com.community.aspn.util.AjaxResponse;
import com.community.aspn.util.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
//@CrossOrigin(origins="*",maxAge=3600)
public class MemberController {

    @Resource
    MemberService memberservice;

    /**
     * @Author nanguangjun
     * @Description // 会员注册
     * @Date 16:03 2021/1/7
     * @Param [member]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/members")
    public @ResponseBody AjaxResponse insertmember(@RequestBody Member member){
        Map<String, String> stringStringMap = memberservice.insertMember(member);
        return AjaxResponse.success(stringStringMap);
    }

    /**
     * @Author nanguangjun
     * @Description // member application
     * @Date 9:32 2021/5/25
     * @Param [memberApp]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/memberApplication")
    public @ResponseBody AjaxResponse memberApplication(@RequestBody MemberApp memberApp){
        try {
            return AjaxResponse.success(memberservice.memberApplication(memberApp));
        } catch (Exception e){
            return AjaxResponse.error(memberservice.memberApplication(memberApp));
        }
    }


    /**
     * @Author nanguangjun
     * @Description // 获取用户
     * @Date 16:03 2021/1/7
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/members/{id}")
    public @ResponseBody AjaxResponse getmemberById(@PathVariable int id,HttpServletRequest request){
        Member memberById = memberservice.getMemberById(id,request);
        memberById.setPassword(null);
        return AjaxResponse.success(memberById);
    }

    /**
     * @Author nanguangjun
     * @Description //会员登录
     * @Date 16:52 2021/2/23
     * @Param [member]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/members/login")
    public @ResponseBody AjaxResponse logIn(@RequestBody Member member, HttpServletRequest request){
        Member login = memberservice.login(member,request);
        Map<String, Object> map = new HashMap<>();
        if(login != null){
            map.put("member",login);
            String token= TokenUtil.sign(login);
            map.put("token",token);
        }else {
            map.put("member", 0);
        }
        return AjaxResponse.success(map);
    }


    /**
     * @Author nanguangjun
     * @Description // check session
     * @Date 13:44 2021/5/10
     * @Param [member, request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/members/checkSession")
    public @ResponseBody AjaxResponse checkSession(@RequestBody Member member, HttpServletRequest request){
        Member login = memberservice.checkSession(member,request);
        Map<String, Object> map = new HashMap<>();
        if(login != null){
            map.put("member",login);
            String token= TokenUtil.sign(login);
            map.put("token",token);
        }else {
            map.put("member", 0);
        }
        return AjaxResponse.success(map);
    }

    /**
     * @Author nanguangjun
     * @Description //获取部门
     * @Date 16:52 2021/2/23
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/members/department")
    public @ResponseBody AjaxResponse getDepartment(){
        return AjaxResponse.success(memberservice.getDepartment());
    }

    /**
     * @Author nanguangjun
     * @Description //修改用戶
     * @Date 16:53 2021/2/23
     * @Param [member]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/members")
    public @ResponseBody AjaxResponse updateMember(@RequestBody Member member){
        Map<String, String> stringStringMap = memberservice.updateMember(member);
        return AjaxResponse.success(stringStringMap);
    }

    /**
     * @Author nanguangjun
     * @Description //获取所有用户
     * @Date 16:53 2021/2/23
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/members/getAll")
    public @ResponseBody AjaxResponse getAllmembers(){
        List<Member> allMember = memberservice.getAllMember();
        return AjaxResponse.success(allMember);
    }

    /**
     * @Author nanguangjun
     * @Description // 비밀번호 수정
     * @Date 13:24 2021/5/26
     * @Param [pw]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/members/password")
    public @ResponseBody AjaxResponse passwordChang(@RequestBody Map<String,Object> pw){
        int i = memberservice.changPassword(pw);
        return AjaxResponse.success(i);
    }

    /**
     * @Author nanguangjun
     * @Description //get member application
     * @Date 13:30 2021/5/26
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/getAllAppMember")
    public @ResponseBody AjaxResponse getAllAppMember(){
        List<Map<String, Object>> allAppMember = memberservice.getAllAppMember();
        return AjaxResponse.success(allAppMember);
    }

    /**
     * @Author nanguangjun
     * @Description // 신청회원을 회원으로 변경 요청
     * @Date 10:34 2021/5/27
     * @Param [ids]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/appMemberToRealMember")
    public @ResponseBody AjaxResponse appMemberToRealMember(@RequestBody List<Integer> ids){
        if (ids.size() == 0){
            return AjaxResponse.error();
        }
        memberservice.appMemberToRealMember(ids);
        return AjaxResponse.success();
    }

    /**
     * @Author nanguangjun
     * @Description // 신청회원을 삭제
     * @Date 13:45 2021/5/27
     * @Param [ids]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/appMemberDelete")
    public @ResponseBody AjaxResponse appMemberDelete(@RequestBody List<Integer> ids){
        if (ids.size() == 0){
            return AjaxResponse.error();
        }
        memberservice.appMemberDelete(ids);
        return AjaxResponse.success();
    }



}
