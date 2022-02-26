package com.harry.novel_web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_file")
public class Files {

    @TableId(type = IdType.AUTO)
    private Integer id;

//    private String novelName;
//    private String novelType;
//    private Long novelSize;
//    private String novelUrl;
//
//    private String briefName;
//    private String briefType;
//    private Long briefSize;
//    private String briefUrl;
//
//    private String pictureName;
//    private String pictureType;
//    private Long pictureSize;
//    private String pictureUrl;
    private String type;

    private String novel_name;
    private String novel_type;
    private Long novel_size;
    private String novel_url;

    private String brief_name;
    private String brief_type;
    private Long brief_size;
    private String brief_url;

    private String picture_name;
    private String picture_type;
    private Long picture_size;
    private String picture_url;
}
