package com.community.aspn.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String loginId;
    private String password;
    private String userName;
    //部门
    private String department;
    //职位
    private String duty;
    private String phone;
    private String email;
    //备注
    private String remark;
    //用户权限
    private int authority;
    private String registerId;
    private Date registerTime;
    private String updateId;
    private Date updateTime;
}
