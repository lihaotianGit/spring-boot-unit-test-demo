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

ALTER TABLE demo_product MODIFY COLUMN expiration_date DATETIME NOT NULL COMMENT '过期时间'