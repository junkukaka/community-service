package com.community.aspn.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("search_his")
public class Search {
    @TableId
    private String id;
    private String content;
    private String ty;
    private Integer registerId;
    private Date registerTime;
}
