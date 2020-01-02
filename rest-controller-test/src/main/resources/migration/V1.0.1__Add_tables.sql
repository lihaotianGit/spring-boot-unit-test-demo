/*
*********************************************************************
Modify from http://www.yiibai.com/mysql/
*********************************************************************
Name: MySQL Sample Database demo
Link: http://www.yiibai.com/mysql/sample-database.html
*********************************************************************
*/

-- ----------------------------
-- Table structure for `demo_product_line`
-- ----------------------------

USE `unittest`;

DROP TABLE IF EXISTS `demo_product_line`;
CREATE TABLE `demo_product_line`
(
    `id`           bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `product_line` varchar(50) NOT NULL,
    `text_desc`    varchar(4000) DEFAULT NULL,
    `html_desc`    mediumtext,
    `image`        mediumblob,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='产品线';

-- ----------------------------
-- Table structure for `demo_product`
-- ----------------------------
DROP TABLE IF EXISTS `demo_product`;
CREATE TABLE `demo_product`
(
    `id`               bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `product_line_id`  bigint(20)     NOT NULL,
    `product_code`     varchar(15)    NOT NULL DEFAULT '' COMMENT '产品代码',
    `product_name`     varchar(70)    NOT NULL COMMENT '产品名称',
    `product_scale`    varchar(10)    NOT NULL COMMENT '规格',
    `product_vendor`   varchar(50)    NOT NULL COMMENT '供应商',
    `product_desc`     text           NOT NULL,
    `quantity_inStock` smallint(6)    NOT NULL COMMENT '库存',
    `buy_price`        decimal(10, 2) NOT NULL COMMENT '进货价格',
    `msrp`             decimal(10, 2) NOT NULL COMMENT '建议零售价',
    `expiration_date`  timestamp      NOT NULL COMMENT '过期时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='产品';