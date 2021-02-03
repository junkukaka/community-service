package com.community.aspn.util.mino;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinIOFileUtil {

    private static final String REG = "(data:image\\/png\\;base64\\,)[a-z,A-Z]([^\'\"]+)";
    private static final Pattern PATTERN = Pattern.compile(REG);

    /**
     * @Author nanguangjun
     * @Description // 日期 / uuid.文件后缀
     * @Date 17:22 2021/2/2
     * @Param [原始图片名称]
     * @return java.lang.String
     **/
    public static String getCommunityFileName(String img){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String[] split = img.split("\\.");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        String path =  "image/" + format + "/" + randomUUIDString+ "." + split[1];
        return path;
    }

    /**
     * @Author nanguangjun
     * @Description // 论坛复制粘贴的图片
     * @Date 9:40 2021/2/3
     * @Param []
     * @return java.lang.String
     **/
    public static String getCommunityFileName(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        String path =  "image/" + format + "/" + randomUUIDString+ "." + "png";
        return path;
    }

    /**
     * @Author nanguangjun
     * @Description // base64 转 MultipartFile
     * @Date 13:27 2021/2/3
     * @Param [imgStr]
     * @return org.springframework.web.multipart.MultipartFile
     **/
    public static MultipartFile base64MutipartFile(String imgStr){
        try {
            String [] baseStr = imgStr.split(",");
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] b;
            b = base64Decoder.decodeBuffer(baseStr[1]);
            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new StringToBASE64MultipartFile(b,baseStr[0]) ;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 查看是否匹配
     * @param content
     * @return
     */
    public static Boolean ifBase64RegexMatcher(String content){
        Matcher matcher = PATTERN.matcher(content);
        return matcher.matches();
    }

    /**
     * 如果匹配的话就替换url
     * @param content
     * @return
     */
    public static int base64RegexReplace(String content){
        Matcher matcher = PATTERN.matcher(content);
        int i = 1;
        while (matcher.find()){
            String group = matcher.group(i);
            System.out.println(group);
            i++;
        }
        return 1;
    }

    /**
     * @Author nanguangjun
     * @Description // 获取文件 ContentType
     * @Date 17:34 2021/2/2
     * @Param [原始图片名称]
     * @return java.lang.String
     **/
    public static String getContentType(String img){
        String[] split = img.split("\\.");
        String type = "";
        switch (split[1]){
            case "png":
                type = "image/png";
                break;
            case "jpeg":
            case "jpg":
                type = "image/jpeg";
                break;
            case "gif":
                type = "image/gif";
                break;
            case "bmp":
                type = "image/bmp";
                break;
            case "ico":
                type = "image/vnd.microsoft.icon";
                break;
            case "svg":
                type = "image/svg+xml";
                break;
            case "tif":
            case "tiff":
                type = "image/tiff";
                break;
            case "webp":
                type = "image/webp";
                break;
            default:
                type = "None";
        }

        return type;
    };



}
