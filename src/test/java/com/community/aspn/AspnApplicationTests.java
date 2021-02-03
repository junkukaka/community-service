package com.community.aspn;


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
        String content = "src='data:image/png;base64,iVBORw0KGgoAasdAAANSUasdasdhEUgAAAbgAA89=='src='data:image/png;base64,iVBORw0KGgoAAAANSUasdasdhEUgAAAbgAA89=='" ;
        String pattern = "(data:image\\/png\\;base64\\,)[a-z,A-Z]([^\'\"]+)";

        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(content);
        String replace = content;

        while (matcher.find()){
            String group = matcher.group();
            replace = replace.replace(group, "123");
            System.out.println(replace);

        }
    }

}
