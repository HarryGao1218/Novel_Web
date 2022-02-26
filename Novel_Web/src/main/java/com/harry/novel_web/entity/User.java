package com.harry.novel_web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="t_user")
public class User {

    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
}
