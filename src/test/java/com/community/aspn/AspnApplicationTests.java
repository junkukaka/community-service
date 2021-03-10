package com.community.aspn;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.community.aspn.pojo.user.User;
import com.community.aspn.util.TokenUtil;
import com.community.aspn.util.mino.MinIOFileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootTest
class AspnApplicationTests {

    @Test
    void minoUtilTest(){
        String communityFileName = MinIOFileUtil.getCommunityFileName("abc.jpg");
        System.out.println(communityFileName);
        System.out.println(MinIOFileUtil.getCommunityFileName());
    }

    @Test
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
    void testBaseToFile(){
        String content = "<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAacAAAEnCAYAAAANX4xAAAAf6ElEQVR4Ae3d2Y8UZd/Gcc6e/+AORK5CYII=\">" ;
        Boolean aBoolean = MinIOFileUtil.ifBase64RegexMatcher(content);
        System.out.println(aBoolean);
    }

    @Test
    void testTokenToString(){
        User user = new User();
        user.setId(1);
        String token = TokenUtil.sign(user);
        System.out.println("token = " + token);

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("txdy")).build();
        DecodedJWT jwt = verifier.verify(token);
        int t = jwt.getClaim("userId").asInt();
        System.out.println(t);
    }

    @Test
    void testEmailRegex(){
        String regex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
        String[] str1 = {"aaa@","aa.b@qq.com","1123@163.com","113fe$@11.com","han. @sohu.com.cn","han.c@sohu.com.cn.cm.cm","","张吗"};
        for (String str:str1){
            System.out.println(str+" \\\\. "+str.matches(regex));
        }
    }


}
