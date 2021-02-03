package com.community.aspn;


import com.community.aspn.util.mino.MinIOFileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AspnApplicationTests {

    @Test
    void minoUtilTest(){
        String communityFileName = MinIOFileUtil.getCommunityFileName("abc.jpg");
        System.out.println(communityFileName);
        System.out.println(MinIOFileUtil.getCommunityFileName());
    }

}
