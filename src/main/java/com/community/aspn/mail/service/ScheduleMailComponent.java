package com.community.aspn.mail.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.mail.mapper.ScheduleMailMapper;
import com.community.aspn.member.mapper.MemberMapper;
import com.community.aspn.pojo.member.Member;
import com.community.aspn.pojo.member.MemberApp;
import com.community.aspn.pojo.sys.ScheduleMail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleMailComponent {



    @Value("${spring.mail.username}")
    private String from;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private ScheduleMailMapper scheduleMailMapper;

    /**
     * @Author nanguangjun
     * @Description // application success 회원가입 성고 메세지
     * @Date 9:56 2021/7/27
     * @Param [member]
     * @return java.lang.String
     **/
    public void memberApplicationRemindSuccess(Member member){
        String date = simpleDateFormat.format(new Date());
        String subject = "ASPN Community&Wiki 회원가입 " + date;
        String content = this.getMemberApplicationRemindSuccess(member);
        this.insertScheduleMail(subject,content,member);
//        this.sendHtmlMail(member.getEmail(),subject,content);
    }

    /**
     * @Author nanguangjun
     * @Description // 회원 가입 신청 메세지
     * @Date 14:36 2021/7/27
     * @Param [memberApp]
     * @return void
     **/
    public void memberApplicationRemind(MemberApp memberApp){
        String date = simpleDateFormat.format(new Date());
        String subject = "ASPN Community&Wiki 회원가입 신청 " + date;
        String content = this.getMemberApplicationRemindRemind(memberApp);
        this.insertScheduleMail(subject,content,memberApp);
        //this.sendHtmlMail(memberApp.getEmail(),subject,content);
        //관리자 한테 신청성고 메세지를 보낸다
        this.memberApplicationToAdmin(memberApp,content);
    }

    /**
     * @Author nanguangjun
     * @Description // 获取邮件发送数据
     * @Date 10:36 2021/9/13
     * @Param [subject, content, object]
     * @return com.community.aspn.pojo.sys.ScheduleMail
     **/
    public void insertScheduleMail(String subject,String content,Object object){
        MemberApp memberApp = null;
        Member member = null;
        ScheduleMail scheduleMail = new ScheduleMail();
        scheduleMail.setCreateDay(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        scheduleMail.setContent(content);
        scheduleMail.setTitle(subject);
        scheduleMail.setSendMail(from);
        if(object instanceof MemberApp){
            memberApp = (MemberApp)object;
            scheduleMail.setToMail(memberApp.getEmail());
        }else {
            member = (Member) object;
            scheduleMail.setToMail(member.getEmail());
        }
        scheduleMailMapper.insert(scheduleMail);
    }

    /**
     * @Author nanguangjun
     * @Description // 회원 가입 신청 메세지을 admin 한테 보냅니다
     * @Date 10:22 2021/8/9
     * @Param []
     * @return void
     **/
    public void memberApplicationToAdmin(MemberApp memberApp,String content){
        List<Member> members = this.getAdminMember();
        String date = simpleDateFormat.format(new Date());
        String subject = "ASPN Community&Wiki " + memberApp.getMemberName() + "님 회원가입 신청 " + date;
        for (int i = 0; i < members.size(); i++) {
            this.insertScheduleMail(subject,content,members.get(i));
        }
    }



    /**
     * @Author nanguangjun
     * @Description // 회원가입 성공
     * @Date 13:22 2021/7/27
     * @Param [member]
     *  <div style="background-color:#f2f2f2; width:100%;padding:10px 0;">
     * 	<div style="margin: 0 auto; width:500px; background-color:white;border: 1px solid #dedede">
     * 		<div style="padding:20px;border-bottom: 1px solid #dedede">
     * 			<h2 style="font-weight: bold;margin:0">ASPN Community&Wiki</h2>
     * 		</div>
     * 		<div style="padding:20px 20px;background-color:#fafafa;">
     * 			<h5 style="font-weight: bold;padding:0 0 10px 0">님 회원가입 성공했습니다.</h5>
     * 			<hr style="border-color:#c5c5c5;border-top-width:1px;"/>
     * 			<ul style="font-size:9px;color:#a0a0a0;padding:5px 0 0;list-style:none;margin:0">
     * 				<li>- 본 메일은 발신전용입니다.</li>
     * 				<li>- 문의사항은 문의메일 (nanguangjun@aspnc.com)을 이용해 주세요.</li>
     * 			</ul>
     * 		</div>
     * 	</div>
     *   </div>
     * @return java.lang.String
     **/
    public String getMemberApplicationRemindSuccess(Member member){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div style=\"background-color:#f2f2f2; width:100%;padding:10px 0;\">\n" +
                "\t<div style=\"margin: 0 auto; width:500px; background-color:white;border: 1px solid #dedede\">\n" +
                "\t\t<div style=\"padding:20px;border-bottom: 1px solid #dedede\">\n" +
                "\t\t\t<h2 style=\"font-weight: bold;margin:0\">ASPN Community&Wiki</h2>\n" +
                "\t\t</div>\n" +
                "\t\t<div style=\"padding:20px 20px;background-color:#fafafa;\">\n" +
                "\t\t\t<h5 style=\"font-weight: bold;padding:0 0 10px 0\">"
                + member.getMemberName() + "님 회원가입 성공했습니다.</h5>\n" +
                "\t\t\t<hr style=\"border-color:#c5c5c5;border-top-width:1px;\"/>\n" +
                "\t\t\t<ul style=\"font-size:9px;color:#a0a0a0;padding:5px 0 0;list-style:none;margin:0\">\n" +
                "\t\t\t\t<li>- 본 메일은 발신전용입니다.</li>\n" +
                "\t\t\t\t<li>- 문의사항은 문의메일 (nanguangjun@aspnc.com)을 이용해 주세요.</li>\n" +
                "\t\t\t</ul>"

        );
        return stringBuilder.toString();
    }

    /**
     * @Author nanguangjun
     * @Description // 회원가입 신청 알람
     * @Date 14:35 2021/7/27
     * @Param [memberApp]
     * @return java.lang.String
     **/
    public String getMemberApplicationRemindRemind(MemberApp memberApp){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div style=\"background-color:#f2f2f2; width:100%;padding:10px 0;\">\n" +
                "\t<div style=\"margin: 0 auto; width:500px; background-color:white;border: 1px solid #dedede\">\n" +
                "\t\t<div style=\"padding:20px;border-bottom: 1px solid #dedede\">\n" +
                "\t\t\t<h2 style=\"font-weight: bold;margin:0\">ASPN Community&Wiki</h2>\n" +
                "\t\t</div>\n" +
                "\t\t<div style=\"padding:20px 20px;background-color:#fafafa;\">\n" +
                "\t\t\t<h5 style=\"font-weight: bold;padding:0 0 10px 0\">"
                + memberApp.getMemberName() + "님 회원가입 신청완료.</h5>\n" +
                "\t\t\t<hr style=\"border-color:#c5c5c5;border-top-width:1px;\"/>\n" +
                "\t\t\t<ul style=\"font-size:9px;color:#a0a0a0;padding:5px 0 0;list-style:none;margin:0\">\n" +
                "\t\t\t\t<li>- 본 메일은 발신전용입니다.</li>\n" +
                "\t\t\t\t<li>- 문의사항은 문의메일 (nanguangjun@aspnc.com)을 이용해 주세요.</li>\n" +
                "\t\t\t</ul>"
        );
        return stringBuilder.toString();
    }

    /**
     * @Author nanguangjun
     * @Description //获取Admin 账号
     * @Date 14:03 2021/9/13
     * @Param []
     * @return java.util.List<com.community.aspn.pojo.member.Member>
     **/
    public List<Member> getAdminMember(){
        List<Member> members = memberMapper.selectList(new QueryWrapper<Member>().eq("authority", 0));
        return members;
    }
}
