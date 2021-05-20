package com.community.aspn;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.community.aspn.pojo.member.Member;
import com.community.aspn.util.TokenUtil;
import com.community.aspn.util.mino.MinIOFileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootTest
class AspnApplicationTests {

//    @Test
//    void minoUtilTest(){
//        String communityFileName = MinIOFileUtil.getCommunityFileName("abc.jpg");
//        System.out.println(communityFileName);
//        System.out.println(MinIOFileUtil.getCommunityFileName());
//    }
//
 /*   @Test
    void testRes(){
        String content = "<img src=\"data:image/jepg;base64,iVBORANX4xQVR4Ae3d2Y8UZd/Gcc6e/+AORK5CYII=\">" ;
        String pattern = "(data:image\\/[a-z]{2,4}\\;base64\\,)[a-z,A-Z]([^\'\"]+)";

        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(content);
        String replace = content;

        while (matcher.find()){
            String group = matcher.group();
            replace = replace.replace(group, "123");
            System.out.println(replace);
        }
    }

    @Test
    void testRes2(){
        String content = "<img src=\"data:image/jpeg;base64,//AIM+LLX4u+FLW/vL5bzTtc06fVjbwrvVIrJI/M2bQVwxG3D8nBxFei63O4rQ4q2X47LsTRdFc1E//9k=\">" ;
        String pattern = "(data:image\\/[a-z]{2,4}\\;base64\\,)[a-z,A-Z,/]([^\'\"]+)";

        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(content);
        String replace = content;

        while (matcher.find()){
            String group = matcher.group();
            replace = replace.replace(group, "123");
            System.out.println(replace);
        }
    }

    @Test
    void testBaseToFile(){
        String content = "<img src=\"data:image/jpeg;base64,/9j/4TIQRXhpZgAATU0AKgAAAAgADQEPAAIAAAAJAAAItgEQAAIAAAACAYAAAANX4xAAAAf6ElEQVR4Ae3d2Y8UZd/Gcc6e/+AORK5CYII=\">" ;
        Boolean aBoolean = MinIOFileUtil.ifBase64RegexMatcher(content);
        System.out.println(aBoolean);
    }

    @Test
    void testBaseToFile2(){
        String content ="<img src=\"data:image/jpeg;base64,//AIM+LLX4u+FLW/vL5bzTtc06fVjbwrvVIrJI/M2bQVwxG3D8nBxFei63O4rQ4q2X47LsTRdFc1E//9k=\">" ;
        Boolean aBoolean = MinIOFileUtil.ifBase64RegexMatcher(content);
        System.out.println(aBoolean);
    }*/
//
//    @Test
//    void testTokenToString(){
//        Member member = new Member();
//        member.setId(1);
//        String token = TokenUtil.sign(member);
//        System.out.println("token = " + token);
//
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("txdy")).build();
//        DecodedJWT jwt = verifier.verify(token);
//        int t = jwt.getClaim("userId").asInt();
//        System.out.println(t);
//    }
//
//    @Test
//    void testEmailRegex(){
//        String regex = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//        String[] str1 = {"lu.jiayu@ncbakery.com",
//                "cs1@chefchoice-china.com",
//                "skyzhang@zidan.com.cn",
//                "cs2@dd.com",
//                "skyzhang@zidan.com.cn",
//                "zhanjin_cai@163.com",
//                "2450068377@qq.com",
//                "aa.b@qq.com","1123@163.com","113fe@11.com",
//                "han. @sohu.com.cn","han.c@sohu.com.cn.cm.cm",
//                "","张吗"};
//        for (String str:str1){
//            System.out.println(str+" \\\\. "+str.matches(regex));
//        }
//    }
//    @Test
//    public void testIPReplace(){
//        String target = "#@#MINIO#@#";
//        String str = "http://192.168.72.132:9000/community/2021/2021-04/2021-04-06/e54ee717-5a81-485a-bb4c-c6cd14eac373.png";
//        String replace = str.replace("192.168.72.132:9000", target);
//        System.out.println(replace);
//    }
//
//    @Test
//    public void testMinIOReplace(){
//        String target = "192.168.72.132:9000";
//        String str = "http://#@#MINIO#@#/community/2021/2021-04/2021-04-06/e54ee717-5a81-485a-bb4c-c6cd14eac373.png";
//        String replace = str.replace("#@#MINIO#@#", target);
//        System.out.println(replace);
//    }
//
//    @Test
//    public void testIp(){
//        String pattern = "192.168";
//        String ip = "192.168.72.132";
//        System.out.println(ip.substring(0,7));
//    }
//
//    @Test
//    public void testsIp(){
//        String pattern = "http://192.168.[0-9]*.[0-9]*:9000";
//        String content = "![desc](http://192.168.1.132:9000/community/2021/2021-04/2021-04-08/adbae455-6fba-403c-9523-08b29cb4a2b8.png){{{width=\"auto\" height=\"auto\"}}}";
//        Pattern r = Pattern.compile(pattern);
//        Matcher matcher = r.matcher(content);
//        String replace = content;
//        while (matcher.find()){
//            String group = matcher.group();
//            replace = replace.replace(group, "@-@MINIO@-@");
//            System.out.println(replace);
//        }
//    }
//
//    @Test
//    public void testsIps(){
//        String pattern = "@-@MINIO@-@";
//        String content = "![desc](@-@MINIO@-@/community/2021/2021-04/2021-04-08/adbae455-6fba-403c-9523-08b29cb4a2b8.png){{{width=\"auto\" height=\"auto\"}}}";
//        Pattern r = Pattern.compile(pattern);
//        Matcher matcher = r.matcher(content);
//        String replace = content;
//        while (matcher.find()){
//            String group = matcher.group();
//            replace = replace.replace(group, "http://192.168.1.134:9000");
//            System.out.println(replace);
//        }
//    }
//
    @Test
    public void testMd5() throws NoSuchAlgorithmException {
        // spring自带工具包DigestUtils
        System.out.println(DigestUtils.md5DigestAsHex("qwe".getBytes()));
        // 7815696ecbf1c96e6894b779456d330e
    }






}
