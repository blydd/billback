package com.example.billback.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.billback.common.Result;
import com.example.billback.entity.Tag;
import com.example.billback.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.billback.common.Constant.USER_ID;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/query")
    public Result<List<Tag>> list(HttpServletRequest request) {

        return Result.success(tagService.list(Wrappers.<Tag>lambdaQuery().eq(Tag::getUserId, request.getAttribute(USER_ID))));
    }

    @GetMapping("/{id}")
    public Result<Tag> getById(@PathVariable Long id) {
        return Result.success(tagService.getById(id));
    }

    @PostMapping("/save")
    public Result<Tag> save(@RequestBody Tag tag,HttpServletRequest request) {
        //如果request中的USER_ID为1，返回错误信息，状态码100
        if (Long.valueOf(String.valueOf(request.getAttribute(USER_ID))) == 1) {
            return Result.error( 100,"请先登录");
        }
        tag.setUserId(Long.valueOf(String.valueOf(request.getAttribute(USER_ID))));
        tagService.saveTag(tag);
        return Result.success(tag);
    }

    @PutMapping("/update/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody Tag tag,HttpServletRequest request) {
        //如果request中的USER_ID为1，返回错误信息，状态码100
        if (Long.valueOf(String.valueOf(request.getAttribute(USER_ID))) == 1) {
            return Result.error( 100,"请先登录");
        }
        tag.setId(id);
        tagService.updateById(tag);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id,HttpServletRequest request) {
        //如果request中的USER_ID为1，返回错误信息，状态码100
        if (Long.valueOf(String.valueOf(request.getAttribute(USER_ID))) == 1) {
            return Result.error( 100,"请先登录");
        }
        Tag tag = tagService.getById(id);
        if (tag.getInoutType() == 3 && StrUtil.equals("还信用卡",tag.getName())){
            throw new RuntimeException("系统标签不可删除!");
        }
        tagService.removeById(id);
        return Result.success();
    }
} 