/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : ds_pub

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 24/08/2023 20:29:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_province
-- ----------------------------
DROP TABLE IF EXISTS `base_province`;
CREATE TABLE `base_province`  (
  `id` bigint(0) NULL DEFAULT NULL COMMENT 'id',
  `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '省名称',
  `region_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地区id',
  `area_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地区编码',
  `iso_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '国际编码'
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_province
-- ----------------------------
INSERT INTO `base_province` VALUES (1, '北京', '1', '110000', 'CN-11');
INSERT INTO `base_province` VALUES (2, '天津市', '1', '120000', 'CN-12');
INSERT INTO `base_province` VALUES (3, '山西', '1', '140000', 'CN-14');
INSERT INTO `base_province` VALUES (4, '内蒙古', '1', '150000', 'CN-15');
INSERT INTO `base_province` VALUES (5, '河北', '1', '130000', 'CN-13');
INSERT INTO `base_province` VALUES (6, '上海', '2', '310000', 'CN-31');
INSERT INTO `base_province` VALUES (7, '江苏', '2', '320000', 'CN-32');
INSERT INTO `base_province` VALUES (8, '浙江', '2', '330000', 'CN-33');
INSERT INTO `base_province` VALUES (9, '安徽', '2', '340000', 'CN-34');
INSERT INTO `base_province` VALUES (10, '福建', '2', '350000', 'CN-35');
INSERT INTO `base_province` VALUES (11, '江西', '2', '360000', 'CN-36');
INSERT INTO `base_province` VALUES (12, '山东', '2', '370000', 'CN-37');
INSERT INTO `base_province` VALUES (14, '台湾', '2', '710000', 'CN-71');
INSERT INTO `base_province` VALUES (15, '黑龙江', '3', '230000', 'CN-23');
INSERT INTO `base_province` VALUES (16, '吉林', '3', '220000', 'CN-22');
INSERT INTO `base_province` VALUES (17, '辽宁', '3', '210000', 'CN-21');
INSERT INTO `base_province` VALUES (18, '陕西', '7', '610000', 'CN-61');
INSERT INTO `base_province` VALUES (19, '甘肃', '7', '620000', 'CN-62');
INSERT INTO `base_province` VALUES (20, '青海', '7', '630000', 'CN-63');
INSERT INTO `base_province` VALUES (21, '宁夏', '7', '640000', 'CN-64');
INSERT INTO `base_province` VALUES (22, '新疆', '7', '650000', 'CN-65');
INSERT INTO `base_province` VALUES (23, '河南', '4', '410000', 'CN-41');
INSERT INTO `base_province` VALUES (24, '湖北', '4', '420000', 'CN-42');
INSERT INTO `base_province` VALUES (25, '湖南', '4', '430000', 'CN-43');
INSERT INTO `base_province` VALUES (26, '广东', '5', '440000', 'CN-44');
INSERT INTO `base_province` VALUES (27, '广西', '5', '450000', 'CN-45');
INSERT INTO `base_province` VALUES (28, '海南', '5', '460000', 'CN-46');
INSERT INTO `base_province` VALUES (29, '香港', '5', '810000', 'CN-91');
INSERT INTO `base_province` VALUES (30, '澳门', '5', '820000', 'CN-92');
INSERT INTO `base_province` VALUES (31, '四川', '6', '510000', 'CN-51');
INSERT INTO `base_province` VALUES (32, '贵州', '6', '520000', 'CN-52');
INSERT INTO `base_province` VALUES (33, '云南', '6', '530000', 'CN-53');
INSERT INTO `base_province` VALUES (13, '重庆', '6', '500000', 'CN-50');
INSERT INTO `base_province` VALUES (34, '西藏', '6', '540000', 'CN-54');

-- ----------------------------
-- Table structure for base_region
-- ----------------------------
DROP TABLE IF EXISTS `base_region`;
CREATE TABLE `base_region`  (
  `id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地区id',
  `region_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地区名称'
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_region
-- ----------------------------
INSERT INTO `base_region` VALUES ('1', '华北');
INSERT INTO `base_region` VALUES ('2', '华东');
INSERT INTO `base_region` VALUES ('3', '东北');
INSERT INTO `base_region` VALUES ('4', '华中');
INSERT INTO `base_region` VALUES ('5', '华南');
INSERT INTO `base_region` VALUES ('6', '西南');
INSERT INTO `base_region` VALUES ('7', '西北');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` bigint(0) NULL DEFAULT NULL COMMENT '订单编号（对应订单信息表id）',
  `sku_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id（对应商品表id）',
  `sku_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `img_url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `order_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '购买价格(下单时的商品价格）',
  `sku_num` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '购买数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `source_type` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '来源类型',
  `source_id` bigint(0) NULL DEFAULT NULL COMMENT '来源编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 202910 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (8621, 3443, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '1', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8622, 3444, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '2', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8623, 3445, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '3', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8624, 3446, 5, '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg', 'http://INNButKuTkslasORPBmHACnJuccfsxmRxNuMznPm', 244.00, '1', '2020-04-25 18:47:14', '2402', 19);
INSERT INTO `order_detail` VALUES (8625, 3446, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '3', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8626, 3447, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '3', '2020-04-25 18:47:14', '2404', 2);
INSERT INTO `order_detail` VALUES (8627, 3448, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '1', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8628, 3449, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '1', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8629, 3450, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '3', '2020-04-25 18:47:14', '2402', 83);
INSERT INTO `order_detail` VALUES (8630, 3451, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8631, 3452, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '1', '2020-04-25 18:47:14', '2403', NULL);
INSERT INTO `order_detail` VALUES (8632, 3453, 16, '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 235.00, '2', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8633, 3454, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '2', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8634, 3455, 5, '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg', 'http://INNButKuTkslasORPBmHACnJuccfsxmRxNuMznPm', 244.00, '3', '2020-04-25 18:47:14', '2403', NULL);
INSERT INTO `order_detail` VALUES (8635, 3456, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '1', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8636, 3457, 13, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 7299.00, '3', '2020-04-25 18:47:14', '2404', 2);
INSERT INTO `order_detail` VALUES (8637, 3458, 5, '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg', 'http://INNButKuTkslasORPBmHACnJuccfsxmRxNuMznPm', 244.00, '2', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8638, 3459, 2, 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）', 'http://JfJSvAnPkErYPcUsbgCuokhjxKiLeqpDXakZqFeE', 3321.00, '1', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8639, 3460, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '2', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8640, 3461, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '1', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8641, 3462, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '1', '2020-04-25 18:47:14', '2402', 46);
INSERT INTO `order_detail` VALUES (8642, 3463, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '3', '2020-04-25 18:47:14', '2402', 24);
INSERT INTO `order_detail` VALUES (8643, 3464, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '3', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8644, 3465, 13, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 7299.00, '2', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8645, 3466, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '1', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8646, 3467, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '3', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8647, 3468, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8648, 3469, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8649, 3470, 2, 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）', 'http://JfJSvAnPkErYPcUsbgCuokhjxKiLeqpDXakZqFeE', 3321.00, '1', '2020-04-25 18:47:14', '2401', NULL);
INSERT INTO `order_detail` VALUES (8650, 3471, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '2', '2020-04-25 18:47:14', '2402', 25);
INSERT INTO `order_detail` VALUES (8651, 3472, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '2', '2020-04-25 18:47:14', '2404', 2);
INSERT INTO `order_detail` VALUES (8652, 3473, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '1', '2020-04-25 18:47:14', '2404', 1);
INSERT INTO `order_detail` VALUES (8653, 3474, 13, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 7299.00, '2', '2020-04-26 18:48:15', '2402', 72);
INSERT INTO `order_detail` VALUES (8654, 3475, 2, 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）', 'http://JfJSvAnPkErYPcUsbgCuokhjxKiLeqpDXakZqFeE', 3321.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8655, 3476, 2, 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）', 'http://JfJSvAnPkErYPcUsbgCuokhjxKiLeqpDXakZqFeE', 3321.00, '1', '2020-04-26 18:48:15', '2402', 17);
INSERT INTO `order_detail` VALUES (8656, 3477, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-26 18:48:15', '2402', 15);
INSERT INTO `order_detail` VALUES (8657, 3478, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8658, 3479, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '2', '2020-04-26 18:48:15', '2403', NULL);
INSERT INTO `order_detail` VALUES (8659, 3480, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8660, 3481, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '1', '2020-04-26 18:48:15', '2404', 2);
INSERT INTO `order_detail` VALUES (8661, 3482, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8662, 3483, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '3', '2020-04-26 18:48:15', '2402', 63);
INSERT INTO `order_detail` VALUES (8663, 3484, 3, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', 3100.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8664, 3485, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8665, 3486, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8666, 3487, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '3', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8667, 3488, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '3', '2020-04-26 18:48:15', '2402', 67);
INSERT INTO `order_detail` VALUES (8668, 3489, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8669, 3490, 16, '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 235.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8670, 3491, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '3', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8671, 3492, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8672, 3493, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8673, 3494, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '3', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8674, 3495, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-26 18:48:15', '2404', 1);
INSERT INTO `order_detail` VALUES (8675, 3496, 3, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', 3100.00, '1', '2020-04-26 18:48:15', '2403', NULL);
INSERT INTO `order_detail` VALUES (8676, 3497, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8677, 3498, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '2', '2020-04-26 18:48:15', '2403', NULL);
INSERT INTO `order_detail` VALUES (8678, 3499, 5, '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg', 'http://INNButKuTkslasORPBmHACnJuccfsxmRxNuMznPm', 244.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8679, 3499, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '3', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8680, 3500, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '1', '2020-04-26 18:48:15', '2402', 17);
INSERT INTO `order_detail` VALUES (8681, 3501, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8682, 3502, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '3', '2020-04-26 18:48:15', '2404', 2);
INSERT INTO `order_detail` VALUES (8683, 3503, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '1', '2020-04-26 18:48:15', '2402', 76);
INSERT INTO `order_detail` VALUES (8684, 3504, 16, '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 235.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8685, 3505, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '1', '2020-04-26 18:48:15', '2402', 13);
INSERT INTO `order_detail` VALUES (8686, 3506, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8687, 3507, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8688, 3508, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8689, 3509, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '2', '2020-04-26 18:48:15', '2402', 100);
INSERT INTO `order_detail` VALUES (8690, 3510, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8691, 3511, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '3', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8692, 3512, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '3', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8693, 3513, 13, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 7299.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8694, 3514, 13, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 7299.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8695, 3515, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8696, 3516, 3, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', 3100.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8697, 3517, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '2', '2020-04-26 18:48:15', '2402', 51);
INSERT INTO `order_detail` VALUES (8698, 3518, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '2', '2020-04-26 18:48:15', '2402', 20);
INSERT INTO `order_detail` VALUES (8699, 3519, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8700, 3520, 16, '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 235.00, '3', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8701, 3521, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8702, 3522, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8703, 3523, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '3', '2020-04-26 18:48:15', '2403', NULL);
INSERT INTO `order_detail` VALUES (8704, 3524, 13, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 7299.00, '3', '2020-04-26 18:48:15', '2402', 66);
INSERT INTO `order_detail` VALUES (8705, 3525, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '2', '2020-04-26 18:48:15', '2402', 48);
INSERT INTO `order_detail` VALUES (8706, 3526, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '1', '2020-04-26 18:48:15', '2404', 1);
INSERT INTO `order_detail` VALUES (8707, 3527, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '3', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8708, 3528, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8709, 3529, 3, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', 3100.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8710, 3530, 3, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', 3100.00, '2', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8711, 3531, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8712, 3532, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8713, 3533, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '1', '2020-04-26 18:48:15', '2402', 64);
INSERT INTO `order_detail` VALUES (8714, 3534, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-26 18:48:15', '2401', NULL);
INSERT INTO `order_detail` VALUES (8715, 3535, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:48:15', '2402', 77);
INSERT INTO `order_detail` VALUES (8716, 3536, 16, '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 235.00, '3', '2020-04-26 18:48:15', '2402', 51);
INSERT INTO `order_detail` VALUES (8717, 3537, 13, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 7299.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8718, 3538, 13, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 7299.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8719, 3539, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '2', '2020-04-26 18:55:01', '2402', 28);
INSERT INTO `order_detail` VALUES (8720, 3540, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8721, 3541, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-26 18:55:01', '2402', 33);
INSERT INTO `order_detail` VALUES (8722, 3542, 16, '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 235.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8723, 3542, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '1', '2020-04-26 18:55:01', '2402', 69);
INSERT INTO `order_detail` VALUES (8724, 3543, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '1', '2020-04-26 18:55:01', '2402', 99);
INSERT INTO `order_detail` VALUES (8725, 3544, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8726, 3545, 3, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', 3100.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8727, 3546, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8728, 3547, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '2', '2020-04-26 18:55:01', '2403', NULL);
INSERT INTO `order_detail` VALUES (8729, 3548, 5, '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg', 'http://INNButKuTkslasORPBmHACnJuccfsxmRxNuMznPm', 244.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8730, 3549, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8731, 3550, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8732, 3551, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8733, 3552, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '3', '2020-04-26 18:55:01', '2404', 1);
INSERT INTO `order_detail` VALUES (8734, 3553, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8735, 3554, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8736, 3555, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8737, 3556, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '1', '2020-04-26 18:55:01', '2402', 32);
INSERT INTO `order_detail` VALUES (8738, 3556, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8739, 3557, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8740, 3558, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:55:01', '2403', NULL);
INSERT INTO `order_detail` VALUES (8741, 3558, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-26 18:55:01', '2404', 2);
INSERT INTO `order_detail` VALUES (8742, 3559, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8743, 3560, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8744, 3561, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8745, 3562, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8746, 3563, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '2', '2020-04-26 18:55:01', '2404', 1);
INSERT INTO `order_detail` VALUES (8747, 3564, 3, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', 3100.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8748, 3565, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-26 18:55:01', '2402', 67);
INSERT INTO `order_detail` VALUES (8749, 3566, 3, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', 3100.00, '1', '2020-04-26 18:55:01', '2402', 39);
INSERT INTO `order_detail` VALUES (8750, 3567, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '1', '2020-04-26 18:55:01', '2402', 68);
INSERT INTO `order_detail` VALUES (8751, 3568, 13, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 7299.00, '3', '2020-04-26 18:55:01', '2402', 43);
INSERT INTO `order_detail` VALUES (8752, 3569, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8753, 3570, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8754, 3571, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8755, 3572, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8756, 3573, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8757, 3574, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8758, 3575, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8759, 3576, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8760, 3577, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8761, 3578, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '3', '2020-04-26 18:55:01', '2404', 1);
INSERT INTO `order_detail` VALUES (8762, 3579, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-26 18:55:01', '2402', 74);
INSERT INTO `order_detail` VALUES (8763, 3580, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8764, 3581, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8765, 3582, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8766, 3583, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8767, 3584, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '3', '2020-04-26 18:55:01', '2402', 95);
INSERT INTO `order_detail` VALUES (8768, 3585, 2, 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）', 'http://JfJSvAnPkErYPcUsbgCuokhjxKiLeqpDXakZqFeE', 3321.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8769, 3586, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-26 18:55:01', '2404', 1);
INSERT INTO `order_detail` VALUES (8770, 3587, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '2', '2020-04-26 18:55:01', '2402', 59);
INSERT INTO `order_detail` VALUES (8771, 3588, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8772, 3589, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '1', '2020-04-26 18:55:01', '2402', 11);
INSERT INTO `order_detail` VALUES (8773, 3590, 2, 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）', 'http://JfJSvAnPkErYPcUsbgCuokhjxKiLeqpDXakZqFeE', 3321.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8774, 3591, 10, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 222.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8775, 3592, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8776, 3593, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8777, 3594, 1, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', 2220.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8778, 3595, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '2', '2020-04-26 18:55:01', '2403', NULL);
INSERT INTO `order_detail` VALUES (8779, 3596, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:55:01', '2402', 96);
INSERT INTO `order_detail` VALUES (8780, 3597, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8781, 3598, 16, '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 235.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8782, 3599, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8783, 3600, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '2', '2020-04-26 18:55:01', '2404', 2);
INSERT INTO `order_detail` VALUES (8784, 3601, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '2', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8785, 3602, 2, 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）', 'http://JfJSvAnPkErYPcUsbgCuokhjxKiLeqpDXakZqFeE', 3321.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8786, 3603, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8787, 3604, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8788, 3605, 5, '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg', 'http://INNButKuTkslasORPBmHACnJuccfsxmRxNuMznPm', 244.00, '1', '2020-04-26 18:55:01', '2403', NULL);
INSERT INTO `order_detail` VALUES (8789, 3606, 2, 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）', 'http://JfJSvAnPkErYPcUsbgCuokhjxKiLeqpDXakZqFeE', 3321.00, '2', '2020-04-26 18:55:01', '2404', 2);
INSERT INTO `order_detail` VALUES (8790, 3607, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8791, 3608, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '1', '2020-04-26 18:55:01', '2403', NULL);
INSERT INTO `order_detail` VALUES (8792, 3609, 16, '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 235.00, '2', '2020-04-26 18:55:01', '2402', 86);
INSERT INTO `order_detail` VALUES (8793, 3610, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:55:01', '2402', 85);
INSERT INTO `order_detail` VALUES (8794, 3611, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8795, 3612, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '3', '2020-04-26 18:55:01', '2404', 2);
INSERT INTO `order_detail` VALUES (8796, 3613, 5, '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg', 'http://INNButKuTkslasORPBmHACnJuccfsxmRxNuMznPm', 244.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8797, 3614, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '1', '2020-04-26 18:55:01', '2403', NULL);
INSERT INTO `order_detail` VALUES (8798, 3615, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '1', '2020-04-26 18:55:01', '2404', 1);
INSERT INTO `order_detail` VALUES (8799, 3616, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8800, 3617, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8801, 3618, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '3', '2020-04-26 18:55:01', '2402', 71);
INSERT INTO `order_detail` VALUES (8802, 3619, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8803, 3620, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '3', '2020-04-26 18:55:01', '2402', 41);
INSERT INTO `order_detail` VALUES (8804, 3621, 7, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', 1553.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8805, 3622, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8806, 3623, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '3', '2020-04-26 18:55:01', '2402', 19);
INSERT INTO `order_detail` VALUES (8807, 3624, 9, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', 2452.00, '3', '2020-04-26 18:55:01', '2402', 11);
INSERT INTO `order_detail` VALUES (8808, 3625, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '3', '2020-04-26 18:55:01', '2404', 1);
INSERT INTO `order_detail` VALUES (8809, 3626, 16, '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 235.00, '1', '2020-04-26 18:55:01', '2403', NULL);
INSERT INTO `order_detail` VALUES (8810, 3627, 11, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6399.00, '1', '2020-04-26 18:55:01', '2401', NULL);
INSERT INTO `order_detail` VALUES (8811, 3628, 6, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', 145.00, '2', '2020-04-26 18:55:01', '2402', 33);
INSERT INTO `order_detail` VALUES (8812, 3629, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '1', '2020-04-26 18:55:01', '2403', NULL);
INSERT INTO `order_detail` VALUES (8813, 3630, 8, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', 8900.00, '1', '2020-04-26 18:55:16', '2401', NULL);
INSERT INTO `order_detail` VALUES (8814, 3631, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '3', '2020-04-26 18:55:16', '2401', NULL);
INSERT INTO `order_detail` VALUES (8815, 3632, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '2', '2020-04-26 18:55:16', '2402', 56);
INSERT INTO `order_detail` VALUES (8816, 3633, 14, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 496.00, '1', '2020-04-26 18:55:16', '2401', NULL);
INSERT INTO `order_detail` VALUES (8817, 3634, 12, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 6699.00, '2', '2020-04-26 18:55:16', '2401', NULL);
INSERT INTO `order_detail` VALUES (8818, 3635, 4, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', 1442.00, '1', '2020-04-26 18:55:16', '2402', 33);
INSERT INTO `order_detail` VALUES (8819, 3636, 3, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', 3100.00, '3', '2020-04-26 18:55:16', '2401', NULL);
INSERT INTO `order_detail` VALUES (8820, 3637, 15, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', 252.00, '2', '2020-04-26 18:55:16', '2401', NULL);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `consignee` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `consignee_tel` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收件人电话',
  `final_total_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '总金额',
  `order_status` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id（对应用户表id）',
  `delivery_address` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '送货地址',
  `order_comment` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `out_trade_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单交易编号（第三方支付用)',
  `trade_body` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单描述（第三方支付用）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `operate_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `tracking_no` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '物流单编号',
  `parent_order_id` bigint(0) NULL DEFAULT NULL COMMENT '父订单编号',
  `img_url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `province_id` int(0) NULL DEFAULT NULL COMMENT '省份id（对应省份表id）',
  `benefit_reduce_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '优惠金额',
  `original_total_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '原价金额',
  `feight_fee` decimal(16, 2) NULL DEFAULT NULL COMMENT '运费',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 85170 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (3443, '严致', '13207871570', 1449.00, '1005', 2790, '第4大街第5号楼4单元464门', '描述345855', '214537477223728', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:59:01', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/117814.jpg', 20, 0.00, 1442.00, 7.00);
INSERT INTO `order_info` VALUES (3444, '慕容亨', '13028730359', 17805.00, '1005', 2015, '第9大街第26号楼3单元383门', '描述948496', '226551358533723', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等2件商品', '2020-04-25 18:47:14', '2020-04-26 18:55:17', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/353392.jpg', 11, 0.00, 17800.00, 5.00);
INSERT INTO `order_info` VALUES (3445, '姚兰凤', '13080315675', 16180.00, '1005', 8263, '第5大街第1号楼7单元722门', '描述148518', '754426449478474', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等3件商品', '2020-04-25 18:47:14', '2020-04-26 18:55:17', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/478856.jpg', 26, 3935.00, 20097.00, 18.00);
INSERT INTO `order_info` VALUES (3446, '柏锦黛', '13487267342', 4922.00, '1005', 7031, '第17大街第40号楼2单元564门', '描述779464', '262955273144195', '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg等4件商品', '2020-04-25 18:47:14', '2020-04-26 19:11:37', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/144444.jpg', 30, 0.00, 4903.00, 19.00);
INSERT INTO `order_info` VALUES (3447, '计娴瑾', '13208002474', 6665.00, '1005', 5903, '第4大街第25号楼6单元338门', '描述396659', '689816418657611', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等3件商品', '2020-04-25 18:47:14', '2020-04-25 18:47:14', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/793265.jpg', 29, 0.00, 6660.00, 5.00);
INSERT INTO `order_info` VALUES (3448, '时友裕', '13908519819', 217.00, '1005', 5525, '第19大街第27号楼9单元874门', '描述286614', '675628874311147', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:55:02', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/553516.jpg', 32, 55.00, 252.00, 20.00);
INSERT INTO `order_info` VALUES (3449, '东郭妍', '13289011809', 164.00, '1005', 9321, '第11大街第33号楼6单元645门', '描述368435', '489957278482334', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等1件商品', '2020-04-25 18:47:14', '2020-04-26 23:10:20', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/235333.jpg', 22, 0.00, 145.00, 19.00);
INSERT INTO `order_info` VALUES (3450, '汪毅', '13419873912', 1064.00, '1005', 1088, '第7大街第9号楼2单元723门', '描述774486', '661124816482447', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等3件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/552723.jpg', 28, 432.00, 1488.00, 8.00);
INSERT INTO `order_info` VALUES (3451, '濮阳美', '13389679437', 7367.00, '1005', 4616, '第18大街第14号楼6单元643门', '描述192662', '529153571431837', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等3件商品', '2020-04-25 18:47:14', '2020-04-25 18:47:14', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/885242.jpg', 8, 0.00, 7356.00, 11.00);
INSERT INTO `order_info` VALUES (3452, '伏枫', '13624104576', 2468.00, '1005', 6385, '第18大街第18号楼8单元758门', '描述316723', '476782783569135', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等1件商品', '2020-04-25 18:47:14', '2020-04-25 18:47:14', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/674357.jpg', 12, 0.00, 2452.00, 16.00);
INSERT INTO `order_info` VALUES (3453, '舒盛', '13981290386', 365.00, '1005', 6739, '第17大街第39号楼5单元733门', '描述477362', '633659185243265', '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g等2件商品', '2020-04-25 18:47:14', '2020-04-25 18:47:14', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/515433.jpg', 26, 118.00, 470.00, 13.00);
INSERT INTO `order_info` VALUES (3454, '东方松', '13614792091', 3123.00, '1005', 8880, '第7大街第33号楼9单元768门', '描述868538', '618137194429438', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等2件商品', '2020-04-25 18:47:14', '2020-04-25 18:47:14', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/868134.jpg', 32, 0.00, 3106.00, 17.00);
INSERT INTO `order_info` VALUES (3455, '施媛', '13945466325', 738.00, '1005', 417, '第14大街第27号楼1单元553门', '描述159231', '748996689597244', '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg等3件商品', '2020-04-25 18:47:14', '2020-04-26 18:55:17', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/851963.jpg', 30, 0.00, 732.00, 6.00);
INSERT INTO `order_info` VALUES (3456, '钟离岩', '13866171015', 6003.00, '1005', 8190, '第10大街第4号楼4单元384门', '描述973881', '841545623791642', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:59:01', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/311645.jpg', 27, 416.00, 6399.00, 20.00);
INSERT INTO `order_info` VALUES (3457, '成天达', '13188716679', 12205.00, '1005', 8921, '第8大街第32号楼8单元146门', '描述348679', '895524654126494', '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰等3件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/488373.jpg', 9, 9704.00, 21897.00, 12.00);
INSERT INTO `order_info` VALUES (3458, '何亮', '13254191941', 504.00, '1005', 6956, '第8大街第1号楼1单元823门', '描述147276', '762295464259983', '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg等2件商品', '2020-04-25 18:47:14', '2020-04-26 18:59:01', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/352685.jpg', 2, 0.00, 488.00, 16.00);
INSERT INTO `order_info` VALUES (3459, '舒杰', '13895862911', 3340.00, '1005', 5137, '第6大街第17号楼8单元913门', '描述945471', '428427935234469', 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:59:01', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/415751.jpg', 34, 0.00, 3321.00, 19.00);
INSERT INTO `order_info` VALUES (3460, '轩辕巧', '13463093388', 695.00, '1005', 4158, '第17大街第18号楼3单元144门', '描述819591', '913644417385741', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等2件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/221634.jpg', 17, 305.00, 992.00, 8.00);
INSERT INTO `order_info` VALUES (3461, '曹希宁', '13274015910', 162.00, '1005', 5729, '第19大街第3号楼1单元932门', '描述158263', '775883538412211', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/467383.jpg', 18, 0.00, 145.00, 17.00);
INSERT INTO `order_info` VALUES (3462, '濮阳香月', '13536127776', 1572.00, '1005', 6221, '第7大街第22号楼3单元744门', '描述433977', '974611765733658', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:55:02', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/446883.jpg', 2, 0.00, 1553.00, 19.00);
INSERT INTO `order_info` VALUES (3463, '窦眉君', '13653558792', 18861.00, '1005', 2760, '第17大街第11号楼6单元725门', '描述277661', '725479846238691', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等3件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/934246.jpg', 3, 344.00, 19197.00, 8.00);
INSERT INTO `order_info` VALUES (3464, '康梦', '13611032296', 11439.00, '1005', 5464, '第18大街第29号楼8单元732门', '描述819999', '885913584779336', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等3件商品', '2020-04-25 18:47:14', '2020-04-26 23:10:20', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/593729.jpg', 7, 7774.00, 19197.00, 16.00);
INSERT INTO `order_info` VALUES (3465, '东方勇', '13839956857', 7681.00, '1005', 539, '第18大街第28号楼1单元874门', '描述593359', '182756585142448', '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰等2件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/933388.jpg', 32, 6937.00, 14598.00, 20.00);
INSERT INTO `order_info` VALUES (3466, '时洁梅', '13942139526', 420.00, '1005', 85, '第8大街第14号楼2单元474门', '描述276845', '595927521669121', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:55:02', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/855376.jpg', 5, 82.00, 496.00, 6.00);
INSERT INTO `order_info` VALUES (3467, '狄澜纯', '13974048736', 6665.00, '1005', 4537, '第9大街第24号楼6单元678门', '描述357628', '889926862515773', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等3件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/916149.jpg', 10, 0.00, 6660.00, 5.00);
INSERT INTO `order_info` VALUES (3468, '许友', '13376701295', 357.00, '1005', 1029, '第8大街第8号楼1单元237门', '描述252357', '238226754838585', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-25 18:47:14', '2020-04-25 18:47:14', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/637359.jpg', 30, 159.00, 504.00, 12.00);
INSERT INTO `order_info` VALUES (3469, '俞筠柔', '13104784263', 235.00, '1005', 2481, '第1大街第33号楼6单元463门', '描述842281', '547947737184414', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/484651.jpg', 1, 0.00, 222.00, 13.00);
INSERT INTO `order_info` VALUES (3470, '萧舒影', '13442002716', 3339.00, '1005', 8584, '第9大街第16号楼5单元453门', '描述973651', '148911663317583', 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/641278.jpg', 33, 0.00, 3321.00, 18.00);
INSERT INTO `order_info` VALUES (3471, '欧阳楠', '13018243765', 4921.00, '1005', 9858, '第7大街第9号楼6单元978门', '描述199917', '933481749285156', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等2件商品', '2020-04-25 18:47:14', '2020-04-26 18:55:17', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/625242.jpg', 20, 0.00, 4904.00, 17.00);
INSERT INTO `order_info` VALUES (3472, '郝叶', '13392712236', 17813.00, '1005', 4669, '第13大街第17号楼8单元189门', '描述933817', '456589832366633', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等2件商品', '2020-04-25 18:47:14', '2020-04-26 23:24:33', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/412265.jpg', 9, 0.00, 17800.00, 13.00);
INSERT INTO `order_info` VALUES (3473, '曹之轮', '13170110139', 4489.00, '1005', 6995, '第19大街第35号楼7单元783门', '描述416259', '726397471156226', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等1件商品', '2020-04-25 18:47:14', '2020-04-26 18:48:16', '2020-04-25 19:02:14', NULL, NULL, 'http://img.gmall.com/311276.jpg', 29, 2218.00, 6699.00, 8.00);
INSERT INTO `order_info` VALUES (3474, '任斌梁', '13278586939', 11283.00, '1005', 3102, '第15大街第29号楼4单元716门', '描述224736', '886231996174494', '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/187985.jpg', 20, 3331.00, 14598.00, 16.00);
INSERT INTO `order_info` VALUES (3475, '吴航弘', '13864521068', 3328.00, '1005', 1604, '第18大街第14号楼8单元958门', '描述361462', '577844875751679', 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/163575.jpg', 2, 0.00, 3321.00, 7.00);
INSERT INTO `order_info` VALUES (3476, '李怡', '13083847290', 3333.00, '1005', 1826, '第10大街第28号楼1单元712门', '描述895374', '715224259493273', 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）等1件商品', '2020-04-26 18:48:15', '2020-04-26 19:03:49', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/363565.jpg', 2, 0.00, 3321.00, 12.00);
INSERT INTO `order_info` VALUES (3477, '慕容莎锦', '13473945060', 227.00, '1005', 1801, '第5大街第33号楼5单元191门', '描述774858', '112942313359592', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/919292.jpg', 13, 0.00, 222.00, 5.00);
INSERT INTO `order_info` VALUES (3478, '舒荷丹', '13016546699', 11008.00, '1005', 6290, '第1大街第17号楼3单元391门', '描述756147', '239589714562241', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/723568.jpg', 16, 2404.00, 13398.00, 14.00);
INSERT INTO `order_info` VALUES (3479, '姚欣飘', '13119066801', 4458.00, '1005', 869, '第5大街第37号楼5单元734门', '描述836583', '648735574272512', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/726467.jpg', 18, 0.00, 4440.00, 18.00);
INSERT INTO `order_info` VALUES (3480, '闻人康', '13581475857', 2466.00, '1005', 9441, '第2大街第26号楼5单元463门', '描述865485', '126942732796856', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/775414.jpg', 31, 0.00, 2452.00, 14.00);
INSERT INTO `order_info` VALUES (3481, '华致树', '13224466769', 1569.00, '1005', 7388, '第19大街第17号楼5单元831门', '描述197452', '912664613575578', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/839864.jpg', 7, 0.00, 1553.00, 16.00);
INSERT INTO `order_info` VALUES (3482, '苗媛', '13524220350', 255.00, '1005', 3145, '第15大街第3号楼8单元819门', '描述918223', '428441341249118', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:59:01', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/619331.jpg', 6, 6.00, 252.00, 9.00);
INSERT INTO `order_info` VALUES (3483, '尉迟山仁', '13367609185', 6675.00, '1005', 4860, '第6大街第10号楼6单元512门', '描述772653', '366128774198441', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/138262.jpg', 19, 0.00, 6660.00, 15.00);
INSERT INTO `order_info` VALUES (3484, '金时', '13488470752', 3117.00, '1005', 4282, '第18大街第40号楼4单元899门', '描述414744', '326689884714431', '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/945214.jpg', 31, 0.00, 3100.00, 17.00);
INSERT INTO `order_info` VALUES (3485, '郝亮', '13970629363', 233.00, '1005', 3635, '第20大街第12号楼7单元243门', '描述213138', '436712278283827', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/846797.jpg', 32, 0.00, 222.00, 11.00);
INSERT INTO `order_info` VALUES (3486, '蒋影荔', '13866317920', 459.00, '1005', 1024, '第20大街第16号楼9单元188门', '描述113669', '882542765953535', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/214246.jpg', 3, 0.00, 444.00, 15.00);
INSERT INTO `order_info` VALUES (3487, '呼延宏', '13394097991', 15558.00, '1005', 1315, '第2大街第24号楼3单元769门', '描述332353', '823441412566351', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等3件商品', '2020-04-26 18:48:15', '2020-04-26 23:10:20', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/719259.jpg', 3, 3659.00, 19197.00, 20.00);
INSERT INTO `order_info` VALUES (3488, '孟倩婷', '13841649716', 4346.00, '1005', 4579, '第18大街第15号楼9单元927门', '描述362891', '245462627862693', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/744347.jpg', 11, 0.00, 4326.00, 20.00);
INSERT INTO `order_info` VALUES (3489, '萧翰朗', '13321764779', 2227.00, '1005', 506, '第1大街第18号楼7单元195门', '描述847525', '236974498381355', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/654278.jpg', 24, 0.00, 2220.00, 7.00);
INSERT INTO `order_info` VALUES (3490, '顾超浩', '13957810037', 327.00, '1005', 1335, '第11大街第31号楼3单元942门', '描述136944', '162666137759322', '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/215468.jpg', 25, 161.00, 470.00, 18.00);
INSERT INTO `order_info` VALUES (3491, '吴腾', '13521091142', 1038.00, '1005', 3770, '第2大街第7号楼3单元819门', '描述797787', '292516979264183', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:59:01', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/759356.jpg', 8, 467.00, 1488.00, 17.00);
INSERT INTO `order_info` VALUES (3492, '姚卿聪', '13227106664', 4910.00, '1005', 8418, '第14大街第37号楼4单元623门', '描述942522', '339899931474394', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等2件商品', '2020-04-26 18:48:15', '2020-04-26 19:11:37', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/969544.jpg', 34, 0.00, 4904.00, 6.00);
INSERT INTO `order_info` VALUES (3493, '吕彪', '13188078835', 285.00, '1005', 2359, '第19大街第28号楼6单元239门', '描述769842', '333158412854276', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/113873.jpg', 31, 238.00, 504.00, 19.00);
INSERT INTO `order_info` VALUES (3494, '唐霄枫', '13327189353', 448.00, '1005', 453, '第8大街第28号楼2单元548门', '描述591655', '367475151773677', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/791497.jpg', 32, 0.00, 435.00, 13.00);
INSERT INTO `order_info` VALUES (3495, '狄嘉琼', '13052927685', 227.00, '1005', 1755, '第18大街第20号楼5单元998门', '描述616257', '845117933146186', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/897257.jpg', 28, 0.00, 222.00, 5.00);
INSERT INTO `order_info` VALUES (3496, '轩辕若', '13930872019', 3108.00, '1005', 7287, '第4大街第39号楼6单元581门', '描述346945', '713968591292292', '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/257375.jpg', 3, 0.00, 3100.00, 8.00);
INSERT INTO `order_info` VALUES (3497, '李佳嘉', '13443931140', 449.00, '1005', 2383, '第7大街第15号楼4单元341门', '描述287936', '498262597433449', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/242382.jpg', 21, 56.00, 496.00, 9.00);
INSERT INTO `order_info` VALUES (3498, '魏姬', '13310260149', 17815.00, '1005', 5400, '第13大街第13号楼3单元235门', '描述554116', '839298771633358', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/492437.jpg', 2, 0.00, 17800.00, 15.00);
INSERT INTO `order_info` VALUES (3499, '滕纨', '13553137877', 15947.00, '1005', 1605, '第13大街第30号楼4单元733门', '描述146563', '389892525459136', '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg等5件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/122323.jpg', 4, 3743.00, 19685.00, 5.00);
INSERT INTO `order_info` VALUES (3500, '赵广', '13645071763', 1450.00, '1005', 2814, '第15大街第32号楼1单元448门', '描述513519', '129695747411546', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:59:01', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/552239.jpg', 7, 0.00, 1442.00, 8.00);
INSERT INTO `order_info` VALUES (3501, '邬梁栋', '13064807927', 232.00, '1005', 1143, '第11大街第35号楼8单元488门', '描述686584', '215986792136871', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/428714.jpg', 4, 36.00, 252.00, 16.00);
INSERT INTO `order_info` VALUES (3502, '萧健', '13409916885', 806.00, '1005', 4504, '第2大街第18号楼6单元652门', '描述712747', '277232497461671', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/953597.jpg', 22, 688.00, 1488.00, 6.00);
INSERT INTO `order_info` VALUES (3503, '慕容裕', '13243876595', 161.00, '1005', 3003, '第3大街第38号楼6单元682门', '描述352977', '454427428146258', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/346227.jpg', 28, 0.00, 145.00, 16.00);
INSERT INTO `order_info` VALUES (3504, '华策腾', '13866060841', 345.00, '1005', 614, '第1大街第4号楼9单元118门', '描述167911', '331657196776268', '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g等2件商品', '2020-04-26 18:48:15', '2020-04-26 23:10:20', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/567764.jpg', 8, 143.00, 470.00, 18.00);
INSERT INTO `order_info` VALUES (3505, '滕兰', '13940776593', 8907.00, '1005', 9501, '第6大街第38号楼4单元752门', '描述285446', '693679333617633', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:59:01', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/415382.jpg', 33, 0.00, 8900.00, 7.00);
INSERT INTO `order_info` VALUES (3506, '时静淑', '13911907193', 320.00, '1005', 6581, '第16大街第31号楼7单元367门', '描述825711', '446293384981564', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/274332.jpg', 33, 185.00, 496.00, 9.00);
INSERT INTO `order_info` VALUES (3507, '马岚', '13902307691', 2235.00, '1005', 7476, '第9大街第18号楼8单元547门', '描述972833', '616361678614334', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:48:15', '2020-04-26 23:24:33', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/399133.jpg', 9, 0.00, 2220.00, 15.00);
INSERT INTO `order_info` VALUES (3508, '卜士', '13414038783', 449.00, '1005', 993, '第5大街第38号楼5单元948门', '描述991994', '621719322973141', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-26 18:48:15', '2020-04-26 23:54:42', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/637977.jpg', 16, 74.00, 504.00, 19.00);
INSERT INTO `order_info` VALUES (3509, '元娣叶', '13797295745', 2904.00, '1005', 2178, '第12大街第13号楼7单元312门', '描述638394', '821526132576726', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/493138.jpg', 18, 0.00, 2884.00, 20.00);
INSERT INTO `order_info` VALUES (3510, '祁涛', '13633753609', 161.00, '1005', 7353, '第15大街第31号楼6单元619门', '描述352899', '381255972881153', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等1件商品', '2020-04-26 01:46:42', '2020-04-26 01:46:42', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/854931.jpg', 29, 0.00, 145.00, 16.00);
INSERT INTO `order_info` VALUES (3511, '萧眉君', '13373139985', 4334.00, '1005', 1592, '第7大街第9号楼8单元741门', '描述998273', '329776613694292', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/535684.jpg', 16, 0.00, 4326.00, 8.00);
INSERT INTO `order_info` VALUES (3512, '鲍霭凝', '13810462731', 445.00, '1005', 583, '第9大街第23号楼6单元213门', '描述751868', '844161245852374', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/181289.jpg', 3, 0.00, 435.00, 10.00);
INSERT INTO `order_info` VALUES (3513, '方坚', '13911734934', 10505.00, '1005', 1694, '第6大街第15号楼5单元456门', '描述734135', '645734976992765', '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰等2件商品', '2020-04-26 18:48:15', '2020-04-26 19:11:37', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/528556.jpg', 24, 4105.00, 14598.00, 12.00);
INSERT INTO `order_info` VALUES (3514, '魏维', '13692243513', 11911.00, '1005', 2856, '第2大街第40号楼2单元827门', '描述397637', '843158342898143', '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/553818.jpg', 4, 2701.00, 14598.00, 14.00);
INSERT INTO `order_info` VALUES (3515, '百里青倩', '13227337219', 1461.00, '1005', 2093, '第18大街第12号楼8单元176门', '描述661867', '693166851783671', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等1件商品', '2020-04-26 00:31:41', '2020-04-26 00:31:41', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/245613.jpg', 23, 0.00, 1442.00, 19.00);
INSERT INTO `order_info` VALUES (3516, '宇文璧璐', '13881236665', 6212.00, '1005', 6115, '第19大街第35号楼8单元399门', '描述754788', '886389269359638', '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面等2件商品', '2020-04-26 18:48:15', '2020-04-26 19:11:37', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/821592.jpg', 19, 0.00, 6200.00, 12.00);
INSERT INTO `order_info` VALUES (3517, '沈辉', '13594546468', 3112.00, '1005', 6748, '第3大街第1号楼2单元962门', '描述221491', '368823656346233', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等2件商品', '2020-04-26 02:48:41', '2020-04-26 02:48:41', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/669263.jpg', 14, 0.00, 3106.00, 6.00);
INSERT INTO `order_info` VALUES (3518, '令狐筠柔', '13997608322', 4923.00, '1005', 9593, '第9大街第37号楼8单元476门', '描述241771', '484247644322566', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/526596.jpg', 31, 0.00, 4904.00, 19.00);
INSERT INTO `order_info` VALUES (3519, '黄承乐', '13230193266', 2240.00, '1005', 6213, '第7大街第6号楼4单元193门', '描述738636', '647352292877815', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/928694.jpg', 27, 0.00, 2220.00, 20.00);
INSERT INTO `order_info` VALUES (3520, '宋友', '13595559616', 422.00, '1005', 2316, '第19大街第6号楼9单元519门', '描述999789', '319719915285989', '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/523445.jpg', 15, 292.00, 705.00, 9.00);
INSERT INTO `order_info` VALUES (3521, '魏腾', '13989349252', 494.00, '1005', 6382, '第2大街第16号楼8单元241门', '描述543276', '165238894144344', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/555924.jpg', 18, 18.00, 504.00, 8.00);
INSERT INTO `order_info` VALUES (3522, '苏静', '13839929147', 241.00, '1005', 7443, '第12大街第31号楼6单元281门', '描述263972', '238778956524365', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:59:01', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/657468.jpg', 20, 0.00, 222.00, 19.00);
INSERT INTO `order_info` VALUES (3523, '南门士以', '13925940501', 409.00, '1005', 9580, '第16大街第24号楼9单元229门', '描述222118', '686268266662996', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/842537.jpg', 19, 360.00, 756.00, 13.00);
INSERT INTO `order_info` VALUES (3524, '魏思丽', '13899385804', 14259.00, '1005', 2774, '第5大街第5号楼2单元496门', '描述469191', '549126594226973', '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰等3件商品', '2020-04-26 01:18:54', '2020-04-26 01:18:54', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/725325.jpg', 17, 7656.00, 21897.00, 18.00);
INSERT INTO `order_info` VALUES (3525, '南宫茗羽', '13251516080', 2891.00, '1005', 2610, '第2大街第4号楼6单元665门', '描述254892', '295852756563826', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/769425.jpg', 27, 0.00, 2884.00, 7.00);
INSERT INTO `order_info` VALUES (3526, '鲍爱', '13545758591', 3726.00, '1005', 8611, '第10大街第40号楼2单元241门', '描述177982', '721516686151787', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等1件商品', '2020-04-26 18:48:15', '2020-04-26 19:03:49', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/298766.jpg', 18, 2692.00, 6399.00, 19.00);
INSERT INTO `order_info` VALUES (3527, '史海山', '13097160468', 4668.00, '1005', 6589, '第16大街第32号楼5单元523门', '描述428661', '229282372195287', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/629683.jpg', 7, 0.00, 4659.00, 9.00);
INSERT INTO `order_info` VALUES (3528, '东郭家', '13543373281', 7371.00, '1005', 9720, '第1大街第22号楼2单元836门', '描述819464', '547211721315734', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等3件商品', '2020-04-26 18:48:15', '2020-04-26 18:48:16', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/195469.jpg', 20, 0.00, 7356.00, 15.00);
INSERT INTO `order_info` VALUES (3529, '汪珊', '13284838321', 6205.00, '1005', 8437, '第2大街第27号楼6单元569门', '描述599132', '212959288949769', '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面等2件商品', '2020-04-26 18:48:15', '2020-04-26 23:54:42', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/534759.jpg', 20, 0.00, 6200.00, 5.00);
INSERT INTO `order_info` VALUES (3530, '康泽晨', '13309304031', 6207.00, '1005', 96, '第2大街第14号楼4单元922门', '描述375273', '512284781975697', '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/319343.jpg', 19, 0.00, 6200.00, 7.00);
INSERT INTO `order_info` VALUES (3531, '曹雁', '13041109187', 5113.00, '1005', 6814, '第5大街第19号楼9单元561门', '描述898548', '565454369362729', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等1件商品', '2020-04-26 18:48:15', '2020-04-26 23:24:33', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/133184.jpg', 12, 1305.00, 6399.00, 19.00);
INSERT INTO `order_info` VALUES (3532, '汤彩', '13882720383', 1568.00, '1005', 3020, '第8大街第40号楼1单元276门', '描述911951', '674577189754148', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/565624.jpg', 34, 0.00, 1553.00, 15.00);
INSERT INTO `order_info` VALUES (3533, '曹霭', '13723197421', 1569.00, '1005', 472, '第9大街第1号楼3单元642门', '描述827659', '593334148842632', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/224385.jpg', 22, 0.00, 1553.00, 16.00);
INSERT INTO `order_info` VALUES (3534, '钱毅', '13046589040', 227.00, '1005', 9619, '第4大街第22号楼2单元945门', '描述889513', '661432155757988', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:17', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/184496.jpg', 8, 0.00, 222.00, 5.00);
INSERT INTO `order_info` VALUES (3535, '尹玲芬', '13086736180', 324.00, '1005', 5724, '第1大街第15号楼1单元937门', '描述274471', '734864278275346', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-26 18:48:15', '2020-04-26 18:55:02', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/771313.jpg', 11, 199.00, 504.00, 19.00);
INSERT INTO `order_info` VALUES (3536, '陈刚勇', '13275040555', 539.00, '1005', 3552, '第17大街第5号楼1单元821门', '描述666519', '144446717515268', '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g等3件商品', '2020-04-26 18:48:15', '2020-04-26 23:39:40', '2020-04-26 19:03:15', NULL, NULL, 'http://img.gmall.com/383189.jpg', 32, 176.00, 705.00, 10.00);
INSERT INTO `order_info` VALUES (3537, '钟离维启', '13637970077', 16371.00, '1005', 4131, '第12大街第11号楼9单元139门', '描述663421', '863359793386966', '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/525894.jpg', 34, 5539.00, 21897.00, 13.00);
INSERT INTO `order_info` VALUES (3538, '呼延爽琬', '13078195535', 16132.00, '1005', 8778, '第9大街第29号楼6单元961门', '描述326633', '755784824154869', '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/524562.jpg', 4, 5775.00, 21897.00, 10.00);
INSERT INTO `order_info` VALUES (3539, '毛鸣', '13784252329', 586.00, '1005', 7664, '第7大街第7号楼2单元396门', '描述177315', '676939769447775', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/428739.jpg', 2, 420.00, 992.00, 14.00);
INSERT INTO `order_info` VALUES (3540, '赵明永', '13744073629', 6171.00, '1005', 3810, '第17大街第8号楼8单元251门', '描述538457', '695458552241788', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等1件商品', '2020-04-26 18:55:01', '2020-04-26 19:11:37', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/197144.jpg', 10, 248.00, 6399.00, 20.00);
INSERT INTO `order_info` VALUES (3541, '范晓欢', '13087804524', 241.00, '1005', 2011, '第12大街第3号楼9单元684门', '描述894259', '332874462615223', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/684896.jpg', 29, 0.00, 222.00, 19.00);
INSERT INTO `order_info` VALUES (3542, '钟离瑾', '13454359683', 289.00, '1005', 6498, '第1大街第15号楼5单元655门', '描述626924', '741364726861893', '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g等2件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/435438.jpg', 2, 217.00, 487.00, 19.00);
INSERT INTO `order_info` VALUES (3543, '西门梁栋', '13041051678', 1448.00, '1005', 6643, '第13大街第23号楼9单元446门', '描述969168', '617181999166687', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等1件商品', '2020-04-26 18:55:01', '2020-04-26 23:10:20', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/559693.jpg', 9, 0.00, 1442.00, 6.00);
INSERT INTO `order_info` VALUES (3544, '姜利清', '13366204358', 308.00, '1005', 790, '第5大街第1号楼5单元915门', '描述113982', '227492564673868', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等2件商品', '2020-04-26 02:48:41', '2020-04-26 02:48:41', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/424938.jpg', 17, 0.00, 290.00, 18.00);
INSERT INTO `order_info` VALUES (3545, '独孤艳', '13580248698', 3105.00, '1005', 1441, '第19大街第9号楼9单元465门', '描述637957', '461487612145664', '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/518832.jpg', 22, 0.00, 3100.00, 5.00);
INSERT INTO `order_info` VALUES (3546, '费璐', '13373074372', 452.00, '1005', 1064, '第15大街第19号楼5单元444门', '描述343633', '191978495216212', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/162195.jpg', 19, 0.00, 435.00, 17.00);
INSERT INTO `order_info` VALUES (3547, '赵健世', '13475325409', 3119.00, '1005', 1958, '第19大街第22号楼7单元338门', '描述814248', '641531543772891', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等2件商品', '2020-04-26 00:12:05', '2020-04-26 00:12:05', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/182322.jpg', 7, 0.00, 3106.00, 13.00);
INSERT INTO `order_info` VALUES (3548, '闻人素', '13596638733', 263.00, '1005', 8197, '第5大街第19号楼3单元564门', '描述657581', '456331469287859', '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg等1件商品', '2020-04-26 00:12:05', '2020-04-26 00:12:05', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/729443.jpg', 28, 0.00, 244.00, 19.00);
INSERT INTO `order_info` VALUES (3549, '沈心', '13181324023', 4669.00, '1005', 7097, '第19大街第14号楼8单元247门', '描述324217', '143921477397758', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/473156.jpg', 7, 0.00, 4659.00, 10.00);
INSERT INTO `order_info` VALUES (3550, '郝滢', '13218063053', 26712.00, '1005', 4658, '第18大街第34号楼2单元883门', '描述987995', '343137126177866', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等3件商品', '2020-04-26 18:55:01', '2020-04-26 23:10:20', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/683378.jpg', 20, 0.00, 26700.00, 12.00);
INSERT INTO `order_info` VALUES (3551, '任纨', '13388215545', 9540.00, '1005', 1608, '第4大街第19号楼3单元486门', '描述798465', '667696622559845', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/578996.jpg', 19, 3863.00, 13398.00, 5.00);
INSERT INTO `order_info` VALUES (3552, '陈东', '13972033877', 17167.00, '1005', 3307, '第12大街第29号楼9单元122门', '描述495165', '135937593927699', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/367852.jpg', 30, 2040.00, 19197.00, 10.00);
INSERT INTO `order_info` VALUES (3553, '史新利', '13348645650', 1559.00, '1005', 8085, '第17大街第4号楼3单元663门', '描述493826', '115783712735415', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/977485.jpg', 1, 0.00, 1553.00, 6.00);
INSERT INTO `order_info` VALUES (3554, '苗素', '13438626144', 685.00, '1005', 9983, '第18大街第4号楼6单元978门', '描述355816', '458472761295376', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/683963.jpg', 11, 0.00, 666.00, 19.00);
INSERT INTO `order_info` VALUES (3555, '康希', '13520000710', 273.00, '1005', 4773, '第19大街第5号楼4单元181门', '描述655223', '944488778864991', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-26 18:55:01', '2020-04-26 23:10:20', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/418662.jpg', 5, 239.00, 504.00, 8.00);
INSERT INTO `order_info` VALUES (3556, '臧莲', '13935239689', 7472.00, '1005', 5913, '第2大街第39号楼8单元977门', '描述991231', '957241239449416', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等3件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/799886.jpg', 31, 233.00, 7691.00, 14.00);
INSERT INTO `order_info` VALUES (3557, '孙义兴', '13345100236', 8915.00, '1005', 5241, '第8大街第14号楼9单元828门', '描述652613', '158412757891686', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/487729.jpg', 33, 0.00, 8900.00, 15.00);
INSERT INTO `order_info` VALUES (3558, '许松', '13896824675', 5496.00, '1005', 3934, '第9大街第37号楼9单元298门', '描述626978', '288653172453888', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等5件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/553596.jpg', 24, 2375.00, 7860.00, 11.00);
INSERT INTO `order_info` VALUES (3559, '卞浩', '13795296821', 445.00, '1005', 6496, '第9大街第24号楼5单元788门', '描述696319', '968278119135946', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/444142.jpg', 32, 0.00, 435.00, 10.00);
INSERT INTO `order_info` VALUES (3560, '马永健', '13641731760', 17811.00, '1005', 9208, '第6大街第28号楼2单元326门', '描述477193', '133811241326668', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/252638.jpg', 14, 0.00, 17800.00, 11.00);
INSERT INTO `order_info` VALUES (3561, '百里世', '13231364033', 512.00, '1005', 2196, '第15大街第29号楼8单元419门', '描述431973', '822599533785715', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/126757.jpg', 32, 3.00, 496.00, 19.00);
INSERT INTO `order_info` VALUES (3562, '令狐佳', '13873470820', 3121.00, '1005', 1280, '第4大街第29号楼4单元696门', '描述224212', '818364722827152', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/695631.jpg', 27, 0.00, 3106.00, 15.00);
INSERT INTO `order_info` VALUES (3563, '葛彬', '13156412963', 668.00, '1005', 1427, '第6大街第6号楼1单元452门', '描述422299', '174994233897246', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/533375.jpg', 26, 332.00, 992.00, 8.00);
INSERT INTO `order_info` VALUES (3564, '齐俊峰', '13261045165', 9310.00, '1005', 1857, '第12大街第12号楼2单元861门', '描述944114', '491568423357984', '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/348113.jpg', 22, 0.00, 9300.00, 10.00);
INSERT INTO `order_info` VALUES (3565, '呼延武新', '13653457247', 7374.00, '1005', 6017, '第16大街第19号楼2单元631门', '描述885957', '633325667832883', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/496834.jpg', 20, 0.00, 7356.00, 18.00);
INSERT INTO `order_info` VALUES (3566, '穆露', '13145017389', 3116.00, '1005', 7044, '第20大街第2号楼8单元426门', '描述744778', '854899149482414', '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面等1件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/425893.jpg', 32, 0.00, 3100.00, 16.00);
INSERT INTO `order_info` VALUES (3567, '轩辕珍', '13848178540', 1558.00, '1005', 191, '第18大街第30号楼1单元191门', '描述498722', '347127577814774', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/715169.jpg', 15, 0.00, 1553.00, 5.00);
INSERT INTO `order_info` VALUES (3568, '计谦亨', '13651249915', 19973.00, '1005', 3949, '第9大街第5号楼6单元152门', '描述546916', '751484245496917', '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰等3件商品', '2020-04-26 18:55:01', '2020-04-26 23:10:20', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/171849.jpg', 26, 1931.00, 21897.00, 7.00);
INSERT INTO `order_info` VALUES (3569, '贺固之', '13936604280', 1221.00, '1005', 336, '第12大街第22号楼6单元726门', '描述168228', '149772828218998', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等3件商品', '2020-04-26 18:55:01', '2020-04-26 19:11:37', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/586214.jpg', 9, 284.00, 1488.00, 17.00);
INSERT INTO `order_info` VALUES (3570, '狄兴良', '13948905139', 451.00, '1005', 1861, '第2大街第30号楼1单元858门', '描述592852', '256537928218222', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等2件商品', '2020-04-26 18:55:01', '2020-04-26 23:54:42', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/514886.jpg', 32, 0.00, 444.00, 7.00);
INSERT INTO `order_info` VALUES (3571, '岑坚', '13220439586', 682.00, '1005', 5286, '第13大街第27号楼1单元771门', '描述976871', '133965588433243', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/895497.jpg', 17, 0.00, 666.00, 16.00);
INSERT INTO `order_info` VALUES (3572, '贺兰凤', '13022309909', 285.00, '1005', 2712, '第2大街第7号楼2单元886门', '描述514881', '434992686693879', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/765261.jpg', 27, 229.00, 504.00, 10.00);
INSERT INTO `order_info` VALUES (3573, '邬艺咏', '13752952612', 6673.00, '1005', 4889, '第9大街第34号楼3单元362门', '描述454616', '367612831844269', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/737683.jpg', 16, 0.00, 6660.00, 13.00);
INSERT INTO `order_info` VALUES (3574, '宋春', '13901511343', 14691.00, '1005', 1278, '第3大街第32号楼6单元444门', '描述491739', '836343294789391', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/139571.jpg', 11, 4514.00, 19197.00, 8.00);
INSERT INTO `order_info` VALUES (3575, '史晨辰', '13146190655', 4339.00, '1005', 82, '第18大街第19号楼5单元437门', '描述795752', '883338647469222', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/374193.jpg', 28, 0.00, 4326.00, 13.00);
INSERT INTO `order_info` VALUES (3576, '邬素', '13661734372', 4270.00, '1005', 6312, '第15大街第27号楼3单元343门', '描述349761', '643444124416666', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等1件商品', '2020-04-26 18:55:01', '2020-04-26 23:10:20', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/915862.jpg', 25, 2441.00, 6699.00, 12.00);
INSERT INTO `order_info` VALUES (3577, '姜奇', '13060645675', 468.00, '1005', 8839, '第20大街第35号楼3单元852门', '描述147111', '722864813316677', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/169651.jpg', 18, 45.00, 504.00, 9.00);
INSERT INTO `order_info` VALUES (3578, '张娣', '13586813843', 26718.00, '1005', 8708, '第12大街第14号楼3单元391门', '描述732297', '347956132393657', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/991685.jpg', 21, 0.00, 26700.00, 18.00);
INSERT INTO `order_info` VALUES (3579, '伏彩春', '13316165573', 7374.00, '1005', 1515, '第16大街第14号楼7单元619门', '描述349867', '152178625166735', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等3件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/648845.jpg', 15, 0.00, 7356.00, 18.00);
INSERT INTO `order_info` VALUES (3580, '司徒雪荣', '13042517574', 861.00, '1005', 8293, '第19大街第2号楼8单元532门', '描述643134', '755945958158776', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等3件商品', '2020-04-26 18:55:01', '2020-04-26 23:24:33', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/835541.jpg', 11, 642.00, 1488.00, 15.00);
INSERT INTO `order_info` VALUES (3581, '薛永', '13304503610', 231.00, '1005', 4113, '第1大街第16号楼2单元772门', '描述726125', '693851257527699', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/496555.jpg', 31, 0.00, 222.00, 9.00);
INSERT INTO `order_info` VALUES (3582, '孟翰', '13599347052', 4460.00, '1005', 505, '第10大街第26号楼3单元369门', '描述663172', '152227431344327', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等1件商品', '2020-04-26 18:55:01', '2020-04-26 23:24:33', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/768337.jpg', 14, 1949.00, 6399.00, 10.00);
INSERT INTO `order_info` VALUES (3583, '苗风航', '13565062589', 239.00, '1005', 9493, '第15大街第34号楼7单元555门', '描述338195', '372997562197626', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/346813.jpg', 7, 0.00, 222.00, 17.00);
INSERT INTO `order_info` VALUES (3584, '蒋姬', '13955874204', 448.00, '1005', 4844, '第20大街第39号楼6单元272门', '描述988758', '352115873177644', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/336322.jpg', 32, 0.00, 435.00, 13.00);
INSERT INTO `order_info` VALUES (3585, '和娟', '13542261249', 3328.00, '1005', 7967, '第10大街第1号楼9单元919门', '描述862882', '484568714345474', 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）等1件商品', '2020-04-26 18:55:01', '2020-04-26 23:24:33', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/178838.jpg', 5, 0.00, 3321.00, 7.00);
INSERT INTO `order_info` VALUES (3586, '葛坚', '13275891164', 7363.00, '1005', 4268, '第15大街第9号楼6单元887门', '描述912418', '891276929287656', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等3件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/774981.jpg', 3, 0.00, 7356.00, 7.00);
INSERT INTO `order_info` VALUES (3587, '华星', '13567674654', 8423.00, '1005', 2382, '第16大街第14号楼6单元365门', '描述462359', '451727251284847', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/638319.jpg', 1, 4994.00, 13398.00, 19.00);
INSERT INTO `order_info` VALUES (3588, '钟离丽', '13329548204', 4452.00, '1005', 2033, '第8大街第24号楼9单元217门', '描述584463', '861939957476122', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/658856.jpg', 29, 0.00, 4440.00, 12.00);
INSERT INTO `order_info` VALUES (3589, '张仪荷', '13824122825', 1451.00, '1005', 520, '第4大街第2号楼2单元324门', '描述169971', '297412761126125', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等1件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/947938.jpg', 3, 0.00, 1442.00, 9.00);
INSERT INTO `order_info` VALUES (3590, '黄启', '13437150533', 9981.00, '1005', 7350, '第19大街第11号楼8单元389门', '描述779912', '869225353414456', 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）等3件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/973934.jpg', 19, 0.00, 9963.00, 18.00);
INSERT INTO `order_info` VALUES (3591, '范聪澜', '13813399442', 228.00, '1005', 6631, '第9大街第20号楼6单元243门', '描述731121', '668318968677683', '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用等1件商品', '2020-04-26 18:55:01', '2020-04-26 19:11:37', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/995635.jpg', 29, 0.00, 222.00, 6.00);
INSERT INTO `order_info` VALUES (3592, '慕容克', '13418302532', 7366.00, '1005', 5121, '第7大街第36号楼3单元293门', '描述971655', '733615357161554', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/452985.jpg', 19, 0.00, 7356.00, 10.00);
INSERT INTO `order_info` VALUES (3593, '卞勇', '13215752729', 569.00, '1005', 1919, '第16大街第27号楼7单元814门', '描述336937', '938929245634839', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/852429.jpg', 7, 200.00, 756.00, 13.00);
INSERT INTO `order_info` VALUES (3594, '袁影', '13948245696', 2227.00, '1005', 4629, '第11大街第3号楼8单元772门', '描述798822', '746524473847865', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/632488.jpg', 11, 0.00, 2220.00, 7.00);
INSERT INTO `order_info` VALUES (3595, '曹佳', '13307109594', 17818.00, '1005', 2682, '第12大街第15号楼2单元166门', '描述343446', '733973988212593', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等2件商品', '2020-04-26 18:55:01', '2020-04-26 23:24:33', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/356817.jpg', 26, 0.00, 17800.00, 18.00);
INSERT INTO `order_info` VALUES (3596, '纪顺信', '13238531462', 319.00, '1005', 9243, '第1大街第28号楼4单元328门', '描述363494', '444814173213313', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/846851.jpg', 6, 194.00, 504.00, 9.00);
INSERT INTO `order_info` VALUES (3597, '司马羽希', '13669987300', 4922.00, '1005', 4986, '第6大街第18号楼2单元763门', '描述282536', '855298916296723', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/577574.jpg', 15, 0.00, 4904.00, 18.00);
INSERT INTO `order_info` VALUES (3598, '费乐', '13832845700', 620.00, '1005', 986, '第4大街第39号楼5单元859门', '描述125878', '648465899977438', '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g等3件商品', '2020-04-26 18:55:01', '2020-04-26 19:11:37', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/297268.jpg', 16, 94.00, 705.00, 9.00);
INSERT INTO `order_info` VALUES (3599, '茅中', '13241421776', 2902.00, '1005', 8371, '第2大街第8号楼3单元751门', '描述529944', '427911322847722', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等2件商品', '2020-04-26 05:19:29', '2020-04-26 05:19:29', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/833986.jpg', 4, 0.00, 2884.00, 18.00);
INSERT INTO `order_info` VALUES (3600, '夏侯飘', '13914566684', 9891.00, '1005', 476, '第13大街第5号楼1单元667门', '描述179627', '751211197947712', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等2件商品', '2020-04-26 00:12:05', '2020-04-26 00:12:05', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/913791.jpg', 22, 3521.00, 13398.00, 14.00);
INSERT INTO `order_info` VALUES (3601, '孙欣飘', '13807583455', 3117.00, '1005', 5015, '第18大街第34号楼7单元753门', '描述552889', '885498388681545', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/648816.jpg', 8, 0.00, 3106.00, 11.00);
INSERT INTO `order_info` VALUES (3602, '司马航', '13455681293', 9969.00, '1005', 7637, '第6大街第24号楼7单元261门', '描述939151', '585172176322357', 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/861446.jpg', 9, 0.00, 9963.00, 6.00);
INSERT INTO `order_info` VALUES (3603, '任荷丹', '13982433642', 2471.00, '1005', 8561, '第16大街第13号楼4单元457门', '描述961182', '269251611581143', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等1件商品', '2020-04-26 18:55:01', '2020-04-26 19:11:37', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/866915.jpg', 18, 0.00, 2452.00, 19.00);
INSERT INTO `order_info` VALUES (3604, '孟洁', '13536852010', 3345.00, '1005', 1089, '第17大街第18号楼4单元217门', '描述513523', '539135358481199', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/127396.jpg', 28, 3062.00, 6399.00, 8.00);
INSERT INTO `order_info` VALUES (3605, '何希', '13471216418', 254.00, '1005', 3838, '第15大街第21号楼7单元293门', '描述347361', '993569582184329', '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/521458.jpg', 32, 0.00, 244.00, 10.00);
INSERT INTO `order_info` VALUES (3606, '周辰', '13234246790', 6648.00, '1005', 8536, '第19大街第29号楼6单元979门', '描述557615', '849156354796311', 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/484748.jpg', 32, 0.00, 6642.00, 6.00);
INSERT INTO `order_info` VALUES (3607, '上官保', '13476256354', 26717.00, '1005', 4568, '第3大街第21号楼1单元383门', '描述242286', '249914365387472', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/216962.jpg', 7, 0.00, 26700.00, 17.00);
INSERT INTO `order_info` VALUES (3608, '何英', '13130364738', 155.00, '1005', 501, '第1大街第5号楼2单元126门', '描述784156', '428819621522368', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等1件商品', '2020-04-26 18:55:01', '2020-04-26 23:24:33', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/415457.jpg', 16, 0.00, 145.00, 10.00);
INSERT INTO `order_info` VALUES (3609, '萧泽晨', '13425814759', 375.00, '1005', 8768, '第19大街第39号楼3单元854门', '描述454177', '579185842185333', '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g等2件商品', '2020-04-26 18:55:01', '2020-04-26 19:11:37', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/428673.jpg', 24, 113.00, 470.00, 18.00);
INSERT INTO `order_info` VALUES (3610, '司徒薇', '13952130379', 437.00, '1005', 641, '第15大街第14号楼2单元284门', '描述179618', '522979266996242', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等2件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/996996.jpg', 20, 81.00, 504.00, 14.00);
INSERT INTO `order_info` VALUES (3611, '傅琦晶', '13605335038', 2463.00, '1005', 661, '第20大街第1号楼3单元667门', '描述615976', '829818855889761', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等1件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/521842.jpg', 28, 0.00, 2452.00, 11.00);
INSERT INTO `order_info` VALUES (3612, '尉迟祥才', '13527685454', 444.00, '1005', 9795, '第13大街第1号楼9单元385门', '描述464779', '546245479573647', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:02', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/111142.jpg', 2, 0.00, 435.00, 9.00);
INSERT INTO `order_info` VALUES (3613, '钱妹', '13663977722', 252.00, '1005', 5017, '第11大街第29号楼6单元666门', '描述672271', '339218365184916', '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg等1件商品', '2020-04-26 00:12:05', '2020-04-26 00:12:05', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/827259.jpg', 27, 0.00, 244.00, 8.00);
INSERT INTO `order_info` VALUES (3614, '欧阳贵', '13079136177', 1570.00, '1005', 3158, '第15大街第6号楼9单元398门', '描述812347', '555516283566188', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/316227.jpg', 16, 0.00, 1553.00, 17.00);
INSERT INTO `order_info` VALUES (3615, '罗媛艳', '13589282014', 2469.00, '1005', 9572, '第6大街第24号楼8单元852门', '描述846615', '473148582522669', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/177391.jpg', 11, 0.00, 2452.00, 17.00);
INSERT INTO `order_info` VALUES (3616, '彭锦', '13990496288', 7361.00, '1005', 2687, '第4大街第4号楼2单元653门', '描述957144', '959744641949928', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等3件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/634243.jpg', 6, 0.00, 7356.00, 5.00);
INSERT INTO `order_info` VALUES (3617, '王斌梁', '13901814216', 4334.00, '1005', 9077, '第12大街第14号楼8单元922门', '描述166755', '291121822157528', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等3件商品', '2020-04-26 00:12:05', '2020-04-26 00:12:05', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/326813.jpg', 26, 0.00, 4326.00, 8.00);
INSERT INTO `order_info` VALUES (3618, '魏壮会', '13843506992', 455.00, '1005', 5435, '第14大街第10号楼2单元687门', '描述448243', '646692271616256', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等3件商品', '2020-04-26 18:55:01', '2020-04-26 23:24:33', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/768978.jpg', 15, 0.00, 435.00, 20.00);
INSERT INTO `order_info` VALUES (3619, '东郭先', '13461958504', 7363.00, '1005', 5890, '第18大街第28号楼8单元942门', '描述174349', '395271567986826', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/464351.jpg', 8, 0.00, 7356.00, 7.00);
INSERT INTO `order_info` VALUES (3620, '齐东文', '13649643162', 397.00, '1005', 7783, '第17大街第19号楼8单元741门', '描述545374', '771682719926935', '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/754643.jpg', 30, 365.00, 756.00, 6.00);
INSERT INTO `order_info` VALUES (3621, '雷祥', '13472953606', 1567.00, '1005', 8582, '第15大街第28号楼5单元173门', '描述253247', '179124874791296', '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待等1件商品', '2020-04-26 18:55:01', '2020-04-26 23:10:20', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/266993.jpg', 14, 0.00, 1553.00, 14.00);
INSERT INTO `order_info` VALUES (3622, '邬雄琛', '13161681852', 5878.00, '1005', 8225, '第20大街第24号楼6单元689门', '描述816949', '537459758387371', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/592613.jpg', 6, 826.00, 6699.00, 5.00);
INSERT INTO `order_info` VALUES (3623, '任瑾', '13577810475', 26707.00, '1005', 5409, '第17大街第8号楼3单元792门', '描述449526', '984634275419151', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等3件商品', '2020-04-26 00:54:09', '2020-04-26 00:54:09', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/833914.jpg', 20, 0.00, 26700.00, 7.00);
INSERT INTO `order_info` VALUES (3624, '尉迟瑾', '13803547370', 7374.00, '1005', 3331, '第15大街第20号楼8单元548门', '描述498564', '761362628232635', '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信等3件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/852384.jpg', 23, 0.00, 7356.00, 18.00);
INSERT INTO `order_info` VALUES (3625, '岑风', '13470324336', 4332.00, '1005', 8631, '第11大街第17号楼6单元373门', '描述335353', '452671512256784', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等3件商品', '2020-04-26 18:55:01', '2020-04-26 23:39:40', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/249826.jpg', 20, 0.00, 4326.00, 6.00);
INSERT INTO `order_info` VALUES (3626, '卞华慧', '13919851111', 146.00, '1005', 3192, '第7大街第17号楼2单元234门', '描述976955', '893346491458852', '迪奥（Dior）烈艳蓝金唇膏/口红 珊瑚粉 ACTRICE 028号 3.5g等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:59:01', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/535836.jpg', 16, 107.00, 235.00, 18.00);
INSERT INTO `order_info` VALUES (3627, '南门峰', '13419342077', 3693.00, '1005', 5289, '第8大街第28号楼2单元571门', '描述342425', '137759423329977', '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）等1件商品', '2020-04-26 18:55:01', '2020-04-26 19:11:37', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/658295.jpg', 2, 2715.00, 6399.00, 9.00);
INSERT INTO `order_info` VALUES (3628, '窦荔枝', '13595290840', 297.00, '1005', 9812, '第2大街第33号楼1单元834门', '描述348333', '551156957849126', '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg等2件商品', '2020-04-26 18:55:01', '2020-04-26 19:03:49', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/743383.jpg', 18, 0.00, 290.00, 7.00);
INSERT INTO `order_info` VALUES (3629, '韦士', '13513120003', 1451.00, '1005', 1830, '第3大街第28号楼2单元313门', '描述285667', '371124229294946', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等1件商品', '2020-04-26 18:55:01', '2020-04-26 18:55:17', '2020-04-26 19:10:01', NULL, NULL, 'http://img.gmall.com/213688.jpg', 12, 0.00, 1442.00, 9.00);
INSERT INTO `order_info` VALUES (3630, '秦兰凤', '13576098401', 8920.00, '1005', 6987, '第15大街第10号楼2单元721门', '描述936149', '224139975437866', 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待等1件商品', '2020-04-26 02:16:34', '2020-04-26 02:16:34', '2020-04-26 19:10:16', NULL, NULL, 'http://img.gmall.com/524347.jpg', 6, 0.00, 8900.00, 20.00);
INSERT INTO `order_info` VALUES (3631, '柳雄琛', '13706782216', 1038.00, '1005', 6528, '第20大街第27号楼1单元112门', '描述455912', '529598889382888', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等3件商品', '2020-04-26 18:55:16', '2020-04-26 18:55:17', '2020-04-26 19:10:16', NULL, NULL, 'http://img.gmall.com/678876.jpg', 32, 464.00, 1488.00, 14.00);
INSERT INTO `order_info` VALUES (3632, '平云', '13533970339', 780.00, '1005', 6050, '第16大街第28号楼7单元894门', '描述563585', '514421885774477', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等2件商品', '2020-04-26 18:55:16', '2020-04-26 19:11:37', '2020-04-26 19:10:16', NULL, NULL, 'http://img.gmall.com/876558.jpg', 13, 221.00, 992.00, 9.00);
INSERT INTO `order_info` VALUES (3633, '葛东文', '13421342635', 456.00, '1005', 8081, '第9大街第14号楼4单元234门', '描述522361', '824477144524176', 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒等1件商品', '2020-04-26 18:55:16', '2020-04-26 18:59:01', '2020-04-26 19:10:16', NULL, NULL, 'http://img.gmall.com/444591.jpg', 13, 56.00, 496.00, 16.00);
INSERT INTO `order_info` VALUES (3634, '茅娅琦', '13160111050', 11151.00, '1005', 5288, '第7大街第34号楼4单元374门', '描述613496', '642127373719182', '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域等2件商品', '2020-04-26 00:12:05', '2020-04-26 00:12:05', '2020-04-26 19:10:16', NULL, NULL, 'http://img.gmall.com/659576.jpg', 6, 2260.00, 13398.00, 13.00);
INSERT INTO `order_info` VALUES (3635, '何翠', '13910065894', 1448.00, '1005', 1650, '第3大街第20号楼9单元568门', '描述356548', '393347631123893', '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机等1件商品', '2020-04-26 18:55:16', '2020-04-26 19:03:49', '2020-04-26 19:10:16', NULL, NULL, 'http://img.gmall.com/929137.jpg', 10, 0.00, 1442.00, 6.00);
INSERT INTO `order_info` VALUES (3636, '百里之', '13463372101', 9312.00, '1005', 3108, '第8大街第11号楼5单元666门', '描述462576', '792655914636636', '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面等3件商品', '2020-04-26 18:55:16', '2020-04-26 19:11:37', '2020-04-26 19:10:16', NULL, NULL, 'http://img.gmall.com/938678.jpg', 3, 0.00, 9300.00, 12.00);

-- ----------------------------
-- Table structure for sku_info
-- ----------------------------
DROP TABLE IF EXISTS `sku_info`;
CREATE TABLE `sku_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `spu_id` bigint(0) NULL DEFAULT NULL COMMENT 'spuId',
  `price` decimal(10, 0) NULL DEFAULT NULL COMMENT '价格',
  `sku_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `sku_desc` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `weight` decimal(10, 2) NULL DEFAULT NULL COMMENT '重量',
  `tm_id` bigint(0) NULL DEFAULT NULL COMMENT '品牌id',
  `category3_id` bigint(0) NULL DEFAULT NULL COMMENT '三级分类id',
  `sku_default_img` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '默认显示图片',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10001 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sku_info
-- ----------------------------
INSERT INTO `sku_info` VALUES (1, 1, 2220, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'new sku_desc', 0.24, 2, 61, 'http://AOvKmfRQEBRJJllwCwCuptVAOtBBcIjWeJRsmhbJ', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (2, 2, 3321, 'TCL 55A950C 55英寸32核人工智能 HDR曲面超薄4K电视金属机身（枪色）', 'new sku_desc', 15.24, 4, 86, 'http://JfJSvAnPkErYPcUsbgCuokhjxKiLeqpDXakZqFeE', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (3, 3, 3100, '小米（MI）电视 55英寸曲面4K智能WiFi网络液晶电视机4S L55M5-AQ 小米电视4S 55英寸 曲面', 'new sku_desc', 15.24, 1, 86, 'http://LzpblavcZQeYEbwbSjsnmsgAjtpudhDradqsRgdZ', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (4, 4, 1442, '小米Play 流光渐变AI双摄 4GB+64GB 梦幻蓝 全网通4G 双卡双待 小水滴全面屏拍照游戏智能手机', 'new sku_desc', 0.24, 1, 61, 'http://SXlkutIjYpDWWTEpNUiisnlsevOHVElrdngQLgyZ', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (5, 5, 244, '十月稻田 沁州黄小米 (黄小米 五谷杂粮 山西特产 真空装 大米伴侣 粥米搭档) 2.5kg', 'new sku_desc', 2.50, 5, 803, 'http://INNButKuTkslasORPBmHACnJuccfsxmRxNuMznPm', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (6, 6, 145, '北纯精制黄小米（小黄米 月子米 小米粥 粗粮杂粮 大米伴侣）2.18kg', 'new sku_desc', 2.20, 5, 803, 'http://lMddjKYNSaxpeAqbkWcxegrrHjayrosVDeLrABhg', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (7, 1, 1553, '荣耀10青春版 幻彩渐变 2400万AI自拍 全网通版4GB+64GB 渐变蓝 移动联通电信4G全面屏手机 双卡双待', 'new sku_desc', 0.24, 2, 61, 'http://IJwhvJSuwaMHaevQQKZUzLkOsvYFiYsRhmLSjpZb', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (8, 7, 8900, 'Apple iPhoneXSMax (A2104) 256GB 深空灰色 移动联通电信4G手机 双卡双待', 'new sku_desc', 0.24, 3, 61, 'http://RFCTOnWWRtrJdygmPqiUXdBvyhqiTTOnXYagFJDP', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (9, 8, 2452, '荣耀10 GT游戏加速 AIS手持夜景 6GB+64GB 幻影蓝全网通 移动联通电信', 'new sku_desc', 0.24, 2, 61, 'http://hNuKrFXZgFIzzZxnYeCxEDdJlbAwHUCbBChdXAKZ', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (10, 9, 222, '小米（MI） 小米路由器4 双千兆路由器 无线家用穿墙1200M高速双频wifi 千兆版 千兆端口光纤适用', 'new sku_desc', 1.24, 1, 329, 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (11, 10, 6399, '联想(Lenovo)拯救者Y7000 2019新款 15.6英寸游戏本笔记本电脑（i7-9750H 8GB 1TB+256GB GTX1650 4G独显 黑）', 'new sku_desc', 4.25, 6, 285, 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (12, 10, 6699, '联想(Lenovo)拯救者Y7000 英特尔酷睿i7 2019新款 15.6英寸发烧游戏本笔记本电脑（i7-9750H 8GB 512GB SSD GTX1650 4G 高色域', 'new sku_desc', 4.25, 6, 285, 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (13, 10, 7299, '联想(Lenovo)Y9000X 2019新款 15.6英寸高性能标压轻薄本笔记本电脑(i5-9300H 16G 512GSSD FHD)深空灰', 'new sku_desc', 4.25, 6, 285, 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (14, 11, 496, 'Dior迪奥口红唇膏送女友老婆礼物生日礼物 烈艳蓝金999+888两支装礼盒', 'new sku_desc', 0.01, 7, 477, 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', '2021-01-01 12:21:13');
INSERT INTO `sku_info` VALUES (15, 12, 252, '迪奥（Dior）烈艳蓝金唇膏 口红 3.5g 999号 哑光-经典正红', 'new sku_desc', 0.00, 7, 477, 'http://kAXllAQEzJWHwiExxVmyJIABfXyzbKwedeofMqwh', '2021-01-01 12:21:13');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `passwd` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `phone_num` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `head_img` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `user_level` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户级别',
  `birthday` date NULL DEFAULT NULL COMMENT '用户生日',
  `gender` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别 M男,F女',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `operate_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 250001 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (82, 'rz4gxf1', '阿仁', NULL, '康仁', '13437298274', 'rz4gxf1@0355.net', NULL, '1', '2004-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (85, 'cte9ov7mv', '波宁', NULL, '华良海', '13751595688', 'p6vilkg81l9w@qq.com', NULL, '1', '1989-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 23:54:52');
INSERT INTO `user_info` VALUES (96, 'v48hrb', '琴蕊', NULL, '成凝晓', '13665774249', 'v48hrb@aol.com', NULL, '1', '2000-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:54:19');
INSERT INTO `user_info` VALUES (191, '670ja2u', '晓晓', NULL, '苗晓', '13332653961', '670ja2u@aol.com', NULL, '1', '2003-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (336, '5o7uv474', '聪聪', NULL, '单于艳瑞', '13593524629', '5o7uv474@0355.net', NULL, '1', '2004-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:31:50');
INSERT INTO `user_info` VALUES (417, 'm4iflp', '磊民', NULL, '葛磊民', '13687717321', 'm4iflp@ask.com', NULL, '2', '2003-04-26', 'M', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (453, 'mqcnw52b2', '露露', NULL, '黄露', '13557422534', '8y8bdlnb9zr@yeah.net', NULL, '1', '1982-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 01:19:04');
INSERT INTO `user_info` VALUES (472, 'en75wrro1', '梦梦', NULL, '欧阳英', '13522755857', 'en75wrro1@hotmail.com', NULL, '3', '1999-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (476, 'mtjbajlpg', '琬琬', NULL, '臧凤洁', '13981274672', 'mtjbajlpg@3721.net', NULL, '1', '1996-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:31:50');
INSERT INTO `user_info` VALUES (501, 'o1ry4s2786a', '桂桂', NULL, '苗露', '13191575399', 'o1ry4s2786a@live.com', NULL, '1', '1988-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (505, 'vu7f8gofxoz5', '聪聪', NULL, '卜娴', '13436655363', 'vu7f8gofxoz5@yahoo.com', NULL, '3', '1977-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (506, 'rc7v5gp0e', '淑惠', NULL, '诸葛欣', '13828168513', 'uathzx0@msn.com', NULL, '2', '1966-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (520, 'n55hvx1mt', '毓悦', NULL, '郝媛', '13977771642', 'fdi8ryrb3qp@163.com', NULL, '1', '1966-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (539, '40o6zarirw70', '莉桂', NULL, '安莉桂', '13181351179', '40o6zarirw70@gmail.com', NULL, '1', '2003-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (583, 'yus8xwf5zx4', '阿东', NULL, '郑福生', '13273111262', '76niwbc@263.net', NULL, '1', '1985-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (614, 'm2g9pcy5', '美娜', NULL, '潘淑', '13785371649', 'm2g9pcy5@sina.com', NULL, '2', '1972-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:31:50');
INSERT INTO `user_info` VALUES (641, 'zym706mg', '信子', NULL, '安良', '13784989252', 'qhgvpjslhdaw@sohu.com', NULL, '1', '2001-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (661, '01mhwx9z7boc', '兰兰', NULL, '薛兰', '13767846627', '01mhwx9z7boc@qq.com', NULL, '1', '1999-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (790, 'uzuwzgrob63c', '昭冰', NULL, '贺秀', '13564285779', 'sdz8habh8@yahoo.com', NULL, '1', '1988-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (869, 'eozovsf1', '飞彬', NULL, '俞胜学', '13555677759', 'eozovsf1@qq.com', NULL, '2', '1990-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (986, 'y7g603xlvhu', '行时', NULL, '康行时', '13759491949', 'y7g603xlvhu@live.com', NULL, '1', '1987-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 01:19:04');
INSERT INTO `user_info` VALUES (993, 'fv65iylclz', '馨艺', NULL, '狄蓉眉', '13683335827', 'fv65iylclz@live.com', NULL, '3', '2000-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 01:46:53');
INSERT INTO `user_info` VALUES (1024, '5cblkoo', '芝玉', NULL, '苗晓', '13328773998', '5cblkoo@yeah.net', NULL, '1', '1967-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 23:39:49');
INSERT INTO `user_info` VALUES (1029, 'hru6k4qo', '丽丽', NULL, '姜惠珠', '13468589325', 'y3zlymzibmrw@msn.com', NULL, '1', '1971-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (1064, '711xb5', '辉力', NULL, '孙辉力', '13499411513', '711xb5@yeah.net', NULL, '1', '1984-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 01:19:04');
INSERT INTO `user_info` VALUES (1088, 'awcv97w', '月莺', NULL, '毛予馨', '13351748124', 'awcv97w@sina.com', NULL, '1', '1989-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (1089, 'jgfzjceplr', '秀秀', NULL, '汪贞', '13264566762', 'jgfzjceplr@gmail.com', NULL, '1', '1987-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (1143, 'co7oq6v', '秋秋', NULL, '萧晶', '13377938432', 's3w1wsuu11@googlemail.com', NULL, '1', '1982-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 01:19:04');
INSERT INTO `user_info` VALUES (1278, 'p4uxzfhesw', '艺艺', NULL, '常羽希', '13393584467', 'p4uxzfhesw@live.com', NULL, '1', '1998-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 23:39:49');
INSERT INTO `user_info` VALUES (1280, 'pcmb6q26n3e', '媛艳', NULL, '方媛艳', '13761563372', 'c28t085lm@yahoo.com.cn', NULL, '1', '1965-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 23:02:22');
INSERT INTO `user_info` VALUES (1315, 'iuef088', '慧慧', NULL, '安慧', '13119871311', 'iuef088@live.com', NULL, '3', '1965-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:54:19');
INSERT INTO `user_info` VALUES (1335, 'bj4rgne', '姣婉', NULL, '杨露瑶', '13531355918', 'bj4rgne@126.com', NULL, '3', '1970-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (1427, 'n5k71z9gjsc', '艳艳', NULL, '淳于艳', '13377167155', 'gmrt0aijzg9@googlemail.com', NULL, '1', '1979-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 23:39:49');
INSERT INTO `user_info` VALUES (1441, 'j86254fn', '思思', NULL, '令狐琦晶', '13195658627', 'j86254fn@gmail.com', NULL, '2', '1998-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (1515, 'n5aqjv3zgadw', '晓欢', NULL, '曹娟英', '13687871277', 'onc7tbxt57@live.com', NULL, '1', '1981-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (1592, '7iuyuqkd', '璧璐', NULL, '呼延璧璐', '13291231422', '7iuyuqkd@yahoo.com.cn', NULL, '1', '1970-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (1604, 'f933012q4', '阿钧', NULL, '凤钧', '13347942963', 'f933012q4@yeah.net', NULL, '2', '1984-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 23:14:36');
INSERT INTO `user_info` VALUES (1605, 'gh5pph48o05', '莎锦', NULL, '姚莎锦', '13525278811', 'gh5pph48o05@263.net', NULL, '1', '1977-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (1608, 'e9kygvxnma', '玲芬', NULL, '司空玲芬', '13236323235', 'e9kygvxnma@3721.net', NULL, '2', '1970-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (1650, 'paur0czo', '亚宜', NULL, '卫莉桂', '13112742192', 'paur0czo@yahoo.com', NULL, '1', '1980-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (1694, 'rlgv5fdn', '凤洁', NULL, '司空凤洁', '13361261552', 'rlgv5fdn@googlemail.com', NULL, '1', '2003-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (1755, 'ra5lgurpl', '艺艺', NULL, '葛艺', '13673984433', 'ra5lgurpl@googlemail.com', NULL, '1', '1983-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 23:27:15');
INSERT INTO `user_info` VALUES (1801, 'tja53nc0nq', '姬舒', NULL, '华姬舒', '13613368355', 'we8q1rg16@aol.com', NULL, '3', '1990-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (1826, 'bf1wtwz3lw', '爱爱', NULL, '独孤爱', '13669758282', 'bf1wtwz3lw@gmail.com', NULL, '1', '1994-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (1830, '9aqinkr2w2h', '媛媛', NULL, '王静', '13594799374', '4t1tenwp4umd@3721.net', NULL, '1', '1976-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (1857, '17mnm9', '环环', NULL, '何惠', '13541975445', '17mnm9@yahoo.com', NULL, '1', '1991-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (1861, '128ugcyazaet', '阿仁', NULL, '滕仁', '13255372756', 'opkd0ax8kq@163.net', NULL, '1', '1967-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 02:48:51');
INSERT INTO `user_info` VALUES (1919, 'k05ztfmpnq', '瑞凡', NULL, '慕容琼', '13999863966', '9xkcp4x979@ask.com', NULL, '3', '2000-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 01:19:04');
INSERT INTO `user_info` VALUES (1958, 'n0i957th86ti', '宁欣', NULL, '任寒伊', '13919932986', 'thhb6x7pjz@0355.net', NULL, '1', '1966-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (2011, 'm0hnnmlovwd', '枫芸', NULL, '唐枫芸', '13881714381', 'z58gm6g16n@live.com', NULL, '1', '1970-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (2015, 'lzv4iimdpa8', '阿斌', NULL, '李斌', '13879682261', 'lzv4iimdpa8@sina.com', NULL, '1', '1968-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 23:02:22');
INSERT INTO `user_info` VALUES (2033, '46ipaxn8', '文辉', NULL, '舒豪', '13282652171', '46ipaxn8@googlemail.com', NULL, '1', '1969-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (2093, 'mpa7b2wnqr', '舒影', NULL, '欧阳舒影', '13584946157', 'mpa7b2wnqr@0355.net', NULL, '1', '1993-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 23:54:52');
INSERT INTO `user_info` VALUES (2178, 'ooxe6h4nm', '瑾颖', NULL, '袁馥', '13524131878', 'ooxe6h4nm@sina.com', NULL, '2', '1999-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (2196, '23fkeik6cbx', '静淑', NULL, '滕静淑', '13297762367', '4aprrnt@ask.com', NULL, '1', '1992-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (2316, 'kmmz7m0f', '欣欣', NULL, '百里怡', '13432421199', 'hgmwdjk8lta@yeah.net', NULL, '1', '1985-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (2359, 'i0rwbp', '雅芝', NULL, '岑莉', '13935568747', 'i0rwbp@yahoo.com.cn', NULL, '2', '1991-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (2382, 'rgfhvxgqpa1e', '凡凡', NULL, '夏侯娅琦', '13834128931', 'rgfhvxgqpa1e@sina.com', NULL, '1', '1974-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (2383, 'asb0afe1t5', '锦锦', NULL, '熊妹霞', '13152489546', '0g1ujrp1l7@qq.com', NULL, '1', '1971-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (2481, 'm5ykset2cl', '莺莺', NULL, '吴莺', '13168534335', 'm5ykset2cl@aol.com', NULL, '1', '2004-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (2610, 'yv9g2bmfyvg', '荷丹', NULL, '赵荷丹', '13185728389', 'yv9g2bmfyvg@163.net', NULL, '1', '1972-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 02:16:44');
INSERT INTO `user_info` VALUES (2682, 'ty3kklx', '娅琦', NULL, '费娅琦', '13593192971', 'ty3kklx@yahoo.com', NULL, '1', '2004-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 19:01:24');
INSERT INTO `user_info` VALUES (2687, 'c9znye', '婵婵', NULL, '和素', '13693244628', 'c9znye@sina.com', NULL, '1', '1967-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (2712, 'kzwqk3j', '阿乐', NULL, '金时', '13211639483', 'bsiumst0qpv@126.com', NULL, '2', '1998-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 05:19:40');
INSERT INTO `user_info` VALUES (2760, 'o63m838vvk', '阿康', NULL, '西门康', '13378871947', 'sjrbi06t@yahoo.com', NULL, '1', '1989-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (2774, '2qtf0wkpf', '月月', NULL, '计月', '13346743359', '2qtf0wkpf@ask.com', NULL, '3', '1973-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (2790, '7hq5wgbcok5', '琦琦', NULL, '齐桂娣', '13468721332', '7hq5wgbcok5@msn.com', NULL, '1', '1966-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (2814, 'vu2lx286', '竹竹', NULL, '朱竹', '13593524675', 'b0ugrz5a@aol.com', NULL, '3', '2000-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (2856, 'yzlk0r1fx11', '庆磊', NULL, '伍亨奇', '13542189284', 'yzlk0r1fx11@hotmail.com', NULL, '1', '1978-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 02:48:51');
INSERT INTO `user_info` VALUES (3003, 'z5rsham8p1fh', '柔竹', NULL, '薛华', '13293531665', 'z5rsham8p1fh@3721.net', NULL, '3', '1997-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 01:46:53');
INSERT INTO `user_info` VALUES (3020, '7qucygegj', '妍茜', NULL, '熊妍茜', '13718254891', '7qucygegj@aol.com', NULL, '2', '1975-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (3102, '22qje9ur6c', '风航', NULL, '计先', '13583671832', '22qje9ur6c@live.com', NULL, '1', '1972-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (3108, '92geppgxiv8', '毓悦', NULL, '岑毓悦', '13969936239', 'nosicpzc37@sohu.com', NULL, '1', '1967-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (3145, 'g9s8rmj', '仪仪', NULL, '舒宁', '13796989275', 'db056b9ejl@263.net', NULL, '1', '1994-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (3158, 'edb0bita', '翠雅', NULL, '钱予', '13127429711', 'oj3c0hr@3721.net', NULL, '1', '1965-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (3192, 'oou48rh52qe', '华慧', NULL, '滕华慧', '13225879823', 'kc0zug@yahoo.com.cn', NULL, '3', '1998-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:54:19');
INSERT INTO `user_info` VALUES (3307, '4r8pr6k', '影影', NULL, '许影', '13316358695', '4r8pr6k@live.com', NULL, '1', '1982-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:31:50');
INSERT INTO `user_info` VALUES (3331, '7lqlsi5ukbvr', '妹霞', NULL, '朱舒影', '13579768326', 'tvi0se9@gmail.com', NULL, '2', '1978-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (3552, 'ybu761', '怡怡', NULL, '司马萍玲', '13325978925', 'tyo7c23y48@yahoo.com.cn', NULL, '1', '1967-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (3635, 'sw166ipy', '淑淑', NULL, '成纯', '13544144351', 'sw166ipy@yahoo.com.cn', NULL, '1', '1988-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (3770, 'a54ojghf', '菊兰', NULL, '茅芬芳', '13678428685', 'a54ojghf@163.net', NULL, '1', '1968-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (3810, 'n8sp3n', '丹蓉', NULL, '齐丹蓉', '13449713125', 'atqf1h8q0rh@googlemail.com', NULL, '2', '1978-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (3838, 'bszcuvv', '可可', NULL, '谢仪荷', '13523575895', 'bszcuvv@hotmail.com', NULL, '1', '2003-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (3934, 'o6xd4eifqx', '瑶瑶', NULL, '和瑶', '13973175962', 'o6xd4eifqx@0355.net', NULL, '1', '1977-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (3949, '6hv6ti1o', '影荔', NULL, '伍筠', '13791854111', '6hv6ti1o@live.com', NULL, '1', '2005-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (4113, '0i1lqzzn77j', '纯毓', NULL, '公孙寒', '13299335839', '0i1lqzzn77j@hotmail.com', NULL, '1', '1976-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (4131, '3lp70amrwz7', '艺咏', NULL, '钱彩春', '13829467142', 'wwwyl6o@163.net', NULL, '1', '1989-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (4158, 'u65kvdlhg', '璧璐', NULL, '常美', '13554496526', 'u65kvdlhg@qq.com', NULL, '1', '2005-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:54:19');
INSERT INTO `user_info` VALUES (4268, 'tdxk237lb', '玉萍', NULL, '吕静', '13126972652', 'lbvb2v7z7@163.net', NULL, '3', '1994-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (4282, 'lj3ewak5z7', '希希', NULL, '戚佳', '13674186383', 'lj3ewak5z7@263.net', NULL, '2', '1978-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:31:50');
INSERT INTO `user_info` VALUES (4504, 'gywentboxm8', '洁洁', NULL, '戴淑惠', '13192964915', 'gywentboxm8@live.com', NULL, '1', '1968-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (4537, '35a7r1oy', '莎锦', NULL, '轩辕枝', '13964927398', '35a7r1oy@live.com', NULL, '1', '1966-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (4568, 'caph84pl6cmo', '竹霭', NULL, '钟离婷姣', '13115495645', 'caph84pl6cmo@0355.net', NULL, '2', '1985-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (4579, 'n54zvb14', '林有', NULL, '曹克', '13855733659', 'n54zvb14@yahoo.com.cn', NULL, '2', '1977-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 23:27:15');
INSERT INTO `user_info` VALUES (4616, 'l69pj3hfdqb2', '莉桂', NULL, '何聪', '13613877377', 'u1l3xva3kx1m@sohu.com', NULL, '1', '1976-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (4629, 'qkm8au4', '艺咏', NULL, '贺艺咏', '13584524697', 'qkm8au4@sohu.com', NULL, '1', '1994-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (4658, 'f5vemgo', '艺欣', NULL, '薛真环', '13184761885', 'wkvud7j@qq.com', NULL, '1', '2002-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (4669, 'tys6n8x6', '佳嘉', NULL, '曹岚艺', '13485842379', 'iplg40isnsm@ask.com', NULL, '1', '1978-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (4773, '6gw80o', '欣飘', NULL, '伍素云', '13974446966', '6gw80o@aol.com', NULL, '1', '1977-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (4844, 'xf7hovq0x', '阿朋', NULL, '李朋斌', '13172396598', 'xf7hovq0x@qq.com', NULL, '1', '1978-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (4860, 'dzgzu1b712', '惠珠', NULL, '罗柔竹', '13286988451', '48kkfozwx4@sohu.com', NULL, '1', '2001-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (4889, '8vgbq8piyn4s', '行时', NULL, '宇文哲江', '13634871445', '8vgbq8piyn4s@sohu.com', NULL, '1', '1987-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (4986, 'qehnnjzp4n', '蕊薇', NULL, '祁婵雁', '13995773614', 'qehnnjzp4n@gmail.com', NULL, '1', '1973-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (5015, '9ab0pwwnplml', '豪心', NULL, '卫平保', '13741976355', '9ab0pwwnplml@126.com', NULL, '2', '1984-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (5017, '2jlkgyu', '欣欣', NULL, '冯燕彩', '13416448732', 'v7safl@3721.net', NULL, '1', '1998-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (5121, 'ci25sp3zj', '可姬', NULL, '孙亚', '13353776376', 'zw6b08f6@3721.net', NULL, '3', '1978-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (5137, 'c24rxr', '阿勇', NULL, '滕胜学', '13859747457', 'c24rxr@163.net', NULL, '1', '1997-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 23:02:22');
INSERT INTO `user_info` VALUES (5241, 'gecvnzcxaf', '岚艺', NULL, '令狐枫芸', '13495143662', 'gecvnzcxaf@sohu.com', NULL, '2', '1978-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (5286, 'rxgbgwl2k', '莲真', NULL, '冯倩', '13784872741', 'rxgbgwl2k@263.net', NULL, '2', '1982-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (5288, 'j1xn8jn', '眉眉', NULL, '卜眉', '13974784684', 'j1xn8jn@3721.net', NULL, '1', '1989-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (5289, 'rl4hpi1ulw', '华华', NULL, '戴珊', '13561649591', 'rl4hpi1ulw@yahoo.com.cn', NULL, '1', '2004-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (5400, '0j6kf08jph9', '国胜', NULL, '于国胜', '13868418278', '0j6kf08jph9@msn.com', NULL, '1', '1968-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (5409, 'ooo8e6c8', '毓悦', NULL, '殷枫', '13268869937', 'dpvk4fco7qq@qq.com', NULL, '1', '1974-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (5435, 'l3bxmik1cwr', '维启', NULL, '周维启', '13426661544', 'l3bxmik1cwr@hotmail.com', NULL, '1', '1977-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (5464, 'tv6urdr1k', '朋斌', NULL, '舒俊', '13383537915', 'tv6urdr1k@sina.com', NULL, '1', '1984-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (5525, '8v3l0dz', '飘育', NULL, '伍枫芸', '13266495844', 'zsjcca8sf@aol.com', NULL, '1', '1970-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 02:16:44');
INSERT INTO `user_info` VALUES (5724, 'kjj19y2pk', '寒伊', NULL, '雷萍', '13372686698', 'kjj19y2pk@263.net', NULL, '1', '1966-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (5729, 'dc0d5h2', '颖露', NULL, '卫馨艺', '13145133377', 'dc0d5h2@live.com', NULL, '1', '1980-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (5890, 'eueff2jebv7', '梅琳', NULL, '鲁艳', '13637497789', 'kmmd0lprb@qq.com', NULL, '1', '1993-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (5903, 'zhwfsd', '玉玉', NULL, '夏侯竹', '13786273522', 'zhwfsd@googlemail.com', NULL, '1', '1974-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (5913, 'k9fq8zymm7p5', '玉萍', NULL, '顾梦岚', '13679985795', 'zd25kvmogenu@live.com', NULL, '2', '1965-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (6017, 'fay5a7f93o3b', '阿群', NULL, '岑德行', '13339266894', 'fay5a7f93o3b@126.com', NULL, '1', '1965-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (6050, 'br74ou8360ee', '欣飘', NULL, '戚岚', '13237364915', 'br74ou8360ee@sohu.com', NULL, '2', '1987-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (6115, 'rtsbyh2bz856', '敬震', NULL, '毛士', '13744851966', 'rtsbyh2bz856@126.com', NULL, '1', '1995-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 05:19:40');
INSERT INTO `user_info` VALUES (6213, '4ev1lc', '兰兰', NULL, '闻人兰', '13118215255', 'qxztyde602@263.net', NULL, '2', '1986-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (6221, 'y338ykcx', '影荔', NULL, '濮阳云', '13298187826', 'y338ykcx@googlemail.com', NULL, '1', '1980-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 05:19:40');
INSERT INTO `user_info` VALUES (6290, 'ekgmo9y2', '绍功', NULL, '轩辕鸣朋', '13434455393', 'et1sdlq@aol.com', NULL, '2', '1975-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 01:46:53');
INSERT INTO `user_info` VALUES (6312, 'nzpcnnk', '姣姣', NULL, '袁馨艺', '13912191469', 'q602s68g1t@163.com', NULL, '1', '2004-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (6382, '4fylodum7pf', '阿轮', NULL, '钱轮', '13861666588', '4fylodum7pf@126.com', NULL, '1', '2005-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 01:46:53');
INSERT INTO `user_info` VALUES (6385, '64okoe', '聪聪', NULL, '蒋璐娅', '13945262689', '8judr4ouy2c@yeah.net', NULL, '1', '1993-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 05:19:40');
INSERT INTO `user_info` VALUES (6496, 'jzotjebg2', '梦岚', NULL, '尉迟莲真', '13436251599', 'jzotjebg2@googlemail.com', NULL, '1', '1975-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 05:19:40');
INSERT INTO `user_info` VALUES (6498, 'ijnuq01vslsr', '珊莎', NULL, '轩辕韶涵', '13915824279', 'ijnuq01vslsr@263.net', NULL, '1', '1978-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 23:27:15');
INSERT INTO `user_info` VALUES (6528, '2ns1mxarn', '阿豪', NULL, '南宫伟刚', '13876885528', 'ifj8wo@yahoo.com', NULL, '2', '1980-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (6581, 'p13m2m6uw', '阿清', NULL, '窦裕河', '13839253469', 'p13m2m6uw@hotmail.com', NULL, '1', '1971-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (6589, 'lhm8gf', '瑞凡', NULL, '曹璐娅', '13676424484', 'lhm8gf@ask.com', NULL, '1', '2004-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 23:02:22');
INSERT INTO `user_info` VALUES (6631, 'd4enzixa', '卿卿', NULL, '邬艺欣', '13941879756', 'd4enzixa@sina.com', NULL, '1', '1966-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 02:16:44');
INSERT INTO `user_info` VALUES (6643, 'wfse2mi8v9', '萍玲', NULL, '杨萍玲', '13296662174', 'wfse2mi8v9@3721.net', NULL, '1', '1978-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 19:01:24');
INSERT INTO `user_info` VALUES (6739, 'nyycmed4', '思思', NULL, '范舒影', '13651113958', '5swq08s4l7@0355.net', NULL, '2', '1986-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (6748, 'ao7hd5qkyjpf', '阿中', NULL, '戚中', '13361399315', 'tb5k1zfg@0355.net', NULL, '1', '1982-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 06:47:39');
INSERT INTO `user_info` VALUES (6814, '89xtog', '阿清', NULL, '成清', '13935394894', '89xtog@163.net', NULL, '1', '1965-04-26', 'M', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (6956, 'ezcuf27hh', '春春', NULL, '卞春', '13688862377', 'ezcuf27hh@msn.com', NULL, '1', '1992-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:31:50');
INSERT INTO `user_info` VALUES (6987, '6q1ph9kb9o', '雪雪', NULL, '长孙秀娟', '13681845288', '6q1ph9kb9o@googlemail.com', NULL, '1', '1977-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (6995, 'cjb0mlrrmuw', '平保', NULL, '蒋平保', '13767673257', 'cjb0mlrrmuw@hotmail.com', NULL, '1', '1992-04-26', 'M', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (7031, '1tz0oo2iw7', '怡婵', NULL, '庞怡婵', '13583753831', '1tz0oo2iw7@163.com', NULL, '1', '1971-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 00:12:14');
INSERT INTO `user_info` VALUES (7044, 'raliry8s', '妍妍', NULL, '吴茗羽', '13136626484', 'zew6fxmj3ly@163.com', NULL, '3', '1971-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (7097, 'rmnxxp', '锦锦', NULL, '百里纯', '13596414417', 'rmnxxp@sina.com', NULL, '1', '1967-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 01:19:04');
INSERT INTO `user_info` VALUES (7287, 'vggtcuh822', '莺莺', NULL, '吕莺', '13415546915', 'vggtcuh822@yahoo.com.cn', NULL, '2', '1998-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (7350, 'z5ljgz', '巧巧', NULL, '淳于可姬', '13667382568', 'z5ljgz@3721.net', NULL, '2', '1982-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (7353, '97jx56wv3ri', '彩彩', NULL, '南门枫芸', '13697151119', 'mif1guwqz@hotmail.com', NULL, '1', '1972-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (7388, '05lev1', '阿亮', NULL, '沈毅俊', '13263824471', '05lev1@hotmail.com', NULL, '1', '1989-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 00:12:14');
INSERT INTO `user_info` VALUES (7443, 'i2smxypttxm', '素素', NULL, '濮阳素', '13689937876', '2g3p8rikrpn@yahoo.com.cn', NULL, '1', '1989-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (7476, 'mxbmgafk5', '昭昭', NULL, '赵欣飘', '13514834792', 'mxbmgafk5@gmail.com', NULL, '2', '1995-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (7637, 'brwcx887y3ao', '瑞凡', NULL, '余蓓纨', '13999867872', 'brwcx887y3ao@0355.net', NULL, '2', '2004-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (7664, 'xtk9mic00xd', '璐娅', NULL, '夏侯环雪', '13482954332', 'xtk9mic00xd@gmail.com', NULL, '2', '1987-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 23:02:22');
INSERT INTO `user_info` VALUES (7783, 'i1utp5le5p', '阿发', NULL, '周国', '13172965847', 'i1utp5le5p@0355.net', NULL, '1', '1972-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 02:48:51');
INSERT INTO `user_info` VALUES (7967, 'a7u3hvu', '永健', NULL, '窦鸣朋', '13885195579', 'qescruvvl@263.net', NULL, '1', '1983-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 01:19:04');
INSERT INTO `user_info` VALUES (8081, '8cdarxvon', '光天', NULL, '曹伟', '13423352427', '8cdarxvon@hotmail.com', NULL, '1', '1997-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (8085, 'xihgl3fhx', '纨纨', NULL, '呼延纨', '13491515698', 'xihgl3fhx@263.net', NULL, '1', '1997-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (8190, 'u6c9agokb', '锦锦', NULL, '鲁静淑', '13923711651', 's7k7toi@qq.com', NULL, '1', '1965-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (8197, '5liqy4g9pg', '瑾瑾', NULL, '卫瑾', '13673525119', 'zf4m2oy37q@yahoo.com', NULL, '1', '1988-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (8225, 'k6d9k2pqbjz', '功松', NULL, '东郭弘', '13276842986', 'k6d9k2pqbjz@163.com', NULL, '3', '2001-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 05:19:40');
INSERT INTO `user_info` VALUES (8263, 'ene9ff', '凤凤', NULL, '舒瑾颖', '13958325947', 'ene9ff@live.com', NULL, '2', '1991-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (8293, 'gykvwjgz', '婷姣', NULL, '呼延婷姣', '13293178578', 'xpa7w8hrke@yeah.net', NULL, '3', '1984-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (8371, '4dvwfp2ofi', '厚庆', NULL, '秦厚庆', '13123475381', '4dvwfp2ofi@yahoo.com', NULL, '1', '1965-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 23:27:15');
INSERT INTO `user_info` VALUES (8418, '9codflbzx10b', '娅娅', NULL, '尤洁梅', '13331764556', 'm0asxixw@163.net', NULL, '1', '1972-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');
INSERT INTO `user_info` VALUES (8437, 'jsduu0jtg', '梅琳', NULL, '南门芬芳', '13149642275', 'jsduu0jtg@163.net', NULL, '2', '1984-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 05:19:40');
INSERT INTO `user_info` VALUES (8536, 'b3d0t4dywl', '蕊蕊', NULL, '纪素', '13813395756', '6gegirkmnbxe@msn.com', NULL, '2', '1987-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (8561, 'buf6fh9', '澜澜', NULL, '独孤卿聪', '13268212716', 'buf6fh9@hotmail.com', NULL, '1', '1966-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (8582, 'bifn13h3tp', '善厚', NULL, '单于善厚', '13882394841', 'bifn13h3tp@0355.net', NULL, '1', '2002-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (8584, '4b8uqv9', '春春', NULL, '臧聪澜', '13172173433', '4b8uqv9@126.com', NULL, '1', '1979-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (8611, 'nrd6yvgh3ohr', '可姬', NULL, '黄可姬', '13631427141', 'nrd6yvgh3ohr@163.com', NULL, '1', '1979-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (8631, 'dpd2nvma', '舒影', NULL, '和秀娟', '13716862348', 'ljq8gh@163.com', NULL, '1', '2005-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (8708, 'xlrhjb', '寒伊', NULL, '邹霞香', '13757112274', 'xlrhjb@ask.com', NULL, '1', '1974-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (8768, '6o6sfd8ts', '妍妍', NULL, '南门妍', '13964414673', 'beik6pahsoq@yeah.net', NULL, '1', '1976-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 02:16:44');
INSERT INTO `user_info` VALUES (8778, 'uo1bmdw0a', '卿卿', NULL, '安卿', '13159242947', 'uo1bmdw0a@3721.net', NULL, '1', '2001-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (8839, '89admd614w', '阿全', NULL, '唐光', '13327965425', 'u1h4kuvoo@ask.com', NULL, '1', '1966-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (8880, 'wqd1e1rpy', '永健', NULL, '司徒新', '13963161736', 'wqd1e1rpy@ask.com', NULL, '1', '1974-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 08:22:40');
INSERT INTO `user_info` VALUES (8921, 'bq1zocx5', '菊兰', NULL, '尹芬芳', '13316231129', 'bq1zocx5@msn.com', NULL, '1', '1968-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:00:46');
INSERT INTO `user_info` VALUES (9077, 'ahvzrsifi9hn', '阿朗', NULL, '濮阳力明', '13986162131', 'ahvzrsifi9hn@sohu.com', NULL, '1', '1990-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (9208, 'dycti7ihmuyi', '凤洁', NULL, '陈凤洁', '13711848441', 'dycti7ihmuyi@163.com', NULL, '3', '1997-04-26', 'F', '2020-04-26 18:57:55', NULL);
INSERT INTO `user_info` VALUES (9243, 'nb86zy5p', '婉娴', NULL, '柳丹蓉', '13633548794', '9dthuaxxwb4@yahoo.com.cn', NULL, '1', '2003-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 03:23:33');
INSERT INTO `user_info` VALUES (9321, 'rwimf0', '阿强', NULL, '慕容强', '13931853795', '1yrilph@live.com', NULL, '1', '1995-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 02:16:44');
INSERT INTO `user_info` VALUES (9441, 'wtbuhlzihu', '阿永', NULL, '金栋维', '13995289837', 'wtbuhlzihu@263.net', NULL, '3', '1992-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 00:31:50');
INSERT INTO `user_info` VALUES (9493, 'nh3xwn22kjsx', '欢欢', NULL, '孟云', '13581411414', 'nh3xwn22kjsx@0355.net', NULL, '3', '1968-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (9501, 'hjbuhwxjun', '阿元', NULL, '任元', '13189583738', 'hjbuhwxjun@aol.com', NULL, '3', '1970-04-26', 'M', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (9572, '35hld0h4h6', '爽琬', NULL, '魏美', '13883985981', '35hld0h4h6@3721.net', NULL, '1', '1987-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 01:46:53');
INSERT INTO `user_info` VALUES (9580, 'i3fh5jqaqf', '婉娴', NULL, '唐婉娴', '13351629941', '615rad52w21x@163.com', NULL, '2', '1968-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 02:16:44');
INSERT INTO `user_info` VALUES (9593, 'bkas6bwy', '宜宜', NULL, '诸葛霄', '13813118752', 'ffcd8s@0355.net', NULL, '3', '1995-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 04:38:50');
INSERT INTO `user_info` VALUES (9619, 'kxhto40', '彩彩', NULL, '康萍玲', '13248121875', 'kxhto40@gmail.com', NULL, '3', '1984-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (9720, 'oqe5o5', '琦琦', NULL, '任媛', '13736748615', 'oqe5o5@sina.com', NULL, '1', '1988-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 19:01:24');
INSERT INTO `user_info` VALUES (9795, 'srltr7l4', '寒伊', NULL, '葛勤珍', '13984418628', '4dk4ze1@126.com', NULL, '1', '2003-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 05:19:40');
INSERT INTO `user_info` VALUES (9812, 'cl6v9x', '淑淑', NULL, '伏桂', '13113775417', 'pnkjweb8hg6@gmail.com', NULL, '1', '1992-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 09:12:53');
INSERT INTO `user_info` VALUES (9858, 'gsbw7ba', '毓毓', NULL, '钱慧', '13413776314', 'gsbw7ba@sina.com', NULL, '1', '1974-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 07:34:22');
INSERT INTO `user_info` VALUES (9983, 'kjc3svpk5x', '悦昭', NULL, '王芸菲', '13585261376', 'ka6mrr277c3@gmail.com', NULL, '3', '1993-04-26', 'F', '2020-04-26 18:57:55', '2020-04-26 06:02:36');

SET FOREIGN_KEY_CHECKS = 1;
