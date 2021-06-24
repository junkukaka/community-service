package com.community.aspn.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("authority_wiki")
public class AuthorityWiki {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer aId;
    private Integer menuId;
    private int viewYn;
    private int editYn;
    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
