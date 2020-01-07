/*
*********************************************************************
Modify from http://www.yiibai.com/mysql/
*********************************************************************
Name: MySQL Sample Database demo
Link: http://www.yiibai.com/mysql/sample-database.html
*********************************************************************
*/

USE `unittest`;

-- ----------------------------
-- Table structure for `demo_product`
-- ----------------------------
CREATE TABLE `demo_product`
(
    `id`               bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `product_line_id`  bigint(20)     NOT NULL,
    `product_name`     varchar(70)    NOT NULL COMMENT '产品名称',
    `quantity_inStock` smallint(6)    NOT NULL COMMENT '库存',
    `buy_price`        decimal(10, 2) NOT NULL COMMENT '进货价格',
    `expiration_date`  datetime       NOT NULL COMMENT '过期时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='产品';