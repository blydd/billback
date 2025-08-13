package com.example.billback.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.billback.common.Result;
import com.example.billback.entity.Tag;
import com.example.billback.entity.User;
import com.example.billback.enums.AccountTypeEnum;
import com.example.billback.enums.InoutTypeEnum;
import com.example.billback.enums.TagTypeEnum;
import com.example.billback.service.TagService;
import com.example.billback.service.UserService;
import com.example.billback.vo.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.example.billback.common.Constant.USER_ID;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;
    @Resource
    private UserService userService;


    /**
     * 登录
     * @param user username password
     * @return
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody User user) {
        return userService.wxLogin(user);
    }

    /**
     * 注册用户
     * @param user username password sex
     * @return
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 条件查询标签
     * @param tag
     * @return
     */
    @PostMapping("/query")
    public Result<List<Tag>> list(Tag tag) {

        return Result.success(tagService.list(Wrappers.<Tag>lambdaQuery()
                .eq(Objects.nonNull(tag.getUserId()), Tag::getUserId, tag.getUserId())
                .eq(StrUtil.isNotBlank(tag.getTagType()), Tag::getTagType, tag.getTagType())
                .eq(StrUtil.isNotBlank(tag.getInoutType()), Tag::getInoutType, tag.getInoutType())
        ));
    }

    /**
     * 根据id查询标签详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Tag> getById(@PathVariable Long id) {
        return Result.success(tagService.getById(id));
    }

    /**
     * 保存标签
     * @param tag
     * @return
     */
    @PostMapping
    public Result<Tag> save(@RequestBody Tag tag,HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(USER_ID);
        tag.setUserId(userId);
        if (StrUtil.isBlank(tag.getName())){
            return Result.error( 100,"标签名称不能为空");
        }
        if (StrUtil.isBlank(tag.getTagType())){
            return Result.error( 100,"标签类型不能为空");
        }
        if (StrUtil.equals(TagTypeEnum.PAYMENT_METHOD.getType(), tag.getTagType())){
            if (StrUtil.isBlank(tag.getInoutType())){
                return Result.error( 100,"收支类型不能为空");
            }
            if (!InoutTypeEnum.check(tag.getInoutType())){
                return Result.error( 100,"收支类型不正确");
            }
            if (StrUtil.isBlank(tag.getAccountType())){
                return Result.error( 100,"账户类型不能为空");
            }
            if (!AccountTypeEnum.check(tag.getAccountType())){
                return Result.error( 100,"账户类型不正确");
            }
        }

        if (!TagTypeEnum.check(tag.getTagType())){
            return Result.error( 100,"标签类型不正确");
        }

        //判断是否重复
        Tag byName = tagService.getOne(Wrappers.<Tag>lambdaQuery()
                .eq(Tag::getName, tag.getName())
                .eq(Tag::getTagType, tag.getTagType())
                .eq(Tag::getAccountType, tag.getAccountType())
                .eq(Tag::getInoutType, tag.getInoutType())
                .eq(Tag::getUserId, userId));
        if (Objects.nonNull(byName)){
            return Result.error( 100,"标签已存在");
        }

        tagService.save(tag);
        return Result.success(tag);
    }

    /**
     * 修改标签
     * @param tag
     * @return
     */
    @PutMapping
    public Result<Void> update(@RequestBody Tag tag,HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute(USER_ID);
        tag.setUserId(userId);
        if (Objects.isNull(tag.getId())){
            return Result.error( 100,"id缺失");
        }
        Tag byId = tagService.getById(tag.getId());
        if (Objects.isNull(byId)){
            return Result.error( 100,"标签不存在");
        }
        if (!Objects.equals(byId.getUserId(),userId)){
            return Result.error( 100,"无权限");
        }
        if (!StrUtil.isAllNotBlank(tag.getName(),tag.getInoutType(),tag.getTagType(),tag.getAccountType())){
            return Result.error( 100,"参数缺失");
        }
        if (!InoutTypeEnum.check(tag.getInoutType())){
            return Result.error( 100,"收支类型不正确");
        }
        if (!TagTypeEnum.check(tag.getTagType())){
            return Result.error( 100,"标签类型不正确");
        }
        if (!AccountTypeEnum.check(tag.getAccountType())){
            return Result.error( 100,"账户类型不正确");
        }
        //判断是否重复
        Tag byName = tagService.getOne(Wrappers.<Tag>lambdaQuery()
                .eq(Tag::getName, tag.getName())
                .eq(Tag::getTagType, tag.getTagType())
                .eq(Tag::getAccountType, tag.getAccountType())
                .eq(Tag::getInoutType, tag.getInoutType())
                .eq(Tag::getUserId, userId));
        if (Objects.nonNull(byName) && !Objects.equals(byName.getId(),tag.getId())){
            return Result.error( 100,"标签已存在");
        }
        tagService.updateById(tag);
        return Result.success();
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        Tag tag = tagService.getById(id);
        if (Objects.isNull(tag)){
            return Result.error( 100,"标签不存在");
        }
        //查询是否有关联账单
        
        tagService.removeById(id);
        return Result.success();
    }
} 