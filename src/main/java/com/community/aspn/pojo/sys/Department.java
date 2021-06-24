package com.community.aspn.pojo.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("department")
public class Department {
    private String id;
    private String name;
    private Integer authority;
    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
