package com.harry.novel_web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.harry.novel_web.entity.Files;
import com.harry.novel_web.entity.Shelf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ShelfMapper extends BaseMapper<Shelf> {

    @Select("select * from t_file where id = #{novel_id}")
    List<Shelf> findNovel(Integer novel_id);

    @Select("select * from t_shelf where (user_id = #{user_id} and novel_id = #{novel_id})")
    List<Shelf> findShelf(Integer user_id,Integer novel_id);

    @Select("select t_file.id,t_file.type,t_file.novel_name,t_file.novel_type,t_file.novel_size,t_file.novel_url,t_file.brief_name,t_file.brief_type,t_file.brief_size,t_file.brief_url,t_file.picture_name,t_file.picture_type,t_file.picture_size,t_file.picture_url from t_file,t_shelf where t_shelf.`user_id` = #{user_id} and t_shelf.`novel_id` = t_file.`id`")
    List<Files> findUserShelf(Integer user_id);
}
