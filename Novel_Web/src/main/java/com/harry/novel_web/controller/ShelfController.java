package com.harry.novel_web.controller;

import com.harry.novel_web.common.Constants;
import com.harry.novel_web.common.Result;
import com.harry.novel_web.controller.dto.ShelfDTO;
import com.harry.novel_web.entity.Shelf;
import com.harry.novel_web.mapper.ShelfMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/shelf")
public class ShelfController {

    @Resource
    private ShelfMapper shelfMapper;

    @PostMapping("/add")
//    public Result add_shelf(@RequestParam (value = "userId",required = true)Integer userId,
//                            @RequestParam (value = "novelId",required = true)Integer novelId) {

//    public Result add_shelf(@RequestParam Integer userId,@RequestParam Integer novelId){

        public Result add_shelf(@RequestBody ShelfDTO shelfDTO){
        Integer userId = shelfDTO.getUser_id();
        Integer novelId = shelfDTO.getNovel_id();

        if(userId==null ||novelId==null){return Result.error(Constants.CODE_400,"userId或novelId为空");}
        if(shelfMapper.findNovel(novelId).size()==0){
            return Result.error(Constants.CODE_400,"没有查询到书籍");
        }else if(shelfMapper.findShelf(userId,novelId).size()!=0){
            return Result.error(Constants.CODE_400,"该书已经在书架中");
        }else{
            Shelf saveShelf = new Shelf();
            saveShelf.setUser_id(userId);
            saveShelf.setNovel_id(novelId);
            shelfMapper.insert(saveShelf);
            return Result.success();
        }
    }

    @GetMapping("/find")
            public Result findUserShelf(@RequestParam Integer user_id) {

//        public Result findUserShelf(@RequestBody ShelfDTO shelfDTO) {
//        Integer user_id = shelfDTO.getUser_id();

        if (shelfMapper.findUserShelf(user_id).size() == 0) {
            return Result.error(Constants.CODE_400, "您还没有收藏");
        } else {
            return Result.success(shelfMapper.findUserShelf(user_id));
        }
    }
}
