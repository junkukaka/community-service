package com.community.aspn.pojo.community;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("community")
public class Community {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer menuId;
    private String title;
    private String content;
    private Integer memberId;

    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
