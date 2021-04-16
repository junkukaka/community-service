package com.community.aspn.pojo.wiki;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("wiki_master_his")
public class WikiMasterHis {
    @TableId(type = IdType.AUTO)
    private Integer id;
}
