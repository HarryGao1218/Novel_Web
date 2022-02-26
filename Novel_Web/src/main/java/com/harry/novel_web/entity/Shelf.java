package com.harry.novel_web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_shelf")
public class Shelf {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer user_id;
    private Integer novel_id;
}