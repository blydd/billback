package com.example.billback.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.billback.vo.BillVo;
import com.example.billback.common.Result;
import com.example.billback.dto.BillDto;
import com.example.billback.entity.Bill;
import com.example.billback.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.billback.common.Constant.USER_ID;

/**
 * 账单管理控制器
 */
@RestController
@RequestMapping("/api/bills")
public class BillController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    /**
     * 查询用户的账单列表 done
     * 
     * @param billDto
     */
    @PostMapping("/query")
    public Result<List<BillVo>> list(@RequestBody BillDto billDto, HttpServletRequest request) {
        billDto.setUserId(Long.valueOf(String.valueOf(request.getAttribute(USER_ID))));
        return Result.success(billService.getBills(billDto));
    }

    /**
     * 根据ID查询单个账单
     * 
     * @param id 账单ID
     * @return 账单详细信息
     */
    @GetMapping("/{id}")
    public Result<BillVo> getById(@PathVariable Long id, HttpServletRequest request) {
        logger.info("查询账单详情，ID：{}", id);
        return Result.success(billService.getOneById(id));
    }

    /**
     * 创建新账单 done
     * 
     * @param bill 账单信息
     * @return 创建成功的账单信息
     */
    @PostMapping("/save")
    public Result<Bill> save(@RequestBody BillDto bill, HttpServletRequest request) {
        logger.info("创建新账单：{}", bill);
        //如果request中的USER_ID为1，返回错误信息，状态码100
        if (Long.valueOf(String.valueOf(request.getAttribute(USER_ID))) == 1) {
            return Result.error( 100,"请先登录");
        }
        try {
            bill.setUserId(Long.valueOf(String.valueOf(request.getAttribute(USER_ID))));
            Bill savedBill = billService.saveBillWithTags(bill);
            logger.info("账单创建成功，ID：{}", savedBill.getId());
            return Result.success(savedBill);
        } catch (Exception e) {
            logger.error("创建账单失败", e);
            return Result.error("创建账单失败：" + e.getMessage());
        }
    }

    /**
     * 更新账单信息
     * 
     * @param id 账单ID
     * @param bill 更新的账单信息

     * @return 更新成功的响应
     */
    @PutMapping("/update/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody BillDto bill, HttpServletRequest request) {
        //如果request中的USER_ID为1，返回错误信息，状态码100
        if (Long.valueOf(String.valueOf(request.getAttribute(USER_ID))) == 1) {
            return Result.error( 100,"请先登录");
        }
        try {
            bill.setUserId(Long.valueOf(String.valueOf(request.getAttribute(USER_ID))));
            bill.setId(id);
            billService.updateBillWithTags(bill);
            logger.info("账单更新成功，ID：{}", id);
            return Result.success();
        } catch (Exception e) {
            logger.error("更新账单失败，ID：{}", id, e);
            return Result.error("更新账单失败：" + e.getMessage());
        }
    }

    /**
     * 删除账单
     * 
     * @param id 要删除的账单ID
     * @return 删除成功的响应
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        //如果request中的USER_ID为1，返回错误信息，状态码100
        if (Long.valueOf(String.valueOf(request.getAttribute(USER_ID))) == 1) {
            return Result.error( 100,"请先登录");
        }
        logger.info("删除账单，ID：{}", id);
        try {
            billService.deleteBillWithTags(id);
            logger.info("账单删除成功，ID：{}", id);
            return Result.success();
        } catch (Exception e) {
            logger.error("删除账单失败，ID：{}", id, e);
            return Result.error("删除账单失败：" + e.getMessage());
        }
    }
} 