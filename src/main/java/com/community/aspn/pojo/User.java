package com.community.aspn.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user")
@EqualsAndHashCode(callSuper = true)
public class User extends Model<User> implements Serializable {

    private static final long serialVersionUID = 6401942840459021558L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String password;
    private String userName;
    private String email;
    //部门
    private Integer department;
    //职位
    private Integer duty;
    private String phone;
    //备注
    private String remark;
    //用户权限
    private int authority;
    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
