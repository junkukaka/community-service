package com.community.aspn.pojo.community;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author nanguangjun
 * @Description //用户访问量
 * @Date 13:44 2021/3/9
 * @Param
 * @return
 **/
@Data
@TableName("com_hits")
public class ComHits {
    @TableId
    private Integer communityId;
    private Integer memberId;
    private String ip;

    private Date registerTime;
    private Date updateTime;
}
