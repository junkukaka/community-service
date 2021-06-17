package com.community.aspn.pojo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("files")
public class Files {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String docId;
    private String originalName;
    private String filePath;
    private String flag;
    private Date registerTime;
}
