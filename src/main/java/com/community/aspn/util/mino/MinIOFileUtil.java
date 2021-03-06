package com.community.aspn.util.mino;


import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;


import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MinIOFileUtil {

    private static final String REG = "(data:image\\/[a-z]{2,4}\\;base64\\,)[a-z,A-Z,/]([^\'\"]+)";
    public static final Pattern PATTERN = Pattern.compile(REG);


    /**
     * @Author nanguangjun
     * @Description // get file path+name
     * @Date 17:17 2021/3/30
     * @Param [str]
     * @return java.lang.String
     **/
    public static String getFileName(String str){
        String randomUUIDString = getUUID();
        String[] split = str.split("\\.");
        String date = getFormatDate();
        String year = getFormatYear();
        String month = getFormatMonth();
        return year + "/" + month + "/" + date + "/" + randomUUIDString+ "." + split[1];
    }


    public static String getFormatDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    public static String getFormatYear(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(new Date());
    }

    public static String getFormatMonth(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(new Date());
    }

    /**
     * @Author nanguangjun
     * @Description //getUUID
     * @Date 11:02 2021/2/25
     * @Param []
     * @return java.lang.String
     **/
    public static String getUUID(){
        return UUID.randomUUID().toString();
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
     * @Author nanguangjun
     * @Description //是否匹配 base64 截图文件
     * @Date 10:36 2021/2/4
     * @Param [content]
     * @return java.lang.Boolean
     **/
    public static Boolean ifBase64RegexMatcher(String content){
        Matcher matcher = PATTERN.matcher(content);
        return matcher.find();
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
