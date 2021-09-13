package com.community.aspn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;

@SpringBootTest
public class MailTest {

    @Autowired
    private JavaMailSender javaMailSender;


//    @Test
//    public void sendSuccessMail(){
//        Member member = new Member();
//        member.setEmail("nanguangjun@aspnc.com");
//        member.setMemberName("남광준");
//        mailSendUtil.memberApplicationRemindSuccess(member);
//    }
//
//    @Test
//    public void sendRemindMail(){
//        MemberApp member = new MemberApp();
//        member.setEmail("nanguangjun@aspnc.com");
//        member.setMemberName("남광준");
//        mailSendUtil.memberApplicationRemind(member);
//    }
//
//    @Test
//    public void sendEmail() {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom("aspn300@aspnc.com");
//        msg.setTo("nanguangjun@aspnc.com");
//        msg.setSubject("Testing from Spring Boot");
//        msg.setText("Hello World Spring Boot Email");
//        javaMailSender.send(msg);
//    }
//
//    @Test
//    public void sendHTMLEmail() throws Exception{
//        String subject = "html mail test";
//        String content = "<h1>test</h1>`";
//        this.sendHtmlMail("aspn300@aspnc.com","nanguangjun@aspnc.com",subject,content);
//    }
//
//
//
//    private void sendHtmlMail(String from,String to, String subject, String content) throws MessagingException {
//
//        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
//
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
//        mimeMessageHelper.setTo(to);
//        mimeMessageHelper.setSubject(subject);
//        mimeMessageHelper.setText(content, true);
//        mimeMessageHelper.setFrom(from);
//
//        try {
//            javaMailSender.send(mimeMailMessage);
//
//        } catch (MailException e) {
//        }
//    }
//
//    // 单发
//    @Test
//    public void QQSendOneEmail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setSubject("qq title");
//        // 从哪里 发送
//        message.setFrom("342421055@qq.com");
//        // 到哪里 可设置多个，实现群发
//        message.setTo("nanguangjun@aspnc.com");
//        message.setSentDate(new Date());
//        message.setText("342421055@qq.com");
//        try {
//            javaMailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

