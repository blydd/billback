package com.example.billback.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.billback.entity.Tag;
import com.example.billback.mapper.TagMapper;
import com.example.billback.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public void saveTag(Tag tag) {
        //根据类型和名字查询是否存在,存在则不新增
        List<Tag> list = this.list(Wrappers.<Tag>lambdaQuery()
                .eq(Tag::getTagType, tag.getTagType())
                .eq(Tag::getName, tag.getName())
                .eq(Tag::getInoutType, tag.getInoutType())
                .eq(Tag::getAccountType, tag.getAccountType()));
        if (CollUtil.isEmpty(list)){
            this.save(tag);
        }
    }
}