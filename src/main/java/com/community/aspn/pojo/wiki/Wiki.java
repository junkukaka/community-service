package com.community.aspn.pojo.wiki;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("wiki")
public class Wiki {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private Integer hisId;
    private Integer menuId;
    private String picture;

    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
