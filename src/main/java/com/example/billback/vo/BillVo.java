package com.example.billback.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.billback.entity.Bill;
import com.example.billback.entity.Tag;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: bgt
 * @Date: 2025/4/1 16:09
 * @Desc:
 */
@Data
@Accessors(chain = true)
public class BillVo extends Bill {
    @TableField(exist = false)
    private List<Tag> tags;
}
