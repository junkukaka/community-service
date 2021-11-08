package com.community.aspn.pojo.wiki;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("wiki_like")
public class WikiLike {
    @TableId
    private Integer wikiId;
    private Integer memberId;
    private Integer likeYn;
    private Date registerTime;
}
