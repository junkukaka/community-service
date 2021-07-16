package com.community.aspn.pojo.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("member_app")
public class MemberApp {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String loginId;
    private String picture;
    private String password;
    private String memberName;
    private String email;
    private String memberYn;
    //部门
    private String department;
    private Integer authority;
    private String phone;
    //备注
    private String comment;

    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
