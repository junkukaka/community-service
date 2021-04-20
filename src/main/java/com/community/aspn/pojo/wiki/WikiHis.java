package com.community.aspn.pojo.wiki;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("wiki_his")
public class WikiHis {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer wikiId;
    private String title;
    private Integer menuId;
    private String picture;
    private Integer memberId;
    private String content; //内容
    private int hisYn; //标记是否能修改，历史与否
    private String information; //改动说明

    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
