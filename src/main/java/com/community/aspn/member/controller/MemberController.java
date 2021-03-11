package com.community.aspn.member.controller;

import com.community.aspn.pojo.member.Member;
import com.community.aspn.member.service.MemberService;
import com.community.aspn.util.AjaxResponse;
import com.community.aspn.util.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
     * @Description // 获取用户
     * @Date 16:03 2021/1/7
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/members/{id}")
    public @ResponseBody AjaxResponse getmemberById(@PathVariable int id){
        Member memberById = memberservice.getMemberById(id);
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
    public @ResponseBody AjaxResponse logIn(@RequestBody Member member){
        Member login = memberservice.login(member);

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
    public @ResponseBody AjaxResponse updatemember(@RequestBody Member member){
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


}
