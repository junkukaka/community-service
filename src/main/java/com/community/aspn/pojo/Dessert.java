package com.community.aspn.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Dessert {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String calories;
    private Float fat;
    private Integer carbs;
    private Float protein;
}
