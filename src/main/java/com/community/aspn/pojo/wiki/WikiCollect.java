package com.community.aspn.pojo.wiki;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("wiki_collect")
public class WikiCollect {
    private Integer wikiId;
    private Integer memberId;
    private Integer collectMenuId;
    private Date updateTime;
}
