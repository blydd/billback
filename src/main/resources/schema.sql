CREATE TABLE `bills` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `user_id` int NOT NULL COMMENT '用户id',
                         `amount` decimal(10,2) NOT NULL COMMENT '金额',
                         `bill_date` timestamp NOT NULL COMMENT '账单时间',
                         `desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '备注',
                         `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                         `type` int DEFAULT NULL COMMENT '收支类型:1-支出,2-入账',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='账单表';

CREATE TABLE `tags` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
                        `inout_type` int NOT NULL COMMENT '收支类型:1-支出,2-入账,3-不计入收支',
                        `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图标',
                        `user_id` int DEFAULT NULL COMMENT '用户id',
                        `tag_type` int DEFAULT NULL COMMENT '标签类型:1-支付方式,2-账单类型',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签表';

CREATE TABLE `bill_tags` (
                             `bill_id` int DEFAULT NULL,
                             `tag_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;