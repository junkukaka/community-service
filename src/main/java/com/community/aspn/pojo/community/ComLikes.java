package com.community.aspn.pojo.community;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author nanguangjun
 * @Description //用户喜欢
 * @Date 13:41 2021/3/9
 * @Param
 * @return
 **/
@Data
@TableName("com_like")
public class ComLikes {
    @TableId
    private Integer communityId;
    private Integer userId;
    private Integer likeYn;

    private Date registerTime;
}
