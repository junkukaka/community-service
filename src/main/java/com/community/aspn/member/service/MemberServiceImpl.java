package com.community.aspn.member.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.authority.mapper.DepartmentMapper;
import com.community.aspn.mail.service.ScheduleMailComponent;
import com.community.aspn.member.mapper.MemberAppMapper;
import com.community.aspn.pojo.member.Member;
import com.community.aspn.member.mapper.MemberMapper;
import com.community.aspn.pojo.member.MemberApp;
import com.community.aspn.pojo.sys.Department;
import com.community.aspn.util.mino.MinoIOComponent;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @Resource
    MemberAppMapper memberAppMapper;

    @Resource
    DepartmentMapper departmentMapper;

    @Resource
    ScheduleMailComponent scheduleMailComponent;

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
        //use spring md5
        member.setPassword(DigestUtils.md5DigestAsHex(member.getPassword().getBytes()));
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
        member.setPassword(null);
        member.setUpdateTime(new Date());
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
        Integer loginIdMember = memberMapper.selectCount(new QueryWrapper<Member>().eq("login_id", member.getLoginId()));
        Integer loginIdApp = memberAppMapper.selectCount(new QueryWrapper<MemberApp>().eq("login_id", member.getLoginId()));
        Integer login = loginIdMember + loginIdApp;
        if(login > 0){
            msg.put("code","0");
            msg.put("msg","중복된 login 입니다.");
            return msg;
        }

        //检查邮箱
        Integer emailMember = memberMapper.selectCount(new QueryWrapper<Member>().eq("email", member.getEmail()));
        Integer emailApp = memberAppMapper.selectCount(new QueryWrapper<MemberApp>().eq("email", member.getEmail()));
        Integer email = emailMember + emailApp;
        if(email> 0){
            msg.put("code","0");
            msg.put("msg","중복된 메일 주소 입니다.");
            return msg;
        }
        //检查手机
        Integer phoneMember = memberMapper.selectCount(new QueryWrapper<Member>().eq("phone", member.getPhone()));
        Integer phoneApp = memberAppMapper.selectCount(new QueryWrapper<MemberApp>().eq("phone", member.getPhone()));
        Integer phone = phoneMember + phoneApp;
        if(phone> 0){
            msg.put("code","0");
            msg.put("msg","중복된 핸드폰 번호 입니다.");
            return msg;
        }
        return msg;
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
        String pw = DigestUtils.md5DigestAsHex(member.getPassword().getBytes());
        query.eq("login_id", member.getLoginId())
                .eq("password", pw)
                .eq("status","ON");
        Member m = getMember(request, query);
        if(m != null){
            this.updateLoginTime(m);
        }
        return m;
    }

    /**
     * @Author nanguangjun
     * @Description // update member login time
     * @Date 8:48 2021/6/4
     * @Param [member]
     * @return void
     **/
    private void updateLoginTime(Member member){
        Member m = new Member();
        m.setUpdateTime(new Date());
        m.setUpdateId(member.getId());
        m.setId(member.getId());
        memberMapper.updateById(m);
    }

    /**
     * @Author nanguangjun
     * @Description // check session
     * @Date 13:43 2021/5/10
     * @Param [member, request]
     * @return com.community.aspn.pojo.member.Member
     **/
    @Override
    public Member checkSession(Member member, HttpServletRequest request) {
        QueryWrapper<Member> query = new QueryWrapper<>();
        //用户名，密码验证
        query.eq("login_id", member.getLoginId())
                .eq("password", member.getPassword());
        Member m = getMember(request, query);
        if(m != null){
            this.updateLoginTime(m);
        }
        return m;
    }

    private Member getMember(HttpServletRequest request, QueryWrapper<Member> query) {
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


    @Override
    public List<Map<String,Object>> getDepartment() {
        return memberMapper.getDepartment();
    }

    /**
     * @Author nanguangjun
     * @Description // chang password
     * @Date 15:05 2021/5/17
     * @Param [pw]
     * @return int
     **/
    @Override
    public int changPassword(Map<String, Object> pw) {
        String password  = pw.get("new").toString();
        String confirm = pw.get("confirm").toString();
        Integer id = Integer.parseInt(pw.get("id").toString());
        if(!password.equals(confirm)){
           return 0;
        }
        Member member = new Member();
        member.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        member.setId(id);
        member.setUpdateTime(new Date());
        member.setUpdateId(id);
        memberMapper.updateById(member);
        return 1;
    }

    /**
     * @Author nanguangjun
     * @Description // member application
     * @Date 9:35 2021/5/25
     * @Param [memberApp]
     * @return int
     **/
    @Override
    public Map<String, String> memberApplication(MemberApp memberApp) {
        memberApp.setMemberYn("N");
        memberApp.setRegisterTime(new Date());
        Member member = new Member();
        member.setLoginId(memberApp.getLoginId());
        member.setEmail(memberApp.getEmail());
        member.setPhone(memberApp.getPhone());
        Map<String, String> resultMap = this.checkMemberOne(member);
        if (!"0".equals(resultMap.get("code"))) {
            memberApp.setPassword(DigestUtils.md5DigestAsHex(memberApp.getPassword().getBytes()));
            memberAppMapper.insert(memberApp);
            resultMap.put("code", "1");
            resultMap.put("msg", "신청 성공!");
            scheduleMailComponent.memberApplicationRemind(memberApp);
        }
        return resultMap;
    }

    /**
     * @Author nanguangjun
     * @Description // get member application
     * @Date 13:27 2021/5/26
     * @Param []
     * @return java.util.List<com.community.aspn.pojo.member.MemberApp>
     **/
    @Override
    public List<Map<String,Object>> getAllAppMember() {
        return memberMapper.getAllAppMember();
    }

    /**
     * @Author nanguangjun
     * @Description // 회원조회  admin 페이지
     * @Date 15:53 2021/5/28
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public HashMap<String,Object> getAllMemberByAdmin(HashMap<String,Object> params) {
        HashMap<String, Object> result = new HashMap<>();
        Integer size = Integer.parseInt(params.get("itemsPerPage").toString());
        Integer page = Integer.parseInt(params.get("page").toString());

        //分页查询传参
        Map<String, Object> args = new HashMap<>();

        if(params.get("department") != null){
            args.put("department", params.get("department").toString());
        }
        if (params.get("authority") != null){
            args.put("authority", params.get("authority").toString());
        }
        Integer total = memberMapper.getAllMemberByAdminCount(args);
        args.put("page", (page - 1) * size);
        args.put("size", size);
        List<Map<String, Object>> list = memberMapper.getAllMemberByAdmin(args); //分页查询

        int pages = total % size == 0 ? total / size : total / size + 1;
        result.put("members", list); //数据
        result.put("page", page); //当前页面
        result.put("pages", pages); //总页数
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // 신청회원을 정규 회원을로 변경
     * @Date 17:03 2021/5/26
     * @Param [ids]
     * @return void
     **/
    @Override
    public void appMemberToRealMember(List<Integer> ids) {
        MemberApp memberApp;
        Integer loginIdCount = 0;
        for (int i = 0; i < ids.size(); i++) {
            memberApp = memberAppMapper.selectById(ids.get(i));
            String department = memberApp.getDepartment();
            Department d = departmentMapper.selectById(department);
            memberApp.setAuthority(d.getAuthority());
            loginIdCount = memberMapper.selectCount(new QueryWrapper<Member>().eq("login_id", memberApp.getLoginId()));
            //如果会员已存在就停止
            if(loginIdCount> 0){
                break;
            }
            this.appMemberInsertMember(memberApp);
            Member member = memberMapper.selectOne(new QueryWrapper<Member>().eq("login_id", memberApp.getLoginId()));
            scheduleMailComponent.memberApplicationRemindSuccess(member);
        }
    }

    /**
     * @Author
     * @Description // 신청 회원 삭제
     * @Date 13:49 2021/5/27
     * @Param [ids]
     * @return void
     **/
    @Override
    public void appMemberDelete(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            memberAppMapper.deleteById(ids.get(i));
        }
    }

    /**
     * @Author nanguangjun
     * @Description // 初始化密码:123456
     * @Date 8:25 2021/7/20
     * @Param [id]
     * @return void
     **/
    @Override
    public void iniPassword(Integer id) {
        Member member = memberMapper.selectById(id);
        member.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        memberMapper.updateById(member);
    }

    /**
     * @Author nanguangjun
     * @Description // 커뮤니티 위키 글 통계
     * @Date 10:05 2021/11/1
     * @Param [params]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> reportWCMemberCount(HashMap<String, String> params) {
        String [] dept = params.get("departments").split(",");
        List<String> list = Arrays.asList(dept);
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("list",list);
        maps.put("start",params.get("start"));
        maps.put("end",params.get("end"));
        List<Map<String, Object>> result = memberMapper.reportWCMemberCount(maps);
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // 회원리스트 조회 검색조건 회원 이름
     * @Date 10:41 2021/11/25
     * @Param [name]
     * @return java.util.List<com.community.aspn.pojo.member.Member>
     **/
    @Override
    public List<Member> getMembersSearchByName(HashMap<String,String> params) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("member_name",params.get("name"));
        List<Member> members = memberMapper.selectList(queryWrapper);
        String p = "";
        for (int i = 0; i < members.size(); i++) {
            if(members.get(i).getPicture() != null){
                p = minoIOComponent.afterGetContentFromDBToFront(members.get(i).getPicture().toString(),
                        params.get("remoteAddr").toString());
                members.get(i).setPicture(p);
            }
        }
        return members;
    }

    /**
     * 평정 상세
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getRatingDetail(HashMap<String, Object> params) {
        return memberMapper.getRatingDetail(params);
    }

    /**
     * @Author nanguangjun
     * @Description // member insert by memberApp
     * @Date 17:06 2021/5/26
     * @Param [memberApp]
     * @return void
     **/
    private void appMemberInsertMember(MemberApp memberApp){
        Member member = new Member();
        member.setLoginId(memberApp.getLoginId());
        member.setMemberName(memberApp.getMemberName());
        member.setPassword(memberApp.getPassword());
        member.setEmail(memberApp.getEmail());
        member.setAuthority(memberApp.getAuthority());
        member.setDepartment(memberApp.getDepartment());
        member.setRegisterTime(new Date());
        member.setStatus("ON");
        if(memberApp.getPicture() != null){
            member.setPicture(member.getPicture());
        }
        memberMapper.insert(member);
        //update memberApp
        memberApp.setMemberYn("Y");
        memberAppMapper.updateById(memberApp);
    }
}
