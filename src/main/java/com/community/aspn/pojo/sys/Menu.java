package com.community.aspn.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("menu")
public class Menu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    //菜单层级
    private Integer tier;
    //父类菜单
    private Integer father;
    private Character useYn;
    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
