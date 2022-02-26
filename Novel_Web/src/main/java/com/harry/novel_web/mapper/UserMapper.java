package com.harry.novel_web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.harry.novel_web.controller.dto.UserDTO;
import com.harry.novel_web.entity.Files;
import com.harry.novel_web.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select ("select * from t_user where username = #{username}")
    List<User> sameName(String username);

}
