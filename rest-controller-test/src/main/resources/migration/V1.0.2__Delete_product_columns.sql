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

ALTER TABLE demo_product DROP product_vendor;
ALTER TABLE demo_product DROP product_code;
ALTER TABLE demo_product DROP product_desc;
ALTER TABLE demo_product DROP product_scale;
ALTER TABLE demo_product DROP msrp;