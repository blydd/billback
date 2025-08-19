-- 账单表
CREATE TABLE `bill` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `user_id` int NOT NULL COMMENT '用户id',
                         `amount` decimal(10,2) NOT NULL COMMENT '金额',
                         `bill_date` timestamp NOT NULL COMMENT '账单时间',
                         `desc` text COMMENT '备注',
                         `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                         `inout_type` varchar(50) DEFAULT NULL COMMENT '收支类型:支出,入账,不计入',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账单表';
-- 标签表
CREATE TABLE `tag` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(50)  NOT NULL COMMENT '标签名称',
                        `inout_type` varchar(50) NOT NULL COMMENT '收支类型:支出,入账,不计入',
                        `user_id` int DEFAULT NULL COMMENT '用户id',
                        `tag_type` varchar(50) DEFAULT NULL COMMENT '标签类型:支付方式(微信支付宝等),账单类型(衣食住行等)',
                        `account_type` varchar(50) DEFAULT NULL COMMENT '账户类型:储蓄账户,信用账户',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签表';
-- 账单标签关联表
CREATE TABLE `bill_tag` (
                             `bill_id` int DEFAULT NULL,
                             `tag_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- 用户表
CREATE TABLE `user` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `username` varchar(50)  NOT NULL COMMENT '用户名称',
                          `password` varchar(50)  NOT NULL COMMENT '密码',
                         `sex` varchar(50)  NOT NULL COMMENT '性别：男女',
                         `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;