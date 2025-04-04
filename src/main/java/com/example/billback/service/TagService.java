package com.example.billback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.billback.entity.Tag;

public interface TagService extends IService<Tag> {
    void saveTag(Tag tag);
}