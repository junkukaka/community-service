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
    private String memberId;
    private Integer sort;
    private String content; //内容
    private String comment; //改动说明

    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
