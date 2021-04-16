package com.community.aspn.pojo.wiki;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("wiki_master")
public class WikiMaster {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private Integer itemId;
    private Integer menuId;
    private String picture;
    private Integer sort;

    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
