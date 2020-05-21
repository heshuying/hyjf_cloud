/*
Navicat MySQL Data Transfer

Source Server         : beta2_47.105.206.3
Source Server Version : 50725
Source Host           : 47.105.206.3:33306
Source Database       : hyjf_market

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-07-24 15:00:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ht_activity_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_activity_list`;
CREATE TABLE `ht_activity_list` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(60) NOT NULL COMMENT '活动名称',
  `time_start` int(10) unsigned NOT NULL COMMENT '活动开始时间',
  `time_end` int(10) unsigned NOT NULL COMMENT '活动结束时间',
  `img_pc` varchar(150) DEFAULT NULL,
  `img_app` varchar(150) DEFAULT NULL,
  `img_wei` varchar(150) DEFAULT NULL,
  `activity_pc_url` varchar(150) DEFAULT NULL,
  `activity_app_url` varchar(150) DEFAULT NULL,
  `activity_wei_url` varchar(150) DEFAULT NULL,
  `img` varchar(100) DEFAULT NULL COMMENT '主图',
  `qr` varchar(100) DEFAULT ' ' COMMENT '二维码',
  `platform` varchar(50) NOT NULL COMMENT '平台',
  `url_foreground` varchar(200) DEFAULT NULL COMMENT '前台地址',
  `url_background` varchar(200) DEFAULT NULL COMMENT '后台管理地址',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='活动列表';

-- ----------------------------
-- Table structure for ht_activity_user_guess
-- ----------------------------
DROP TABLE IF EXISTS `ht_activity_user_guess`;
CREATE TABLE `ht_activity_user_guess` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `grade` tinyint(2) unsigned NOT NULL COMMENT '竞猜档位，分1,2,3,4四挡',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COMMENT='2019.5.1活动竞猜';

-- ----------------------------
-- Table structure for ht_activity_user_reward
-- ----------------------------
DROP TABLE IF EXISTS `ht_activity_user_reward`;
CREATE TABLE `ht_activity_user_reward` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `activity_id` int(11) DEFAULT NULL COMMENT '活动id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `grade` tinyint(2) unsigned DEFAULT NULL COMMENT '奖励档位，分1,2,3,4四挡',
  `reward_name` varchar(20) DEFAULT NULL COMMENT '奖励名称',
  `reward_type` varchar(20) DEFAULT NULL COMMENT '奖励类型',
  `send_type` varchar(20) DEFAULT NULL COMMENT '发放方式',
  `send_status` tinyint(2) DEFAULT NULL COMMENT '发放状态 1-已发放 0-未发放',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`,`activity_id`,`grade`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COMMENT='活动奖励领取记录';

-- ----------------------------
-- Table structure for ht_ads
-- ----------------------------
DROP TABLE IF EXISTS `ht_ads`;
CREATE TABLE `ht_ads` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT '',
  `type_id` int(8) unsigned DEFAULT NULL COMMENT '广告类型',
  `url` varchar(200) DEFAULT NULL,
  `code` varchar(10) NOT NULL COMMENT '调用代码',
  `content` varchar(1000) DEFAULT NULL COMMENT '内容',
  `image` varchar(200) DEFAULT '',
  `order` int(6) DEFAULT '0' COMMENT '排序',
  `hits` int(10) DEFAULT '0',
  `status` tinyint(2) unsigned DEFAULT '0',
  `share_url` varchar(200) DEFAULT NULL COMMENT '分享url',
  `share_image` varchar(200) DEFAULT NULL COMMENT '分享图片url',
  `is_index` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否在首页特色banner位置显示，0为不显示，1显示',
  `start_time` varchar(19) DEFAULT NULL,
  `end_time` varchar(19) DEFAULT NULL,
  `is_end` tinyint(1) unsigned DEFAULT '0' COMMENT '是否已结束(只针对活动banner有效0:否,1:是)',
  `share_title` varchar(50) DEFAULT NULL,
  `activiti_image` varchar(200) DEFAULT NULL COMMENT '活动列表图',
  `activiti_desc` varchar(500) DEFAULT NULL COMMENT '活动描述',
  `share_content` varchar(1000) DEFAULT NULL COMMENT '共享内容',
  `client_type` tinyint(1) unsigned DEFAULT '0' COMMENT '客户端类型 0为pc广告  1为手机广告',
  `new_user_show` tinyint(1) unsigned DEFAULT '1' COMMENT '是否限制新手 1：限制 2：不限制',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `platform_type` tinyint(1) unsigned DEFAULT NULL COMMENT '拆分状态 1：android广告管理 2：ios广告管理 3: 微信广告管理',
  PRIMARY KEY (`id`),
  KEY `idx_typeid` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='新闻表（具体规则不明）';

-- ----------------------------
-- Table structure for ht_ads_type
-- ----------------------------
DROP TABLE IF EXISTS `ht_ads_type`;
CREATE TABLE `ht_ads_type` (
  `type_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) NOT NULL DEFAULT '',
  `code` varchar(20) DEFAULT NULL COMMENT '广告位代码',
  `order` int(6) DEFAULT '0' COMMENT '排序',
  `remark` varchar(100) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `client_type` tinyint(1) DEFAULT '0' COMMENT '客户端类型 0为pc广告  1为手机广告',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='广告类型';

-- ----------------------------
-- Table structure for ht_duiba_orders
-- ----------------------------
DROP TABLE IF EXISTS `ht_duiba_orders`;
CREATE TABLE `ht_duiba_orders` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `duiba_order_id` varchar(50) DEFAULT NULL COMMENT '兑吧订单号',
  `hy_order_id` varchar(50) DEFAULT NULL COMMENT '汇盈订单号',
  `user_name` varchar(30) DEFAULT NULL COMMENT '订单兑换人用户名',
  `true_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `user_id` int(11) unsigned DEFAULT NULL COMMENT '用户ID',
  `exchange_content` varchar(100) DEFAULT NULL COMMENT '兑换内容',
  `product_type` varchar(20) DEFAULT NULL COMMENT '商品类型',
  `selling_price` decimal(10,2) unsigned DEFAULT NULL COMMENT '售价',
  `marking_price` decimal(10,2) unsigned DEFAULT NULL COMMENT '划线价',
  `cost` decimal(10,2) unsigned DEFAULT NULL COMMENT '成本',
  `order_status` tinyint(1) unsigned DEFAULT NULL COMMENT '订单状态（0成功，1失败，2处理中）',
  `order_time` int(11) DEFAULT NULL COMMENT '下单时间',
  `completion_time` int(11) DEFAULT NULL COMMENT '完成时间',
  `delivery_status` tinyint(1) unsigned DEFAULT NULL COMMENT '发货状态（0待发货，1已发货）',
  `receiving_information` varchar(255) DEFAULT NULL COMMENT '收货信息',
  `recharge_state` varchar(255) DEFAULT NULL COMMENT '虚拟商品充值状态',
  `processing_state` tinyint(1) unsigned DEFAULT NULL COMMENT '处理状态（0通过，1取消）',
  `commodity_code` varchar(50) DEFAULT NULL COMMENT '商品编码',
  `exchange_rate` decimal(10,2) unsigned DEFAULT NULL COMMENT '汇率',
  `integral_price` int(11) DEFAULT NULL COMMENT '兑吧返回积分（计算售价的基础数据）',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `activation_type` tinyint(1) unsigned DEFAULT NULL COMMENT '订单有效状态（0有效，1无效）',
  `coupon_user_id` int(11) DEFAULT NULL COMMENT '优惠券用户表id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_duiba_order_id` (`duiba_order_id`),
  KEY `idx_order_time` (`order_time`),
  KEY `idx_completion_time` (`completion_time`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='兑吧订单表';

-- ----------------------------
-- Table structure for ht_duiba_points
-- ----------------------------
DROP TABLE IF EXISTS `ht_duiba_points`;
CREATE TABLE `ht_duiba_points` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(30) DEFAULT '' COMMENT '用户名',
  `true_name` varchar(20) DEFAULT '' COMMENT '真实姓名',
  `points` int(11) NOT NULL DEFAULT '0' COMMENT '当前操作积分数',
  `total` int(11) NOT NULL DEFAULT '0' COMMENT '当前操作后总积分数',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型 0:获取 1:使用 2:调增 3:调减',
  `business_name` tinyint(1) NOT NULL DEFAULT '0' COMMENT '积分业务名称: 0出借 1商品兑换 2订单取消',
  `duiba_order_id` varchar(50) DEFAULT NULL COMMENT '兑吧订单号',
  `hy_order_id` varchar(50) DEFAULT NULL COMMENT '汇盈订单号',
  `remark` varchar(20) DEFAULT '' COMMENT '备注',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `idx_username` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户积分明细表';

-- ----------------------------
-- Table structure for ht_duiba_points_modify
-- ----------------------------
DROP TABLE IF EXISTS `ht_duiba_points_modify`;
CREATE TABLE `ht_duiba_points_modify` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(30) DEFAULT '' COMMENT '用户名',
  `true_name` varchar(20) DEFAULT '' COMMENT '真实姓名',
  `modify_order_id` varchar(50) DEFAULT NULL COMMENT '申请订单号',
  `points` int(11) NOT NULL DEFAULT '0' COMMENT '调整积分数',
  `total` int(11) NOT NULL DEFAULT '0' COMMENT '当时调整后总积分数',
  `points_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '操作类型：0调增 1调减',
  `modify_name` varchar(30) DEFAULT '' COMMENT '调整人用户名',
  `review_name` varchar(30) DEFAULT '' COMMENT '审批人用户名',
  `modify_reason` varchar(50) DEFAULT '' COMMENT '调整原因',
  `flow_order` int(11) NOT NULL COMMENT '当前审批节点',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审核状态: 0待审核 1审核通过 2审核不通过 ',
  `remark` varchar(20) DEFAULT '' COMMENT '备注',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `idx_username` (`user_name`) USING BTREE,
  KEY `idx_modifyname` (`modify_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分调整申请表';

-- ----------------------------
-- Table structure for ht_invite_prize_conf
-- ----------------------------
DROP TABLE IF EXISTS `ht_invite_prize_conf`;
CREATE TABLE `ht_invite_prize_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prize_name` varchar(100) DEFAULT NULL COMMENT '奖品名称',
  `prize_quantity` int(10) DEFAULT '0' COMMENT '奖品数量',
  `recommend_quantity` int(3) DEFAULT '0' COMMENT '所需推荐星数量',
  `prize_reminder_quantity` int(10) DEFAULT '0' COMMENT '奖品剩余数量',
  `prize_group_code` varchar(13) DEFAULT NULL COMMENT '奖品分组',
  `prize_type` tinyint(1) DEFAULT '1' COMMENT '奖品类别 1：实物奖品，2：优惠券',
  `coupon_code` varchar(30) DEFAULT NULL,
  `prize_probability` decimal(5,2) DEFAULT NULL COMMENT '中奖概率',
  `prize_pic_url` varchar(255) DEFAULT NULL COMMENT '实物奖品图片',
  `prize_kind` tinyint(1) DEFAULT '1' COMMENT '奖品类别  1：兑奖，2：抽奖',
  `prize_sort` int(3) DEFAULT '1' COMMENT '奖品排序  ',
  `prize_status` int(1) DEFAULT '0' COMMENT '奖品状态，0：启用，1：禁用',
  `success_message` varchar(255) DEFAULT NULL COMMENT '兑奖成功时的提示消息',
  `prize_apply_time` int(10) DEFAULT NULL COMMENT '奖品的适用时间，以月为单位',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奖品配置表';

-- ----------------------------
-- Table structure for ht_inviter_return_detail
-- ----------------------------
DROP TABLE IF EXISTS `ht_inviter_return_detail`;
CREATE TABLE `ht_inviter_return_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(32) NOT NULL COMMENT '账户名',
  `true_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `return_performance` decimal(18,2) DEFAULT NULL COMMENT '单笔当月产生的业绩',
  `return_amount` decimal(18,2) DEFAULT NULL COMMENT '获得返现金额',
  `tender_name` varchar(32) DEFAULT NULL COMMENT '投资人账户名',
  `tender_no` varchar(32) DEFAULT NULL COMMENT '投资订单号',
  `tender_amount` decimal(18,2) DEFAULT NULL COMMENT '单笔投资金额',
  `term` varchar(10) DEFAULT NULL COMMENT '投资期限',
  `product_type` varchar(10) DEFAULT NULL COMMENT '产品类型',
  `product_no` varchar(32) DEFAULT NULL COMMENT '产品编号',
  `join_time` varchar(32) DEFAULT NULL COMMENT '加入时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `debt_id` int(11) DEFAULT NULL COMMENT '部门id',
  `debt_name` varchar(32) DEFAULT NULL COMMENT '部门名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='邀请人返现明细';

-- ----------------------------
-- Table structure for ht_new_year_activity
-- ----------------------------
DROP TABLE IF EXISTS `ht_new_year_activity`;
CREATE TABLE `ht_new_year_activity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `true_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别 0 未知 1 男 2女',
  `money_lines` varchar(100) DEFAULT NULL COMMENT '活动钱台词',
  `invest_amount` decimal(21,2) DEFAULT '0.00' COMMENT '累计年化出借金额(元)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='春节活动表';

-- ----------------------------
-- Table structure for ht_new_year_activity_reward
-- ----------------------------
DROP TABLE IF EXISTS `ht_new_year_activity_reward`;
CREATE TABLE `ht_new_year_activity_reward` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `true_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `recipient_name` varchar(10) DEFAULT NULL COMMENT '收件人姓名',
  `recipient_address` varchar(100) DEFAULT NULL COMMENT '收件人地址',
  `recipient_mobile` varchar(11) DEFAULT NULL COMMENT '收件人手机号',
  `reward` varchar(50) DEFAULT NULL COMMENT '奖励名称',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发放状态 1 已发放 0 未发放',
  `release_time` datetime DEFAULT NULL COMMENT '发放时间',
  `get_time` datetime DEFAULT NULL COMMENT '获得时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='春节活动奖品明细表';

-- ----------------------------
-- Table structure for ht_nm_user
-- ----------------------------
DROP TABLE IF EXISTS `ht_nm_user`;
CREATE TABLE `ht_nm_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(20) NOT NULL COMMENT '汇盈账号',
  `true_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `debt_id` int(11) NOT NULL COMMENT '部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='纳觅用户表';

-- ----------------------------
-- Table structure for ht_performance_return_detail
-- ----------------------------
DROP TABLE IF EXISTS `ht_performance_return_detail`;
CREATE TABLE `ht_performance_return_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(32) NOT NULL COMMENT '账户名',
  `true_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `reffer_name` varchar(32) DEFAULT NULL COMMENT '邀请人账户名',
  `tender_no` varchar(32) DEFAULT NULL COMMENT '投资订单号',
  `tender_amount` decimal(18,2) DEFAULT NULL COMMENT '单笔投资金额',
  `term` varchar(10) DEFAULT NULL COMMENT '投资期限',
  `product_type` varchar(10) DEFAULT NULL COMMENT '产品类型',
  `product_no` varchar(32) DEFAULT NULL COMMENT '产品编号',
  `return_performance` decimal(18,2) DEFAULT NULL COMMENT '单笔当月产生的业绩',
  `return_amount` decimal(18,2) DEFAULT NULL COMMENT '单笔返现金额',
  `join_time` varchar(32) DEFAULT NULL COMMENT '加入时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='业绩返现详情';

-- ----------------------------
-- Table structure for ht_sell_daily
-- ----------------------------
DROP TABLE IF EXISTS `ht_sell_daily`;
CREATE TABLE `ht_sell_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_str` varchar(10) NOT NULL COMMENT '统计时间 yyyy.MM.dd',
  `draw_order` int(11) NOT NULL COMMENT '绘制顺序',
  `primary_division` varchar(150) NOT NULL COMMENT '一级部门',
  `two_division` varchar(150) NOT NULL COMMENT '二级部门',
  `store_num` int(11) NOT NULL COMMENT '门店数量',
  `invest_total_month` decimal(13,2) DEFAULT '0.00' COMMENT '本月累计规模业绩',
  `repayment_total_month` decimal(13,2) DEFAULT '0.00' COMMENT '本月累计已还款',
  `invest_total_previous_month` decimal(13,2) DEFAULT '0.00' COMMENT '上月对应累计规模业绩',
  `invest_ratio_growth` varchar(10) DEFAULT '/' COMMENT '环比增速（本月累计规模业绩/上月对应累计规模业绩 - 1）',
  `withdraw_total_month` decimal(13,2) DEFAULT '0.00' COMMENT '本月累计提现',
  `withdraw_rate` varchar(10) DEFAULT '/' COMMENT '提现占比（本月累计提现/本月累计已还款）',
  `recharge_total_month` decimal(13,2) DEFAULT '0.00' COMMENT '本月累计充值',
  `invest_annual_total_month` decimal(13,2) DEFAULT '0.00' COMMENT '本月累计年化业绩',
  `invest_annual_total_previous_month` decimal(13,2) DEFAULT '0.00' COMMENT '上月累计年化业绩',
  `invest_annular_ratio_growth` varchar(10) DEFAULT '/' COMMENT '环比增速（本月累计年化业绩/上月累计年化业绩-1）',
  `invest_total_yesterday` decimal(13,2) DEFAULT '0.00' COMMENT '昨日规模业绩',
  `repayment_total_yesterday` decimal(13,2) DEFAULT '0.00' COMMENT '昨日还款',
  `invest_annual_total_yesterday` decimal(13,2) DEFAULT '0.00' COMMENT '昨日年化业绩',
  `withdraw_total_yesterday` decimal(13,2) DEFAULT '0.00' COMMENT '昨日提现',
  `recharge_total_yesterday` decimal(13,2) DEFAULT '0.00' COMMENT '昨日充值',
  `net_capital_inflow_yesterday` decimal(13,2) DEFAULT '0.00' COMMENT '昨日净资金流（充值-提现）',
  `non_repayment_today` decimal(13,2) DEFAULT '0.00' COMMENT '当日待还',
  `register_total_yesterday` int(11) DEFAULT '0' COMMENT '昨日注册数',
  `recharge_gt3000_user_num` int(11) DEFAULT '0' COMMENT '充值≥3000人数（昨日注册）',
  `invest_gt3000_user_num` int(11) DEFAULT '0' COMMENT '投资≥3000人数（昨日注册）',
  `invest_gt3000_month_user_num` int(11) DEFAULT '0' COMMENT '本月累计投资3000以上新客户数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24993 DEFAULT CHARSET=utf8 COMMENT='销售日报数据表';

-- ----------------------------
-- Table structure for ht_sell_daily_distribution
-- ----------------------------
DROP TABLE IF EXISTS `ht_sell_daily_distribution`;
CREATE TABLE `ht_sell_daily_distribution` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `business_name` varchar(30) NOT NULL COMMENT '业务名称',
  `email` varchar(200) NOT NULL COMMENT '联系人邮箱',
  `time_point` tinyint(1) NOT NULL DEFAULT '1' COMMENT '时间点 1:每个工作日 2:每天  3:每月第一个工作日',
  `time` time DEFAULT '07:00:00' COMMENT '邮件发送具体时间',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1:有效,2:无效',
  `create_name` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_name` varchar(50) DEFAULT NULL COMMENT '更新人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='销售日报配置表';
