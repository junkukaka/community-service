package com.community.aspn.pojo.wiki;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("wiki_member_collect_menu")
public class WikiMemberCollectMenu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer memberId;
    private String menuName;
    private Integer updateId;
    private Date updateTime;
}
