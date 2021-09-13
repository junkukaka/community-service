package com.community.aspn.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("schedule_mail")
public class ScheduleMail {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String createDay;
    private Integer success;
    private String title;
    private String content;
    private String sendMail;
    private String toMail;
    private String msg;
    private Date startTime;
    private Date endTime;
}
