package com.community.aspn.member.controller;

import com.community.aspn.pojo.member.Member;
import com.community.aspn.member.service.MemberService;
import com.community.aspn.pojo.member.MemberApp;
import com.community.aspn.util.AjaxResponse;
import com.community.aspn.util.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public @ResponseBody AjaxResponse getMemberById(@PathVariable int id,HttpServletRequest request){
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
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Member login = memberservice.checkSession(member,request);
            if(login != null){
                map.put("member",login);
                String token= TokenUtil.sign(login);
                map.put("token",token);
            }else {
                map.put("member", 0);
            }
        }catch (Exception e){
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
        try {
            Map<String, String> stringStringMap = memberservice.updateMember(member);
            return AjaxResponse.success(stringStringMap);
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description //获取所有用户
     * @Date 16:53 2021/2/23
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/members/getAllMemberByAdmin")
    public @ResponseBody AjaxResponse getAllMemberByAdmin(HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        if (request.getParameter("memberName") != null && !"".equals(request.getParameter("memberName").trim())){
            map.put("memberName",request.getParameter("memberName").trim());
        }
        map.put("itemsPerPage",Integer.parseInt(request.getParameter("itemsPerPage")));
        map.put("page",Integer.parseInt(request.getParameter("page")));
        map.put("department",request.getParameter("department"));
        map.put("authority",request.getParameter("authority"));
        Map<String, Object> result = memberservice.getAllMemberByAdmin(map);
        return AjaxResponse.success(result);
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

    /**
     * @Author nanguangjun
     * @Description //  member Verify 查看token的有效期
     * @Date 14:21 2021/7/2
     * @Param [token]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/memberVerify/{token}")
    public @ResponseBody AjaxResponse memberVerify(@PathVariable String token){
        int verify = TokenUtil.verify(token);
        return AjaxResponse.success(verify);
    }

    /**
     * @Author nanguangjun
     * @Description // 初始化密码 123456
     * @Date 8:23 2021/7/20
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/iniPassword")
    public @ResponseBody AjaxResponse iniPassword(@RequestBody Member member){
        try {
            memberservice.iniPassword(member.getId());
            return AjaxResponse.success();
        }catch (Exception e){
            System.out.println(e);
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description // 커뮤니티 위키 글 통계
     * @Date 16:17 2021/10/29
     * @Param [request]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/reportWCMemberCount")
    public @ResponseBody AjaxResponse reportWCMemberCount(HttpServletRequest httpServletRequest) throws Exception {
        String start = httpServletRequest.getParameter("startDate");
        String endDate = httpServletRequest.getParameter("endDate");
        String departments = httpServletRequest.getParameter("departments");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date end = simpleDateFormat.parse(endDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(calendar.DATE,1);
        end = calendar.getTime();
        HashMap<String, String> params = new HashMap<>();
        params.put("start",start);
        params.put("end",simpleDateFormat.format(end));
        params.put("departments",departments);
        List<Map<String, Object>> result = memberservice.reportWCMemberCount(params);
        return  AjaxResponse.success(result);
    }
    
    /**
     * @Author nanguangjun
     * @Description // 회원 이름으로 회원 리스트 조회 
     * @Date 10:51 2021/11/25
     * @Param [name, httpServletRequest]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/getMembersSearchByName/{name}")
    public @ResponseBody AjaxResponse getMembersSearchByName(@PathVariable String name,HttpServletRequest httpServletRequest){
        HashMap<String, String> params = new HashMap<>();
        params.put("name",name);
        params.put("remoteAddr",httpServletRequest.getRemoteAddr());
        List<Member> result = memberservice.getMembersSearchByName(params);
        return AjaxResponse.success(result);
    }

    /**
     * 평정 상세
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @GetMapping("/getRatingDetail")
    public @ResponseBody AjaxResponse getRatingDetail(HttpServletRequest httpServletRequest) throws Exception {
        HashMap<String, Object> params = new HashMap<>();
        String start = httpServletRequest.getParameter("startDate");
        String endDate = httpServletRequest.getParameter("endDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date end = simpleDateFormat.parse(endDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(calendar.DATE,1);
        end = calendar.getTime();
        params.put("start",start);
        params.put("end",simpleDateFormat.format(end));
        params.put("memberId",httpServletRequest.getParameter("memberId"));
        List<Map<String, Object>> result = memberservice.getRatingDetail(params);
        return  AjaxResponse.success(result);
    }

}
