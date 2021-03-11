package com.community.aspn.pojo.community;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author nanguangjun
 * @Description // 用户收藏
 * @Date 13:39 2021/3/9
 * @Param
 * @return
 **/
@Data
@TableName("com_collect")
public class ComCollect {

    @TableId
    private Integer communityId;
    private Integer memberId;

    private Date registerTime;
}
