package com.harry.novel_web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.harry.novel_web.entity.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<Files> {
    @Select("select  * from t_file limit #{pageNum},#{pageSize}")
    List<Files> selectPage(Integer pageNum, Integer pageSize);

    @Select ("select * from t_file where novel_name like \"%\"#{nname}\"%\"")
    List<Files> findFile(String nname);

    //删除数据库数据
    @Delete("delete from t_file where id = #{id}")
    int delete(Integer id);

    //查找novel_url
    @Select("select * from t_file where id = #{id}")
    List<Files> findNovel(Integer id);

    //查找novel_url
    @Select("select novel_url from t_file where id = #{id}")
    String findNovelUrl(Integer id);

    //查找brief_url
    @Select("select brief_url from t_file where id = #{id}")
    String findBriefUrl(Integer id);

    //查找picture_url
    @Select("select picture_url from t_file where id = #{id}")
    String findPictureUrl(Integer id);

    //分类查询
    @Select ("select * from t_file where type = #{classify}")
    List<Files> classifyNovel(String classify);

}
