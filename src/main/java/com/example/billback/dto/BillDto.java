package com.example.billback.dto;

import com.example.billback.entity.Bill;
import lombok.Data;

import java.util.List;

/**
 * @author: bgt
 * @Date: 2025/4/1 15:52
 * @Desc:
 */
@Data
public class BillDto extends Bill {

    private String month;





    private List<Long> tagIds;
}
