package com.harry.novel_web.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.harry.novel_web.common.Constants;
import com.harry.novel_web.common.Result;
import com.harry.novel_web.controller.dto.UserDTO;
import com.harry.novel_web.entity.User;
import com.harry.novel_web.mapper.UserMapper;
import com.harry.novel_web.service.UserService;
import com.harry.novel_web.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;



@ResponseBody
@RestController
@RequestMapping("/user")
@CrossOrigin(origins ="*")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }else if(username.equals("admin") && password .equals("123")){
            return Result.adminSuccess();
        }else{
            userService.login(userDTO);
            return Result.success();
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = Md5Utils.code(userDTO.getPassword());
        userDTO.setPassword(password);
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }else if(userMapper.sameName(username).size()!=0){
            return Result.error(Constants.CODE_400, "此用户名已被注册");
        }else {
            return Result.success(userService.register(userDTO));
        }
    }

    //数据库插入
    //新增和修改
    @PostMapping
    public Result save(@RequestBody User user) {
        //新增或者更新
        return Result.success(userService.saveUser(user));
    }

    @DeleteMapping({"/{id}"})
    public Result delete(@PathVariable Integer id) {return Result.success(userService.removeById(id));}

    @PostMapping("del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {return Result.success(userService.removeByIds(ids));}

    //查询所有数据
    @GetMapping()
    public Result findAll() {return Result.success(userService.list());}

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {return Result.success(userService.getById(id));}

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getById(queryWrapper));

    }
}

