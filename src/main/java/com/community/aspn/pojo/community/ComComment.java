package com.community.aspn.pojo.community;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author nanguangjun
 * @Description // community comment
 * @Date 14:10 2021/3/1
 * @Param
 * @return
 **/
@Data
@TableName("com_comment")
public class ComComment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer communityId;
    private Integer userId;
    private Integer replyUserId;
    private String content;

    private Integer registerId;
    private Date registerTime;
    private Integer updateId;
    private Date updateTime;
}
