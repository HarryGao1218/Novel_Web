package com.harry.novel_web.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import com.harry.novel_web.common.Constants;
import com.harry.novel_web.common.Result;
import com.harry.novel_web.entity.Files;
import com.harry.novel_web.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;


@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    //    @PostMapping("/upload")
//    public Result upload(String type,@RequestParam MultipartFile novel,@RequestParam MultipartFile brief,@RequestParam MultipartFile picture) throws IOException {
    @PostMapping("/upload")
    public String upload(String type, @RequestParam MultipartFile novel, @RequestParam MultipartFile brief, @RequestParam MultipartFile picture) throws IOException {

        String originalNovelName = novel.getOriginalFilename();
        String novelType = FileUtil.extName(originalNovelName);
        long novelSize = novel.getSize();

        String originalBriefName = brief.getOriginalFilename();
        String briefType = FileUtil.extName(originalBriefName);
        long briefSize = brief.getSize();

        String originalPictureName = picture.getOriginalFilename();
        String pictureType = FileUtil.extName(originalPictureName);
        long pictureSize = picture.getSize();
        //先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);

        //判断配置文件的目录是否存在，若不存在则创建一个新的文件目录
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }

        //定义一个文件唯一的标识码
        String novelUuid = IdUtil.fastSimpleUUID();
        String novelUUID = novelUuid + StrUtil.DOT + novelType;
        File uploadNovel = new File(fileUploadPath + novelUUID);

        String briefUuid = IdUtil.fastSimpleUUID();
        String briefUUID = briefUuid + StrUtil.DOT + briefType;
        File uploadBrief = new File(fileUploadPath + briefUUID);

        String pictureUuid = IdUtil.fastSimpleUUID();
        String pictureUUID = pictureUuid + StrUtil.DOT + pictureType;
        File uploadPicture = new File(fileUploadPath + pictureUUID);


        //把获取后的文件存储到磁盘目录
        novel.transferTo(uploadNovel);
        String novelUrl = "http://484470xs49.qicp.vip/novel/" + novelUUID;

        brief.transferTo(uploadBrief);
        String briefUrl = "http://484470xs49.qicp.vip/brief/" + briefUUID;

        picture.transferTo(uploadPicture);
        String pictureUrl = "http://484470xs49.qicp.vip/picture/" + pictureUUID;

        //存储数据库
        Files saveFile = new Files();

        saveFile.setType(type);

        saveFile.setNovel_name(originalNovelName);
        saveFile.setNovel_type(novelType);
        saveFile.setNovel_size(novelSize);
        saveFile.setNovel_url(novelUrl);

        saveFile.setBrief_name(originalBriefName);
        saveFile.setBrief_type(briefType);
        saveFile.setBrief_size(briefSize);
        saveFile.setBrief_url(briefUrl);

        saveFile.setPicture_name(originalPictureName);
        saveFile.setPicture_type(pictureType);
        saveFile.setPicture_size(pictureSize);
        saveFile.setPicture_url(pictureUrl);

        fileMapper.insert(saveFile);

        return novelUrl + "\n" + briefUrl + "\n" + pictureUrl;
//        return Result.success(novelUrl + "\n" + briefUrl + "\n" + pictureUrl);
    }

    //分页查询接口
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        return Result.success(fileMapper.selectPage(pageNum, pageSize));
    }

    //模糊查询接口
    @GetMapping("/find")
    public Result find(@RequestParam String nname) {
        if (fileMapper.findFile(nname).size() != 0) {
            return Result.success(fileMapper.findFile(nname));
        } else {
            return Result.error(Constants.CODE_400, "没有查询到书籍");
        }
    }

//    @DeleteMapping("/delete/{id}")
//    public Result delete(@PathVariable Integer id) {
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id) {

        String novelUUID = fileMapper.findNovelUrl(id).split("novel/")[1].toString();
        String briefUUID = fileMapper.findBriefUrl(id).split("brief/")[1].toString();
        String pictureUUID = fileMapper.findPictureUrl(id).split("picture/")[1].toString();


        System.out.println(novelUUID);
        System.out.println(briefUUID);
        System.out.println(pictureUUID);

        File novel = new File(fileUploadPath + novelUUID);
        File brief = new File(fileUploadPath + briefUUID);
        File picture = new File(fileUploadPath + pictureUUID);

        if (fileMapper.findNovel(id).size() != 0) {
            fileMapper.delete(id);
            if (novel.exists()) {
                novel.delete();
            } else {
                return Result.error(Constants.CODE_400, "novel删除失败");
            }
            if (brief.exists()) {
                brief.delete();
            } else {
                return Result.error(Constants.CODE_400, "brief删除失败");
            }
            if (picture.exists()) {
                brief.delete();
            } else {
                return Result.error(Constants.CODE_400, "picture删除失败");
            }
            return Result.success();
        } else {
            return Result.error(Constants.CODE_400, "该书不存在");
        }
    }

    @GetMapping("/classify/{classify}")
    public Result classifyNovel(@PathVariable String classify) {
        return Result.success(fileMapper.classifyNovel(classify));
    }
}
