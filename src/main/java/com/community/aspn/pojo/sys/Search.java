package com.community.aspn.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("search")
public class Search {
    @TableId
    private String content;
    private String ty;
    private int cnt;
    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
