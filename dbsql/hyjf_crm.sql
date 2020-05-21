/*
Navicat MySQL Data Transfer

Source Server         : beta2_47.105.206.3
Source Server Version : 50725
Source Host           : 47.105.206.3:33306
Source Database       : hyjf_crm

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-07-24 15:41:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for crm_admin_and_role
-- ----------------------------
DROP TABLE IF EXISTS `crm_admin_and_role`;
CREATE TABLE `crm_admin_and_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2748 DEFAULT CHARSET=utf8 COMMENT='用户角色关联关系表';

-- ----------------------------
-- Table structure for crm_admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `crm_admin_menu`;
CREATE TABLE `crm_admin_menu` (
  `menu_uuid` varchar(36) NOT NULL COMMENT '文档ID',
  `menu_puuid` varchar(36) NOT NULL DEFAULT '0' COMMENT '上级分类ID',
  `menu_ctrl` varchar(20) DEFAULT NULL COMMENT '权限名称',
  `menu_icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `menu_name` varchar(50) DEFAULT '' COMMENT '标题',
  `menu_sort` int(10) unsigned DEFAULT '0' COMMENT '排序（同级有效）',
  `menu_url` varchar(255) DEFAULT '' COMMENT '链接地址',
  `menu_hide` int(1) unsigned DEFAULT '0' COMMENT '是否隐藏',
  `menu_tip` varchar(255) DEFAULT '' COMMENT '提示',
  `del_flag` varchar(1) DEFAULT '0' COMMENT '删除FLAG    0,有效；1，删除',
  `createtime` varchar(20) DEFAULT NULL COMMENT '添加时间',
  `updatetime` varchar(20) DEFAULT NULL COMMENT '添加者',
  `createuser` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `updateuser` varchar(20) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`menu_uuid`),
  KEY `pid` (`menu_puuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单表';

-- ----------------------------
-- Table structure for crm_admin_menu_permssions
-- ----------------------------
DROP TABLE IF EXISTS `crm_admin_menu_permssions`;
CREATE TABLE `crm_admin_menu_permssions` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(36) DEFAULT NULL COMMENT '菜单ID',
  `permission_id` varchar(36) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=663 DEFAULT CHARSET=utf8 COMMENT='菜单权限关联关系表';

-- ----------------------------
-- Table structure for crm_admin_permissions
-- ----------------------------
DROP TABLE IF EXISTS `crm_admin_permissions`;
CREATE TABLE `crm_admin_permissions` (
  `permission_uuid` varchar(36) NOT NULL DEFAULT '' COMMENT '权限编号',
  `permission` varchar(20) DEFAULT NULL COMMENT '权限按钮ID',
  `permission_name` varchar(20) DEFAULT NULL COMMENT '权限名称',
  `description` text COMMENT '描述',
  `del_flag` varchar(1) DEFAULT '0' COMMENT '删除FLAG    0,有效；1，删除',
  `createtime` varchar(20) DEFAULT NULL COMMENT '添加时间',
  `updatetime` varchar(20) DEFAULT NULL COMMENT '添加者',
  `createuser` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `updateuser` varchar(20) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`permission_uuid`),
  KEY `pid` (`permission_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='权限表';

-- ----------------------------
-- Table structure for crm_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `crm_admin_role`;
CREATE TABLE `crm_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `description` text COMMENT '描述',
  `sort` int(5) DEFAULT '0' COMMENT '排序',
  `state` varchar(1) DEFAULT '1' COMMENT '状态',
  `role_type` int(1) DEFAULT NULL COMMENT '角色类别：0系统角色 1 用户角色',
  `del_flag` varchar(1) DEFAULT '0' COMMENT '删除FLAG    0,有效；1，删除',
  `createtime` varchar(20) DEFAULT NULL COMMENT '添加时间',
  `updatetime` varchar(20) DEFAULT NULL COMMENT '添加者',
  `createuser` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `updateuser` varchar(20) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Table structure for crm_admin_role_menu_permissions
-- ----------------------------
DROP TABLE IF EXISTS `crm_admin_role_menu_permissions`;
CREATE TABLE `crm_admin_role_menu_permissions` (
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `menu_uuid` varchar(36) NOT NULL COMMENT '菜单编号',
  `permission_uuid` varchar(36) NOT NULL COMMENT '权限编号',
  `del_flag` varchar(1) DEFAULT '0' COMMENT '删除FLAG    0,有效；1，删除',
  `createtime` varchar(20) DEFAULT NULL COMMENT '添加时间',
  `updatetime` varchar(20) DEFAULT NULL COMMENT '添加者',
  `createuser` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `updateuser` varchar(20) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`role_id`,`menu_uuid`,`permission_uuid`),
  KEY `pid` (`menu_uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色权限关联关系表';

-- ----------------------------
-- Table structure for crm_borrow_tender
-- ----------------------------
DROP TABLE IF EXISTS `crm_borrow_tender`;
CREATE TABLE `crm_borrow_tender` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态',
  `borrow_nid` varchar(50) DEFAULT '0',
  `nid` varchar(100) NOT NULL DEFAULT '',
  `account_tender` decimal(11,2) DEFAULT '0.00',
  `account` decimal(11,2) DEFAULT '0.00',
  `change_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '债权转让',
  `change_userid` int(11) NOT NULL COMMENT '转让人',
  `change_period` int(11) NOT NULL COMMENT '债权转让期数',
  `tender_status` tinyint(2) NOT NULL COMMENT '投资状态',
  `tender_nid` varchar(50) NOT NULL DEFAULT '',
  `tender_award_account` decimal(11,2) NOT NULL COMMENT '投资奖励金额',
  `recover_full_status` int(2) NOT NULL COMMENT '是否已回收完成',
  `recover_fee` decimal(11,2) NOT NULL COMMENT '回收的总费用',
  `recover_type` varchar(100) NOT NULL COMMENT '回收类型',
  `recover_account_all` decimal(11,2) DEFAULT '0.00' COMMENT '收款总额',
  `recover_account_interest` decimal(11,2) DEFAULT '0.00' COMMENT '收款总利息',
  `recover_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收总额',
  `recover_account_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收利息',
  `recover_account_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收本金',
  `recover_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收总额',
  `recover_account_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收利息',
  `recover_account_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收本金',
  `recover_times` int(10) DEFAULT '0' COMMENT '已收期数',
  `recover_advance_fee` decimal(11,2) NOT NULL COMMENT '提前还款费用',
  `recover_late_fee` decimal(11,2) NOT NULL COMMENT '逾期还款费用',
  `tender_award_fee` decimal(11,2) NOT NULL COMMENT '投资奖励增加',
  `loan_amount` decimal(10,2) DEFAULT '0.00' COMMENT '放款金额',
  `loan_fee` decimal(10,2) DEFAULT '0.00' COMMENT '服务费',
  `contents` varchar(250) NOT NULL,
  `auto_status` tinyint(2) NOT NULL DEFAULT '0',
  `web_status` tinyint(2) NOT NULL COMMENT '网站投标',
  `api_status` tinyint(1) DEFAULT '0' COMMENT '放款状态',
  `addtime` int(1) NOT NULL DEFAULT '0',
  `addip` varchar(15) DEFAULT '',
  `period_status` tinyint(1) NOT NULL DEFAULT '0',
  `isok` tinyint(1) DEFAULT '0',
  `is_report` tinyint(4) DEFAULT '0',
  `flag` tinyint(1) DEFAULT '0' COMMENT '专用标记',
  `activity_flag` tinyint(1) DEFAULT '0' COMMENT '活动专用标志',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `web` tinyint(1) NOT NULL DEFAULT '0' COMMENT '网站收支计算标识',
  `invite_user_name` varchar(100) DEFAULT '',
  `invite_user_id` int(11) NOT NULL DEFAULT '0',
  `invite_region_id` int(11) NOT NULL DEFAULT '0',
  `invite_region_name` varchar(100) NOT NULL DEFAULT '',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT '',
  `invite_department_id` int(11) NOT NULL DEFAULT '0',
  `invite_department_name` varchar(100) NOT NULL DEFAULT '',
  `tender_user_attribute` int(11) NOT NULL DEFAULT '0',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0',
  `order_date` varchar(20) NOT NULL DEFAULT '0000-00-00' COMMENT '投资订单日期',
  `loan_order_date` varchar(20) DEFAULT '0000-00-00' COMMENT '放款订单日期',
  `loan_ordid` varchar(20) NOT NULL DEFAULT '',
  `tender_user_name` varchar(20) NOT NULL DEFAULT '',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `invest_type` tinyint(1) DEFAULT '0' COMMENT '投资类型 0手动投标 1预约投标 2自动投标',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nid` (`nid`),
  KEY `IDX_US_BO_AD` (`user_id`,`borrow_nid`),
  KEY `IDX_addtime` (`addtime`)
) ENGINE=InnoDB AUTO_INCREMENT=4677 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crm_config_entry
-- ----------------------------
DROP TABLE IF EXISTS `crm_config_entry`;
CREATE TABLE `crm_config_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sort` int(3) NOT NULL COMMENT '序号',
  `change_type` int(3) DEFAULT NULL COMMENT '配置类型，保留字段',
  `time_limit` int(3) DEFAULT NULL COMMENT '天数',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态 0，禁用； 1，启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `createtime` datetime DEFAULT NULL COMMENT '提交时间',
  `creater` varchar(50) DEFAULT NULL COMMENT '提交人',
  `updatatime` datetime DEFAULT NULL COMMENT '修改时间',
  `updataer` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='职员工入职时间配置';

-- ----------------------------
-- Table structure for crm_config_protect
-- ----------------------------
DROP TABLE IF EXISTS `crm_config_protect`;
CREATE TABLE `crm_config_protect` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sort` smallint(3) unsigned NOT NULL DEFAULT '0' COMMENT '序号',
  `change_type` smallint(3) DEFAULT NULL COMMENT '配置类型，保留字段',
  `time_limit` smallint(3) DEFAULT NULL COMMENT '天数',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态 0，禁用； 1，启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `createtime` datetime DEFAULT NULL,
  `updatatime` datetime DEFAULT NULL,
  `creater` varchar(20) DEFAULT '' COMMENT '更新时间',
  `updataer` varchar(20) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='修改推荐人保护期时间配置';

-- ----------------------------
-- Table structure for crm_config_referrer
-- ----------------------------
DROP TABLE IF EXISTS `crm_config_referrer`;
CREATE TABLE `crm_config_referrer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sort` int(3) NOT NULL COMMENT '序号',
  `change_type` int(3) DEFAULT NULL COMMENT '修改类型 1，无主单配置；2，有主单配置',
  `time_limit` int(3) DEFAULT NULL COMMENT '天数',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态 0，禁用； 1，启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `createtime` datetime DEFAULT NULL COMMENT '提交时间',
  `creater` varchar(50) DEFAULT NULL COMMENT '提交人',
  `updatatime` datetime DEFAULT NULL COMMENT '修改时间',
  `updataer` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=494 DEFAULT CHARSET=utf8 COMMENT='修改客户推荐人管理';

-- ----------------------------
-- Table structure for crm_contract_info
-- ----------------------------
DROP TABLE IF EXISTS `crm_contract_info`;
CREATE TABLE `crm_contract_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parta` varchar(100) NOT NULL COMMENT '甲方名称',
  `partb` varchar(100) NOT NULL COMMENT '乙方名称',
  `transfer_price` decimal(15,2) NOT NULL COMMENT '转让价款',
  `card_number` varchar(20) NOT NULL COMMENT '证件号码',
  `id_card` varchar(20) NOT NULL COMMENT '身份证号码',
  `create_time` int(11) NOT NULL COMMENT '生成时间',
  `user_id` int(11) NOT NULL COMMENT '操作者',
  `file_path` varchar(100) NOT NULL COMMENT '合同文件路径',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否被删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='生成合同信息';

-- ----------------------------
-- Table structure for crm_customer
-- ----------------------------
DROP TABLE IF EXISTS `crm_customer`;
CREATE TABLE `crm_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_origin_id` int(11) NOT NULL COMMENT '第三方平台客户ID',
  `customer_card_id` varchar(18) NOT NULL COMMENT '客户身份证号，冗余存储，便于查询',
  `customer_origin_instcode` int(8) DEFAULT NULL COMMENT '客户来源',
  `platform_operate_instcode` int(8) DEFAULT NULL COMMENT '操作平台',
  `customer_property` int(1) DEFAULT NULL COMMENT '用户属性，0=>无主单 1=>有主单 2=>线下员工 3=>线上员工''',
  `recommend_card_id` varchar(18) DEFAULT NULL COMMENT '推荐人身 份证号',
  `recommend_customer_id` int(11) DEFAULT NULL COMMENT '推荐人客户ID',
  `add_time` timestamp NULL DEFAULT NULL,
  `add_user` varchar(255) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(255) DEFAULT NULL,
  `del_flg` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0：未删除，1：已删除',
  PRIMARY KEY (`id`),
  KEY `idx_customer_card_id` (`customer_card_id`),
  KEY `idx_recommend_card_id` (`recommend_card_id`),
  KEY `idx_customer_origin_id` (`customer_origin_id`),
  KEY `idx_customer_origin_instcode` (`customer_origin_instcode`)
) ENGINE=InnoDB AUTO_INCREMENT=16909 DEFAULT CHARSET=utf8 COMMENT='客户推荐人、属性表';

-- ----------------------------
-- Table structure for crm_customer_origin
-- ----------------------------
DROP TABLE IF EXISTS `crm_customer_origin`;
CREATE TABLE `crm_customer_origin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform_instcode` int(8) DEFAULT NULL COMMENT '机构编号',
  `customer_id` varchar(255) DEFAULT NULL COMMENT '客户第三方平台的ID号',
  `customer_role` tinyint(1) DEFAULT NULL COMMENT '0:投资人，1：借款人',
  `customer_name` varchar(255) DEFAULT NULL COMMENT '客户姓名',
  `customer_username` varchar(255) DEFAULT NULL COMMENT '客户用户名',
  `customer_mobile` varchar(11) DEFAULT NULL COMMENT '客户手机号',
  `customer_card_id` varchar(18) NOT NULL COMMENT '客户身份证号',
  `register_time` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `opening_time` timestamp NULL DEFAULT NULL COMMENT ' 开户时间',
  `recommend_card_id` varchar(18) DEFAULT NULL COMMENT '推荐人身 份证号',
  `recommend_name` varchar(255) DEFAULT NULL,
  `recommond_username` varchar(255) DEFAULT NULL,
  `recommend_mobile` varchar(11) DEFAULT NULL COMMENT '推荐人客户ID',
  `recommond_id` varchar(255) DEFAULT NULL,
  `available_balance` decimal(11,2) DEFAULT NULL,
  `pending_amount` decimal(11,2) DEFAULT NULL,
  `insert_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_customer_card_id` (`customer_card_id`),
  KEY `idx_platform_instcode` (`platform_instcode`),
  KEY `idx_customer_username` (`customer_username`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_opening_time` (`opening_time`),
  KEY `idx_recommond_username` (`recommond_username`)
) ENGINE=InnoDB AUTO_INCREMENT=8092 DEFAULT CHARSET=utf8 COMMENT='第三方平台导入原始客户信息';

-- ----------------------------
-- Table structure for crm_debt_plan_accede
-- ----------------------------
DROP TABLE IF EXISTS `crm_debt_plan_accede`;
CREATE TABLE `crm_debt_plan_accede` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plan_nid` varchar(20) NOT NULL COMMENT '计划nid',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `user_attribute` int(11) NOT NULL DEFAULT '0',
  `accede_order_id` varchar(50) NOT NULL COMMENT '计划加入订单号',
  `freeze_order_id` varchar(30) NOT NULL COMMENT '加入时的冻结订单号',
  `accede_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '计划加入总金额',
  `accede_balance` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '可用余额',
  `accede_frost` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '计划订单冻结金额（专指投资汇添金专属标的冻结）',
  `liquidates_credit_frost` decimal(11,2) DEFAULT '0.00' COMMENT '清算冻结的余额',
  `liquidates_credit_balance` decimal(11,2) DEFAULT '0.00' COMMENT '清算时待冻结的余额',
  `repay_balance` decimal(11,2) DEFAULT '0.00' COMMENT '还款待冻结金额，不增加',
  `liquidates_repay_frost` decimal(11,2) DEFAULT '0.00' COMMENT '清算还款冻结金额',
  `service_fee_rate` decimal(11,2) DEFAULT '0.00' COMMENT '服务费收取比例',
  `under_take_times` int(2) NOT NULL DEFAULT '0' COMMENT '承接次数',
  `invest_max` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '最大投资金额（参考值）',
  `invest_min` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '最小投资金额（参考值）',
  `cycle_times` int(10) NOT NULL DEFAULT '0' COMMENT '遍历次数（已经尝试自动投资次数）',
  `repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本息',
  `repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `repay_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本息',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_account_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本息',
  `repay_capital_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `invite_user_id` int(11) DEFAULT '0',
  `invite_user_attribute` int(11) DEFAULT '0',
  `invite_user_name` varchar(100) DEFAULT NULL,
  `invite_region_id` int(11) DEFAULT '0',
  `invite_region_name` varchar(100) DEFAULT ' ',
  `invite_branch_id` int(11) DEFAULT '0',
  `invite_branch_name` varchar(100) DEFAULT ' ',
  `invite_department_id` int(11) DEFAULT '0',
  `invite_department_name` varchar(100) DEFAULT ' ',
  `send_status` tinyint(1) DEFAULT NULL COMMENT '协议发送状态0未发送 1已发送',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '此笔加入是否已经完成 0投资中 1投资完成 2还款中 3还款完成',
  `reinvest_status` tinyint(1) DEFAULT '0' COMMENT '是否复投 0非复投 1复投',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识',
  `client` tinyint(1) DEFAULT NULL COMMENT '客户端 0PC，1微信，2安卓APP，3IOS，4其他',
  `create_time` int(11) NOT NULL COMMENT '创建时间（加入时间）',
  `create_user_id` int(11) NOT NULL COMMENT '创建者id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建者用户名',
  `update_time` int(11) DEFAULT NULL COMMENT '更新时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1176 DEFAULT CHARSET=utf8 COMMENT='汇添金计划加入明细表\r\n';

-- ----------------------------
-- Table structure for crm_hjh_invite_info
-- ----------------------------
DROP TABLE IF EXISTS `crm_hjh_invite_info`;
CREATE TABLE `crm_hjh_invite_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `hjh_tender_id` int(11) NOT NULL COMMENT '投资id',
  `invite_user_name` varchar(100) DEFAULT '',
  `invite_user_id` int(11) NOT NULL DEFAULT '0',
  `invite_region_id` int(11) NOT NULL DEFAULT '0',
  `invite_region_name` varchar(100) NOT NULL DEFAULT '',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT '',
  `invite_department_id` int(11) NOT NULL DEFAULT '0',
  `invite_department_name` varchar(100) NOT NULL DEFAULT '',
  `tender_user_attribute` int(11) DEFAULT '0',
  `invite_user_attribute` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='汇计划投资推荐人信息表';

-- ----------------------------
-- Table structure for crm_invest_platform
-- ----------------------------
DROP TABLE IF EXISTS `crm_invest_platform`;
CREATE TABLE `crm_invest_platform` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform_name` varchar(20) NOT NULL COMMENT '投资平台名称',
  `platform_no` int(8) NOT NULL COMMENT '投资平台代码',
  `comments` varchar(50) DEFAULT NULL COMMENT '备注',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_platform_no` (`platform_no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='投资平台表';

-- ----------------------------
-- Table structure for crm_invest_product
-- ----------------------------
DROP TABLE IF EXISTS `crm_invest_product`;
CREATE TABLE `crm_invest_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(20) NOT NULL COMMENT '产品类型名称',
  `product_show_name` varchar(20) NOT NULL DEFAULT '' COMMENT '前台展示名称',
  `product_no` int(8) NOT NULL COMMENT '产品类型代码',
  `product_sort` int(11) NOT NULL DEFAULT '0' COMMENT '前台展示顺序（相同按id正序）',
  `comments` varchar(50) DEFAULT NULL COMMENT '备注',
  `flag` tinyint(1) DEFAULT '0' COMMENT '前台是否展示（1展示、0隐藏）',
  `creator_id` int(11) DEFAULT NULL,
  `creator_time` datetime DEFAULT NULL,
  `updater_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_product_no` (`product_no`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='产品类型表';

-- ----------------------------
-- Table structure for crm_investment_details
-- ----------------------------
DROP TABLE IF EXISTS `crm_investment_details`;
CREATE TABLE `crm_investment_details` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT '0' COMMENT '客户id',
  `id_num` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `referrer_customer_id` int(11) DEFAULT '0' COMMENT '推荐人客户id',
  `referrer_employee_id` int(11) DEFAULT '0' COMMENT '推荐人员工id',
  `referrer_id_card` varchar(20) DEFAULT NULL COMMENT '推荐人身份证号',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  `borrow_type` varchar(50) DEFAULT '' COMMENT '产品类型',
  `borrow_nid` varchar(50) DEFAULT '' COMMENT '产品编号',
  `investment_nid` varchar(50) DEFAULT '' COMMENT '投资编号',
  `unit` tinyint(1) unsigned DEFAULT NULL COMMENT '标期单位(天1，月2，年3)',
  `term` varchar(10) DEFAULT '' COMMENT '产品期限',
  `accede_order_id` varchar(50) DEFAULT NULL COMMENT '汇计划加入订单号',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `add_time` int(11) DEFAULT '0' COMMENT '投资时间',
  `loan_time` int(11) DEFAULT '0' COMMENT '满标放款日期(投资成功日期)',
  `tactics_nid` varchar(50) DEFAULT '' COMMENT '策略编号',
  `tactics_name` varchar(50) DEFAULT '' COMMENT '策略名称',
  `tactics_account` decimal(11,2) DEFAULT '0.00' COMMENT '策略投资金额',
  `tactics_add_time` int(11) DEFAULT '0' COMMENT '策略投资时间',
  `tactics_investment_nid` varchar(50) DEFAULT '' COMMENT '宽策略标的投资编号',
  `tactics_order_nid` varchar(50) DEFAULT '' COMMENT '宽策略订单编号',
  `inst_code` varchar(50) DEFAULT '' COMMENT '机构编号',
  `product_no` int(11) DEFAULT NULL COMMENT '产品类型编号',
  `first_flag` tinyint(1) DEFAULT '0' COMMENT '是否首次投资',
  `referrer_department_id` int(11) DEFAULT NULL COMMENT '推荐人所在部门id',
  PRIMARY KEY (`id`),
  KEY `idx_loan_time` (`loan_time`),
  KEY `idx_ci_ic` (`customer_id`,`inst_code`),
  KEY `idx_investment_nid` (`investment_nid`),
  KEY `idx_referrer_employee_id` (`referrer_employee_id`),
  KEY `idx_referrer_customer_id` (`referrer_customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=180936 DEFAULT CHARSET=utf8 COMMENT='投资详情表';

-- ----------------------------
-- Table structure for crm_investment_referrer_info
-- ----------------------------
DROP TABLE IF EXISTS `crm_investment_referrer_info`;
CREATE TABLE `crm_investment_referrer_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `investment_id` int(11) NOT NULL COMMENT '投资id',
  `referrer_customer_id` int(11) DEFAULT '0' COMMENT '推荐人客户id',
  `referrer_employee_id` int(11) DEFAULT '0' COMMENT '推荐人员工id',
  `referrer_department_id` int(11) DEFAULT NULL COMMENT '推荐人所在部门id',
  PRIMARY KEY (`id`),
  KEY `idx_investment_id` (`investment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='投资详情推荐人关联表';

-- ----------------------------
-- Table structure for crm_param_name
-- ----------------------------
DROP TABLE IF EXISTS `crm_param_name`;
CREATE TABLE `crm_param_name` (
  `name_class` varchar(20) NOT NULL,
  `name_cd` varchar(6) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `other1` varchar(255) DEFAULT NULL,
  `other2` varchar(255) DEFAULT NULL,
  `other3` varchar(255) DEFAULT NULL,
  `sort` int(5) DEFAULT '0',
  `del_flag` varchar(1) DEFAULT '0' COMMENT '删除FLAG',
  `createtime` varchar(20) DEFAULT NULL COMMENT '添加时间',
  `updatetime` varchar(20) DEFAULT NULL COMMENT '添加者',
  `createuser` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `updateuser` varchar(20) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`name_class`,`name_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for crm_referrer_change_log
-- ----------------------------
DROP TABLE IF EXISTS `crm_referrer_change_log`;
CREATE TABLE `crm_referrer_change_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '客户id',
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `user_realname` varchar(30) DEFAULT NULL COMMENT '客户姓名',
  `mobile` char(11) DEFAULT NULL,
  `old_referrer` int(11) DEFAULT NULL COMMENT '原推荐人id',
  `old_referrername` varchar(30) DEFAULT NULL COMMENT '原推荐人用户名',
  `old_referrer_realname` varchar(30) DEFAULT NULL COMMENT '原推荐人姓名',
  `old_referrer_mobile` char(11) DEFAULT NULL,
  `old_departid` int(10) DEFAULT NULL,
  `old_departname` varchar(50) DEFAULT NULL COMMENT '原来团队',
  `old_branchid` int(10) DEFAULT NULL,
  `old_branchname` varchar(50) DEFAULT NULL COMMENT '原来分部',
  `old_regionid` int(10) DEFAULT NULL,
  `old_regionname` varchar(50) DEFAULT NULL COMMENT '原来分公司',
  `new_referrer` int(11) DEFAULT NULL COMMENT '新推荐人id',
  `new_referrername` varchar(30) DEFAULT NULL COMMENT '新推荐人用户名',
  `new_referrer_realname` varchar(30) DEFAULT NULL COMMENT '新推荐人姓名',
  `new_referrer_mobile` char(11) DEFAULT NULL COMMENT '新推荐人电话',
  `new_departid` int(10) DEFAULT NULL,
  `new_departname` varchar(50) DEFAULT NULL COMMENT '团队',
  `new_branchid` int(10) DEFAULT NULL,
  `new_branchname` varchar(50) DEFAULT NULL COMMENT '分部',
  `new_regionid` int(10) DEFAULT NULL,
  `new_regionname` varchar(50) DEFAULT NULL COMMENT '分公司',
  `reason` int(2) DEFAULT NULL COMMENT '修改原因   1. 忘填推荐人,2. 填错推荐人,3. 已离职员工本人,4. 已离职员工客户, 11.填错推荐人，12.用户要求修改， 13.团队经理下挂，14.新推荐人为用户亲友，15.将离职员工客户 99.其它',
  `reason_others` varchar(200) DEFAULT NULL COMMENT '其它原因',
  `creator` varchar(30) DEFAULT NULL COMMENT '创建人',
  `createtime` datetime DEFAULT NULL COMMENT '提交时间',
  `first_check_user` varchar(30) DEFAULT NULL COMMENT '一审操作人',
  `first_check_time` datetime DEFAULT NULL COMMENT '一审时间',
  `first_check_result` int(1) DEFAULT NULL COMMENT '一审结果  1，成功，0，失败',
  `first_check_note` varchar(200) DEFAULT NULL COMMENT '一审意见',
  `second_check_user` varchar(30) DEFAULT NULL COMMENT '二审操作人',
  `second_check_time` datetime DEFAULT NULL COMMENT '二审时间',
  `second_check_result` int(1) DEFAULT NULL COMMENT '二审结果  1，成功，0，失败',
  `second_check_note` varchar(200) DEFAULT NULL COMMENT '二审意见',
  `third_check_user` varchar(30) DEFAULT NULL COMMENT '财务确认操作人',
  `third_check_time` datetime DEFAULT NULL COMMENT '财务确认时间',
  `check_status` int(1) DEFAULT NULL COMMENT '状态 1，待一审，2，一审驳回，3，待二审，4，二审驳回，5，待财务确认，9，修改成功',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户id(crm_customer)',
  `old_referrer_customer_id` int(11) DEFAULT NULL COMMENT '原推荐人客户id(关联基本信息)',
  `old_referrer_id_card` varchar(20) DEFAULT NULL COMMENT '原推荐人身份证号(冗余存储)',
  `old_referrer_employee_id` int(11) DEFAULT NULL COMMENT '原推荐人员工id(绩效计算)',
  `new_referrer_customer_id` int(11) DEFAULT NULL COMMENT '新推荐人客户id(关联基本信息)',
  `new_referrer_id_card` varchar(20) DEFAULT NULL COMMENT '新推荐人身份证号(冗余存储)',
  `new_referrer_employee_id` int(11) DEFAULT NULL COMMENT '新推荐人员工id(绩效计算)',
  `create_timestamp` int(11) DEFAULT NULL COMMENT '提交时间',
  `first_check_timestamp` int(11) DEFAULT NULL COMMENT '一审时间',
  `second_check_timestamp` int(11) DEFAULT NULL COMMENT '二审时间',
  `user_leave_id` int(11) DEFAULT NULL COMMENT '关联离职记录id',
  PRIMARY KEY (`id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_create_timestamp` (`create_timestamp`),
  KEY `idx_check_status` (`check_status`)
) ENGINE=InnoDB AUTO_INCREMENT=1000645 DEFAULT CHARSET=utf8 COMMENT='修改客户推荐人日志';

-- ----------------------------
-- Table structure for crm_statistics_achievements
-- ----------------------------
DROP TABLE IF EXISTS `crm_statistics_achievements`;
CREATE TABLE `crm_statistics_achievements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` mediumint(8) NOT NULL COMMENT '员工ID',
  `employee_id_card` varchar(18) NOT NULL DEFAULT '' COMMENT '员工身份证号（冗余存储）',
  `department_id` int(5) NOT NULL COMMENT '三级部门ID',
  `platform_no` int(8) NOT NULL COMMENT '投资平台代码',
  `task_time` int(10) NOT NULL COMMENT '任务时间（格式：年月，例，201806）',
  `achievements_all` decimal(11,2) DEFAULT NULL COMMENT '业绩总额',
  `discount_achievements_all` decimal(11,2) DEFAULT NULL COMMENT '折比业绩总额',
  `reg_num` int(5) DEFAULT NULL COMMENT '注册人数',
  `open_num` int(5) DEFAULT NULL COMMENT '开户人数',
  `invest_num` int(5) DEFAULT NULL COMMENT '用户首次投资(投资≥3000)人数',
  PRIMARY KEY (`id`),
  KEY `idx_employee_id` (`employee_id`),
  KEY `idx_task_time` (`task_time`)
) ENGINE=InnoDB AUTO_INCREMENT=51564 DEFAULT CHARSET=utf8 COMMENT='投资绩效统计表';

-- ----------------------------
-- Table structure for crm_statistics_investments
-- ----------------------------
DROP TABLE IF EXISTS `crm_statistics_investments`;
CREATE TABLE `crm_statistics_investments` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `employee_id` mediumint(8) NOT NULL COMMENT '员工ID',
  `department_id` int(5) NOT NULL COMMENT '三级部门ID',
  `product_no` int(4) NOT NULL COMMENT '产品类型',
  `platform_no` int(8) NOT NULL COMMENT '投资平台代码',
  `task_time` int(10) NOT NULL COMMENT '任务时间（格式：年月，例，201806）',
  `loan_time` int(10) DEFAULT '0' COMMENT '满标放款日期(投资成功日期)',
  `time_unit` tinyint(2) unsigned DEFAULT NULL COMMENT '标期单位(天1，月2，年3)',
  `time_limit` tinyint(2) unsigned DEFAULT NULL COMMENT '期限',
  `invest_money` decimal(11,2) NOT NULL COMMENT '投资金额',
  `position_id` int(8) DEFAULT NULL COMMENT '统计时岗位名称',
  `level` int(8) DEFAULT NULL COMMENT '统计时角色等级',
  `temporary` tinyint(2) DEFAULT NULL COMMENT '统计时： 1,兼职/2,正式员工',
  PRIMARY KEY (`id`),
  KEY `loan_time_index` (`loan_time`) USING BTREE,
  KEY `idx_employee_id` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66276 DEFAULT CHARSET=utf8 COMMENT='投资明细统计表';

-- ----------------------------
-- Table structure for crm_stats_achievements_info
-- ----------------------------
DROP TABLE IF EXISTS `crm_stats_achievements_info`;
CREATE TABLE `crm_stats_achievements_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` mediumint(8) NOT NULL COMMENT '员工ID',
  `department_id` int(5) NOT NULL COMMENT '三级部门ID',
  `product_no` int(4) NOT NULL COMMENT '产品类型',
  `platform_no` int(8) NOT NULL COMMENT '投资平台代码',
  `task_time` int(10) NOT NULL COMMENT '任务时间（格式：年月，例，201806）',
  `achievements` decimal(11,2) DEFAULT NULL COMMENT '业绩总额',
  `discount_achievements` decimal(11,2) DEFAULT NULL COMMENT '折比业绩总额',
  PRIMARY KEY (`id`),
  KEY `idx_employee_id` (`employee_id`),
  KEY `idx_task_time` (`task_time`)
) ENGINE=InnoDB AUTO_INCREMENT=110757 DEFAULT CHARSET=utf8 COMMENT='业绩明细表';

-- ----------------------------
-- Table structure for crm_tactics
-- ----------------------------
DROP TABLE IF EXISTS `crm_tactics`;
CREATE TABLE `crm_tactics` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idcard` varchar(50) DEFAULT NULL COMMENT '投资人身份证号',
  `referrer_idcard` varchar(50) DEFAULT '0000000' COMMENT '推荐人身份证号',
  `addtime` int(11) DEFAULT '0' COMMENT '投资时间',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `name` varchar(100) DEFAULT '0' COMMENT '策略名称',
  `term` varchar(50) DEFAULT '0' COMMENT '策略期限',
  `tactics_id` varchar(100) DEFAULT '0' COMMENT '策略id',
  `borrow_nid` varchar(400) DEFAULT '0' COMMENT '子订单id',
  `user_real_name` varchar(50) DEFAULT NULL COMMENT '客戶姓名',
  `user_name` varchar(50) DEFAULT NULL COMMENT '客戶用户名',
  `user_id` int(11) DEFAULT NULL COMMENT '客戶id',
  `reg_time` int(11) DEFAULT NULL COMMENT '客戶注册时间',
  `open_time` int(11) DEFAULT NULL,
  `referrer_real_name` varchar(50) DEFAULT NULL,
  `referrer_user_name` varchar(50) DEFAULT NULL,
  `referrer_id` int(11) DEFAULT NULL,
  `now_referrer_idcard` varchar(50) DEFAULT NULL,
  `now_referrer_real_name` varchar(50) DEFAULT NULL,
  `now_referrer_user_name` varchar(50) DEFAULT NULL,
  `now_referrer_user_id` int(11) DEFAULT NULL,
  `group_account` decimal(11,2) DEFAULT NULL,
  `group_addtime` int(11) DEFAULT NULL,
  `create_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int(11) unsigned DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_account_recharge
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_recharge`;
CREATE TABLE `ht_account_recharge` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nid` varchar(50) DEFAULT NULL COMMENT '订单号',
  `user_id` varchar(11) DEFAULT '0' COMMENT '用户ID',
  `username` varchar(30) DEFAULT NULL,
  `account_id` varchar(50) DEFAULT NULL COMMENT '电子账号',
  `tx_date` int(10) DEFAULT NULL COMMENT '交易日期',
  `tx_time` int(10) DEFAULT NULL COMMENT '交易时间',
  `seq_no` int(50) DEFAULT NULL COMMENT '交易流水号',
  `bank_seq_no` varchar(50) DEFAULT NULL COMMENT '交易日期+交易时间+交易流水号',
  `is_bank` tinyint(1) unsigned DEFAULT '0' COMMENT '资金托管平台(0:汇付,1:江西银行)',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '充值状态:0:初始,1:充值中,2:充值成功,3:充值失败,4:终止.',
  `money` decimal(21,2) DEFAULT '0.00' COMMENT '金额',
  `fee` decimal(11,2) NOT NULL COMMENT '费用',
  `balance` decimal(16,2) DEFAULT NULL COMMENT '实际到账余额',
  `payment` varchar(100) DEFAULT NULL COMMENT '所属银行',
  `gate_type` varchar(10) DEFAULT NULL COMMENT '网关类型：QP快捷支付;B2C网关充值;B2B企业充值 ',
  `type` tinyint(1) DEFAULT '0' COMMENT '类型.1网上充值.0线下充值',
  `remark` varchar(250) DEFAULT '0' COMMENT '备注',
  `operator` varchar(20) DEFAULT NULL,
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `client` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0pc 1WX 2AND 3IOS 4other',
  `cardid` varchar(100) DEFAULT NULL COMMENT '银行卡号，导出数据关联时用到',
  `message` varchar(256) DEFAULT '' COMMENT '消息记录',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `nid` (`nid`),
  KEY `idx_bank_seq_no` (`bank_seq_no`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2495 DEFAULT CHARSET=utf8 COMMENT='充值记录';

-- ----------------------------
-- Table structure for ht_account_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_withdraw`;
CREATE TABLE `ht_account_withdraw` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(11) DEFAULT '0' COMMENT '用户ID',
  `nid` varchar(50) DEFAULT NULL COMMENT '交易凭证号',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '提现状态:0:初始,1:提现中,2:提现成功,3:提现失败，4：银行失败',
  `account` varchar(100) DEFAULT '0' COMMENT '账号',
  `bank` varchar(302) DEFAULT NULL COMMENT '所属银行',
  `bank_id` int(30) DEFAULT NULL,
  `total` decimal(11,2) DEFAULT '0.00' COMMENT '总额',
  `credited` decimal(11,2) DEFAULT '0.00' COMMENT '到账总额',
  `fee` varchar(20) DEFAULT '0' COMMENT '手续费',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `remark` varchar(100) DEFAULT NULL,
  `client` tinyint(1) unsigned DEFAULT '0' COMMENT '0pc 1WX 2AND 3IOS 4other',
  `reason` varchar(500) DEFAULT NULL COMMENT '提现原因',
  `bank_flag` tinyint(1) DEFAULT '0' COMMENT '银行存管提现标志位 1为银行存管 0非银行存管',
  `account_id` varchar(30) DEFAULT NULL COMMENT '银行电子账号',
  `tx_date` int(10) DEFAULT NULL COMMENT '交易日期',
  `tx_time` int(10) DEFAULT NULL COMMENT '交易时间',
  `seq_no` int(10) DEFAULT NULL COMMENT '交易流水号',
  `bank_seq_no` varchar(50) DEFAULT NULL COMMENT '交易日期+交易时间+交易流水号',
  `withdraw_type` tinyint(1) unsigned DEFAULT '0' COMMENT '提现类型 0主动提现  1代提现',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `nid` (`nid`),
  KEY `idx_bank_seq_no` (`bank_seq_no`),
  KEY `idx_addtime` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1521 DEFAULT CHARSET=utf8 COMMENT='用户提现';

-- ----------------------------
-- Table structure for ht_borrow
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow`;
CREATE TABLE `ht_borrow` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款的识别名',
  `project_type` tinyint(1) NOT NULL COMMENT '0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标',
  `user_id` varchar(11) DEFAULT '0' COMMENT '用户ID',
  `borrow_user_name` varchar(100) NOT NULL COMMENT '借款用户名称',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态 0备案中,1初审中,2投资中,3复审中(满标),4还款中,5已还款,6流标,7受托',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '借贷总金额',
  `borrow_valid_time` int(20) DEFAULT NULL COMMENT '借款有效时间',
  `borrow_style` varchar(100) DEFAULT '0' COMMENT '还款方式',
  `borrow_period` int(10) DEFAULT '0' COMMENT '借款期限',
  `borrow_apr` decimal(10,2) DEFAULT '0.00' COMMENT '借款利率',
  `label_id` int(10) NOT NULL DEFAULT '0' COMMENT '标签ID',
  `is_show` tinyint(1) DEFAULT '0' COMMENT '是否展示(隐藏测试标用:0:显示,1:不显示)',
  `is_engine_used` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否调用标的分配规则引擎 0:否,1:是',
  `is_installment` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是分期标 0:否,1:是',
  `is_month` tinyint(1) NOT NULL COMMENT '天标/月标 0:天标,1:月标',
  `regist_status` tinyint(1) DEFAULT '0' COMMENT '银行备案状态',
  `regist_time` datetime(3) DEFAULT NULL COMMENT '备案时间',
  `regist_user_id` int(11) DEFAULT NULL COMMENT '备案用户userId',
  `regist_user_name` varchar(30) DEFAULT NULL COMMENT '备案用户名',
  `verify_status` tinyint(1) DEFAULT '0' COMMENT '0未交保证金 1已交保证金 2暂不发布 3定时发标 4立即发标',
  `verify_over_time` int(11) DEFAULT '0' COMMENT '初审通过时间',
  `verify_time` int(11) DEFAULT '0' COMMENT '初审时间（发标时间）',
  `verify_userid` varchar(11) DEFAULT '0' COMMENT '审核人',
  `verify_user_name` varchar(30) DEFAULT NULL COMMENT '初审用户名（admin后台用户）',
  `verify_remark` varchar(255) DEFAULT '',
  `verify_contents` varchar(250) NOT NULL COMMENT '审核备注',
  `borrow_status` tinyint(2) unsigned DEFAULT '0' COMMENT '是否可以进行借款',
  `ontime` int(11) DEFAULT '0' COMMENT '定时发标',
  `borrow_end_time` varchar(100) NOT NULL COMMENT '借款到期时间',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划nid',
  `borrow_full_status` tinyint(2) unsigned DEFAULT '0' COMMENT '满标审核状态',
  `borrow_full_time` int(11) DEFAULT NULL COMMENT '满标时间',
  `tender_times` int(11) DEFAULT '0' COMMENT '投标的次数',
  `borrow_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已借到的金额',
  `borrow_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00',
  `borrow_account_scale` decimal(11,2) DEFAULT '0.00' COMMENT '借贷的完成率',
  `borrow_service` varchar(100) DEFAULT NULL COMMENT '账户管理费',
  `reverify_status` tinyint(1) DEFAULT '0' COMMENT '0初始 1放款请求中 2放款请求成功 3放款校验成功 4放款校验失败 5放款失败 6放款成功 7放款请求失败 8放款部分成功',
  `reverify_time` int(11) DEFAULT '0' COMMENT '复审时间',
  `reverify_userid` varchar(11) DEFAULT '0' COMMENT '审核人',
  `reverify_user_name` varchar(30) DEFAULT NULL COMMENT '复审用户名（admin后台用户）',
  `reverify_remark` varchar(255) DEFAULT '',
  `reverify_contents` varchar(250) NOT NULL COMMENT '审核复审标注',
  `recover_last_time` int(11) DEFAULT NULL COMMENT '最后一笔的放款完成时间',
  `repay_last_time` int(11) DEFAULT NULL COMMENT '最后还款时间',
  `repay_next_time` int(11) DEFAULT '0' COMMENT '下一笔还款时间',
  `repay_status` tinyint(1) DEFAULT '0' COMMENT '0初始 1还款请求中 2还款请求成功 3还款校验成功 4还款校验失败 5还款失败 6还款成功 7还款请求失败 8还款部分成功',
  `repay_full_status` tinyint(2) unsigned DEFAULT '0' COMMENT '是否已经还完',
  `repay_fee_normal` decimal(11,2) NOT NULL COMMENT '正常还款费用',
  `repay_account_all` decimal(11,2) DEFAULT '0.00' COMMENT '应还款总额',
  `repay_account_interest` decimal(11,2) DEFAULT '0.00' COMMENT '总还款利息',
  `repay_account_capital` decimal(11,2) DEFAULT '0.00' COMMENT '总还款本金',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款总额',
  `repay_account_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款利息',
  `repay_account_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款本金',
  `repay_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款总额',
  `repay_account_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款利息',
  `repay_account_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款本金',
  `borrow_manager` varchar(100) DEFAULT NULL COMMENT '融资服务费',
  `service_fee_rate` varchar(13) NOT NULL DEFAULT '0.0002' COMMENT '服务费费率',
  `manage_fee_rate` varchar(13) DEFAULT '0.0001' COMMENT '管理费费率',
  `differential_rate` varchar(13) DEFAULT '0.0000' COMMENT '收益差率',
  `late_interest_rate` varchar(13) DEFAULT NULL COMMENT '逾期利率(汇计划用)',
  `late_free_days` tinyint(2) unsigned DEFAULT '0' COMMENT '逾期免息天数(汇计划用)',
  `create_user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '创建用户-添加标的人员（账户操作人）',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `increase_interest_flag` tinyint(1) DEFAULT '0' COMMENT '产品加息标志位(0:不加息,1:加息)',
  `repay_capital_type` tinyint(1) DEFAULT '0' COMMENT '等额本息保证金的回滚方式 0：到期回滚 1：分期回滚 2：不回滚',
  `invest_level` varchar(10) DEFAULT '稳健型' COMMENT '标的投资等级',
  PRIMARY KEY (`id`),
  UNIQUE KEY `borrow_nid` (`borrow_nid`) USING BTREE,
  KEY `status` (`status`),
  KEY `borrow_apr` (`borrow_apr`)
) ENGINE=InnoDB AUTO_INCREMENT=1720 DEFAULT CHARSET=utf8 COMMENT='标的表(标的状态金额时间等)';

-- ----------------------------
-- Table structure for ht_borrow_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_info`;
CREATE TABLE `ht_borrow_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款的识别名',
  `borrow_pre_nid` varchar(12) NOT NULL DEFAULT '0',
  `borrow_pre_nid_new` varchar(12) DEFAULT NULL COMMENT '新的标的编号排序',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `user_id` varchar(11) DEFAULT '0' COMMENT '用户ID',
  `borrow_user_name` varchar(100) DEFAULT NULL COMMENT '借款用户名称',
  `applicant` varchar(20) DEFAULT NULL COMMENT '项目申请人',
  `repay_org_name` varchar(30) DEFAULT NULL COMMENT '担保机构用户名',
  `is_repay_org_flag` tinyint(1) DEFAULT '0' COMMENT '是否可用担保机构还款(0:否,1:是)',
  `repay_org_user_id` int(11) DEFAULT '0' COMMENT '担保机构用户ID',
  `type` varchar(50) NOT NULL DEFAULT '1' COMMENT '1房贷2车贷',
  `account_contents` longtext NOT NULL COMMENT '财务信息',
  `borrow_use` varchar(100) DEFAULT '0' COMMENT '用途',
  `borrow_contents` text COMMENT '借款的详情',
  `borrow_valid_time` int(20) DEFAULT '0' COMMENT '借款有效时间',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `asset_type` tinyint(2) DEFAULT NULL COMMENT '资产类型',
  `asset_type_name` varchar(20) DEFAULT NULL COMMENT '资产类型名称',
  `entrusted_flg` tinyint(1) NOT NULL DEFAULT '0' COMMENT '受托支付标志 0 否；1是',
  `entrusted_user_name` varchar(20) DEFAULT NULL COMMENT '受托支付用户名',
  `entrusted_user_id` int(11) DEFAULT NULL COMMENT '受托支付用户id',
  `trustee_pay_time` int(10) NOT NULL DEFAULT '0' COMMENT '受托支付申请时间',
  `tender_account_min` int(11) DEFAULT '0' COMMENT '最小的投资额',
  `tender_account_max` int(11) DEFAULT '0' COMMENT '最大的投资额',
  `upfiles_id` varchar(230) NOT NULL COMMENT ' 发标上传图片',
  `borrow_running_use` text COMMENT '资金运转-用途',
  `borrow_running_soruce` text COMMENT '资金运转-来源',
  `borrow_measures_instit` text COMMENT '风险控制措施-机构',
  `borrow_measures_mort` text COMMENT '风险控制措施-抵押物',
  `borrow_measures_mea` text COMMENT '险控制措施-措施',
  `files` text COMMENT '项目资料',
  `project_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标',
  `can_transaction_pc` varchar(3) DEFAULT NULL COMMENT '可投资平台_PC',
  `can_transaction_wei` varchar(3) DEFAULT NULL COMMENT '可投资平台_微网站',
  `can_transaction_ios` varchar(3) DEFAULT NULL COMMENT '可投资平台_IOS',
  `can_transaction_android` varchar(3) DEFAULT NULL COMMENT '可投资平台_Android',
  `operation_label` varchar(3) DEFAULT NULL COMMENT '运营标签',
  `borrow_company_instruction` text COMMENT '机构介绍',
  `borrow_operating_process` text COMMENT '操作流程',
  `company_or_personal` tinyint(1) unsigned DEFAULT NULL COMMENT '借款人信息 借款类型 1：公司 2：个人',
  `borrow_manager_scale_end` varchar(255) DEFAULT '' COMMENT '账户管理费率下线',
  `consume_id` varchar(20) DEFAULT NULL COMMENT '资产包编号',
  `disposal_price_estimate` varchar(50) DEFAULT '' COMMENT '售价预估    ',
  `disposal_period` varchar(50) DEFAULT '' COMMENT '处置周期    ',
  `disposal_channel` varchar(50) DEFAULT '' COMMENT '处置渠道    ',
  `disposal_result` varchar(1000) DEFAULT '' COMMENT '处置结果预案',
  `disposal_note` varchar(1000) DEFAULT '' COMMENT '备注说明',
  `disposal_project_name` varchar(100) DEFAULT '' COMMENT '项目名称    ',
  `disposal_project_type` varchar(100) DEFAULT '' COMMENT '项目类型    ',
  `disposal_area` varchar(200) DEFAULT '' COMMENT '所在地区    ',
  `disposal_predictive_value` varchar(20) DEFAULT '' COMMENT '预估价值    ',
  `disposal_ownership_category` varchar(20) DEFAULT '' COMMENT '权属类别    ',
  `disposal_asset_origin` varchar(20) DEFAULT '' COMMENT '资产成因    ',
  `disposal_attachment_info` varchar(1000) DEFAULT '' COMMENT '附件信息',
  `borrow_increase_money` decimal(10,0) DEFAULT NULL COMMENT '递增金额',
  `borrow_interest_coupon` tinyint(2) DEFAULT NULL COMMENT '优惠券',
  `borrow_taste_money` tinyint(2) DEFAULT NULL COMMENT '体验金',
  `borrow_asset_number` varchar(50) DEFAULT NULL COMMENT '资产编号（风险缓释金）',
  `borrow_project_source` varchar(10) DEFAULT NULL COMMENT '项目来源（项目详情中）',
  `borrow_interest_time` varchar(50) DEFAULT NULL COMMENT '起息时间（项目详情中）',
  `borrow_due_time` varchar(50) DEFAULT NULL COMMENT '到期时间（项目详情中）',
  `borrow_safeguard_way` varchar(500) DEFAULT NULL COMMENT '保障方式（项目详情中）',
  `borrow_income_description` varchar(500) DEFAULT NULL COMMENT '收益说明（项目详情中）',
  `borrow_publisher` varchar(30) DEFAULT NULL COMMENT '发行人（风险缓释金）',
  `borrow_extra_yield` decimal(10,2) DEFAULT '0.00' COMMENT '产品加息收益率（风险缓释金）',
  `increase_interest_flag` tinyint(1) DEFAULT '0' COMMENT '是否加息标志位(0:否,1:是)',
  `contract_period` int(11) DEFAULT NULL COMMENT '融通宝协议期数',
  `borrow_level` varchar(10) DEFAULT NULL COMMENT '借款人评级',
  `asset_attributes` tinyint(1) DEFAULT '0' COMMENT '资产属性 1:抵押标 2:质押标 3:信用标 4:债权转让标 5:净值标',
  `bank_raise_start_date` varchar(10) DEFAULT NULL COMMENT '银行募集开始时间',
  `bank_raise_end_date` varchar(10) DEFAULT NULL COMMENT '银行募集结束时间',
  `bank_regist_days` tinyint(2) unsigned DEFAULT '0' COMMENT '银行备案天数',
  `bank_borrow_days` int(4) DEFAULT '0' COMMENT '银行项目天数',
  `project_name` varchar(15) DEFAULT NULL COMMENT '项目标题',
  `finance_purpose` varchar(30) DEFAULT NULL COMMENT '融资用途',
  `monthly_income` varchar(30) DEFAULT NULL COMMENT '月薪收入',
  `payment` varchar(30) DEFAULT NULL COMMENT '还款来源',
  `first_payment` varchar(30) DEFAULT NULL COMMENT '第一还款来源',
  `second_payment` varchar(30) DEFAULT NULL COMMENT '第二还款来源',
  `cost_introdution` varchar(30) DEFAULT NULL COMMENT '费用说明',
  `fiance_condition` varchar(500) DEFAULT NULL COMMENT '财务状况',
  `is_new` tinyint(1) DEFAULT '0' COMMENT '是否为新标 0 为老标 1为新标 （网站改版后都为新标）',
  `publish_inst_code` varchar(20) NOT NULL DEFAULT '0' COMMENT '定向标: 0-全部  其他值-资金机构的编号',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '100位字符限制-备注',
  `create_user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '创建用户-添加标的人员（账户操作人）',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `borrow_nid` (`borrow_nid`) USING BTREE,
  KEY `user_id` (`user_id`),
  KEY `name` (`name`),
  KEY `project_type` (`project_type`),
  KEY `repayorgname_index` (`repay_org_name`),
  KEY `idx_borrow_pre_nid` (`borrow_pre_nid`),
  KEY `idx_inst_code` (`inst_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1719 DEFAULT CHARSET=utf8 COMMENT='标的信息表';

-- ----------------------------
-- Table structure for ht_borrow_recover
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_recover`;
CREATE TABLE `ht_borrow_recover` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态(初始:0,放款成功后更新成:1)',
  `user_id` varchar(11) DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '投资人用户名',
  `borrow_nid` varchar(50) DEFAULT '' COMMENT '标的编号',
  `nid` varchar(50) NOT NULL COMMENT '投资订单号(对应borrow_tender表的nid)',
  `auth_code` varchar(30) DEFAULT NULL COMMENT '投资人投标成功的授权号',
  `borrow_userid` int(11) DEFAULT '0' COMMENT '借款人用户ID',
  `borrow_user_name` varchar(30) NOT NULL COMMENT '借款人用户名',
  `tender_id` int(11) DEFAULT '0' COMMENT '投资ID(对应borrow_tender表里的ID字段)',
  `accede_order_id` varchar(50) DEFAULT NULL COMMENT '汇计划加入订单号',
  `recover_status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '还款状态(0:未还款,1:已还款)',
  `recover_period` tinyint(2) unsigned DEFAULT NULL COMMENT '还款期数',
  `recover_time` int(11) DEFAULT NULL COMMENT '估计还款时间(分期标的是下一期应还时间)',
  `recover_yestime` int(11) DEFAULT NULL COMMENT '已经还款时间',
  `recover_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还总额',
  `recover_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还利息',
  `recover_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还本金',
  `recover_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还总额',
  `recover_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `recover_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `recover_account_wait` decimal(11,2) NOT NULL COMMENT '未收总额',
  `recover_capital_wait` decimal(11,2) NOT NULL COMMENT '未收本金',
  `recover_interest_wait` decimal(11,2) NOT NULL COMMENT '未收本息',
  `recover_type` varchar(50) NOT NULL COMMENT '还款流程(yes,late,wait)',
  `recover_service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '放款服务费',
  `recover_fee_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收管理费',
  `repay_charge_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还款提前还款利息',
  `repay_delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '已还款延期还款利息',
  `credit_manage_fee` decimal(10,2) DEFAULT '0.00' COMMENT '已收债转管理费',
  `repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还款逾期利息',
  `debt_status` tinyint(1) DEFAULT '0' COMMENT '债权是否结束状态(0:否,1:是)',
  `credit_status` tinyint(1) DEFAULT '0' COMMENT '债转状态 0初始 1承接停止 2完全承接',
  `recover_fee` decimal(11,2) NOT NULL COMMENT '账户管理费',
  `recover_late_fee` decimal(11,2) NOT NULL COMMENT '逾期费用收入',
  `advance_status` int(2) NOT NULL COMMENT '0:正常,1:提前,2:延期,3:逾期',
  `charge_days` int(11) DEFAULT '0' COMMENT '提前天数',
  `charge_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前减息',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `delay_rate` decimal(10,8) DEFAULT '0.00000000' COMMENT '延期费率',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `sendmail` tinyint(1) DEFAULT '0' COMMENT '0发送邮件，1已发送',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `credit_amount` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已转让本金',
  `credit_interest_amount` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已债转总利息（含垫付）',
  `credit_time` int(11) NOT NULL DEFAULT '0' COMMENT '债转时间',
  `repay_ordid` varchar(20) DEFAULT NULL COMMENT '还款订单号',
  `repay_orddate` varchar(10) DEFAULT NULL COMMENT '还款时间',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名(还款时更新)',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户id(还款时更新)',
  `invite_region_id` int(11) NOT NULL DEFAULT '0' COMMENT '一级部门id(还款时更新)',
  `invite_region_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '一级部门名称(还款时更新)',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0' COMMENT '二级部门id(还款时更新)',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '二级部门名称(还款时更新)',
  `invite_department_id` int(11) NOT NULL DEFAULT '0' COMMENT '三级部门id(还款时更新)',
  `invite_department_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '三级部门名称(还款时更新)',
  `tender_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '投资人用户属性',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户属性',
  `loan_batch_no` varchar(10) DEFAULT NULL COMMENT '放款批次号',
  `repay_batch_no` varchar(10) DEFAULT NULL COMMENT '还款批次号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `nid` (`nid`),
  KEY `idx_userid_recover_yestime` (`user_id`,`recover_yestime`),
  KEY `idx_recover_time` (`recover_time`),
  KEY `idx_tender_id` (`tender_id`),
  KEY `idx_addtime` (`create_time`),
  KEY `idx_accede_order_id` (`accede_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2465 DEFAULT CHARSET=utf8 COMMENT='标的放款记录（投资人） 总表';

-- ----------------------------
-- Table structure for ht_borrow_recover_plan
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_recover_plan`;
CREATE TABLE `ht_borrow_recover_plan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态(初始:0,放款成功后更新成:1)',
  `user_id` varchar(11) DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '投资人用户名',
  `borrow_nid` varchar(50) DEFAULT '' COMMENT '标的编号',
  `nid` varchar(50) NOT NULL COMMENT '投资订单号(对应borrow_tender表的nid)',
  `borrow_userid` int(11) DEFAULT '0' COMMENT '借款人用户ID',
  `borrow_user_name` varchar(30) NOT NULL COMMENT '借款人用户名',
  `tender_id` int(11) DEFAULT '0' COMMENT '投资ID(对应borrow_tender表里的ID字段)',
  `accede_order_id` varchar(50) DEFAULT NULL COMMENT '汇计划加入订单号',
  `recover_status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '还款状态(0:未还款,1:已还款)',
  `recover_period` tinyint(2) unsigned DEFAULT NULL COMMENT '还款期数',
  `recover_time` int(11) DEFAULT NULL COMMENT '估计还款时间(分期标的是下一期应还时间)',
  `recover_yestime` varchar(50) DEFAULT NULL COMMENT '已经还款时间(当期实际还款时间)',
  `recover_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还总额',
  `recover_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还利息',
  `recover_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还本金',
  `recover_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还总额',
  `recover_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `recover_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `recover_account_wait` decimal(11,2) NOT NULL COMMENT '未收总额',
  `recover_capital_wait` decimal(11,2) NOT NULL COMMENT '未收本金',
  `recover_interest_wait` decimal(11,2) NOT NULL COMMENT '未收利息',
  `recover_type` varchar(50) NOT NULL COMMENT '还款流程(yes,late,wait)',
  `recover_fee` decimal(11,2) NOT NULL COMMENT '账户管理费',
  `recover_late_fee` decimal(11,2) NOT NULL COMMENT '逾期费用收入',
  `advance_status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '0:正常,1:提前,2:延期,3:逾期',
  `charge_days` int(11) DEFAULT '0' COMMENT '提前天数',
  `charge_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '提前减息',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `delay_rate` decimal(10,8) DEFAULT '0.00000000' COMMENT '延期利率',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `sendmail` tinyint(1) DEFAULT '0' COMMENT '0发送邮件，1已发送',
  `repay_order_id` varchar(50) DEFAULT NULL COMMENT '还款订单号',
  `repay_order_date` varchar(20) DEFAULT NULL COMMENT '还款订单日期',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名(还款时更新)',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户id(还款时更新)',
  `invite_region_id` int(11) NOT NULL DEFAULT '0' COMMENT '一级部门id(还款时更新)',
  `invite_region_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '一级部门名称(还款时更新)',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0' COMMENT '二级部门id(还款时更新)',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '二级部门名称(还款时更新)',
  `invite_department_id` int(11) NOT NULL DEFAULT '0' COMMENT '三级部门id(还款时更新)',
  `invite_department_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '三级部门名称(还款时更新)',
  `tender_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '投资人用户属性',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户属性',
  `auth_code` varchar(30) DEFAULT NULL COMMENT '投资人投标成功的授权号',
  `loan_batch_no` varchar(10) DEFAULT NULL COMMENT '放款批次号',
  `repay_batch_no` varchar(10) DEFAULT NULL COMMENT '还款批次号',
  `recover_fee_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收管理费',
  `credit_status` tinyint(1) DEFAULT '0' COMMENT '债转状态 0初始 1承接停止 2完全承接',
  `repay_charge_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还款提前还款利息',
  `repay_delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '已还款延期还款利息',
  `repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还款逾期利息',
  `credit_amount` decimal(11,2) unsigned DEFAULT '0.00' COMMENT '已承接债转本金',
  `credit_interest_amount` decimal(11,2) DEFAULT '0.00' COMMENT '已承接垫付利息',
  `credit_manage_fee` decimal(10,2) DEFAULT '0.00' COMMENT '已收债转管理费',
  `debt_status` tinyint(1) DEFAULT '0' COMMENT '债权是否结束状态(0:否,1:是)',
  `credit_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已承接利息',
  PRIMARY KEY (`id`),
  KEY `period` (`borrow_nid`,`recover_status`,`recover_period`),
  KEY `idx_nid` (`nid`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_recover_time` (`recover_time`),
  KEY `idx_accede_order_id` (`accede_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6063 DEFAULT CHARSET=utf8 COMMENT='标的放款记录分期（投资人）';

-- ----------------------------
-- Table structure for ht_credit_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_credit_repay`;
CREATE TABLE `ht_credit_repay` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(11) DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '承接人用户名',
  `credit_user_id` int(11) DEFAULT NULL COMMENT '出让人id',
  `credit_user_name` varchar(30) NOT NULL COMMENT '出让人用户名',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态',
  `bid_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `credit_nid` varchar(30) NOT NULL DEFAULT '0' COMMENT '债转标号',
  `credit_tender_nid` varchar(30) NOT NULL COMMENT '债转投标单号',
  `assign_nid` varchar(30) NOT NULL COMMENT '认购单号',
  `auth_code` varchar(30) DEFAULT NULL COMMENT '投资人投标成功的授权号',
  `assign_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `assign_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还总额',
  `assign_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `assign_interest_advance` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '垫付利息',
  `assign_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '购买价格',
  `assign_pay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `assign_repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `assign_repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `assign_repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '还款期数',
  `assign_create_date` int(11) NOT NULL DEFAULT '0' COMMENT '认购日期',
  `client` int(11) DEFAULT NULL COMMENT '客户端',
  `recover_period` int(11) NOT NULL DEFAULT '1' COMMENT '原标还款期数',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '管理费',
  `credit_repay_order_id` varchar(50) DEFAULT NULL COMMENT '债转还款订单号',
  `credit_repay_order_date` varchar(20) DEFAULT NULL COMMENT '债转还款订单日期',
  `advance_status` tinyint(2) unsigned DEFAULT '0' COMMENT '债转提前还款状态 0正常还款 1提前还款 2延期还款 3逾期还款',
  `charge_days` int(11) DEFAULT '0' COMMENT '提前天数',
  `charge_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款利息（负数）',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `late_days` int(11) DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '逾期利息',
  `debt_status` tinyint(1) DEFAULT '0' COMMENT '债权是否结束状态(0:否,1:是)',
  `add_ip` varchar(15) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `credit_nid` (`credit_nid`),
  KEY `idx_bid_nid` (`bid_nid`),
  KEY `idx_assign_nid` (`assign_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=270 DEFAULT CHARSET=utf8 COMMENT='债转还款表';

-- ----------------------------
-- Table structure for ht_hjh_plan
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_plan`;
CREATE TABLE `ht_hjh_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计划id',
  `plan_nid` varchar(20) NOT NULL COMMENT '计划编号',
  `plan_name` varchar(50) NOT NULL COMMENT '计划名称',
  `lock_period` tinyint(2) NOT NULL DEFAULT '0' COMMENT '锁定期(天)',
  `is_month` tinyint(2) NOT NULL DEFAULT '0' COMMENT '默认0 天标，1 月标',
  `expect_apr` decimal(10,2) NOT NULL COMMENT '预期年化利率',
  `min_investment` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最低加入金额',
  `max_investment` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '最高加入金额',
  `investment_increment` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '投资增量',
  `available_invest_account` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '计划可投金额，之前计划有总金额，现在只有剩余能投金额',
  `repay_wait_all` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还总额',
  `plan_invest_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '投资状态 0 全部；1 启用；2 关闭',
  `plan_display_status` tinyint(2) DEFAULT '1' COMMENT '显示状态字段 1显示 2 隐藏',
  `add_time` int(10) DEFAULT NULL COMMENT '添加时间',
  `borrow_style` varchar(100) DEFAULT '0' COMMENT '还款方式',
  `coupon_config` varchar(10) DEFAULT '0' COMMENT '是否可用券：0 不可用 1 体验金 2 加息券 3 代金券',
  `plan_concept` longtext NOT NULL COMMENT '计划介绍',
  `plan_principle` longtext NOT NULL COMMENT '计划原理',
  `safeguard_measures` longtext NOT NULL COMMENT '风控保障措施',
  `margin_measures` longtext NOT NULL COMMENT '风险保证金措施',
  `normal_questions` longtext NOT NULL COMMENT '常见问题',
  `join_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '累积加入总额',
  `plan_wait_captical` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `plan_wait_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `plan_repay_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `plan_repay_capital` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `min_invest_counts` tinyint(2) NOT NULL DEFAULT '1' COMMENT '最小自动投资笔数',
  `invest_level` varchar(10) DEFAULT '稳健型' COMMENT '服务投资等级',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user` int(11) NOT NULL COMMENT '创建人id',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `plan_nid` (`plan_nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='汇计划列表';

-- ----------------------------
-- Table structure for ht_hjh_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_repay`;
CREATE TABLE `ht_hjh_repay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `accede_order_id` varchar(50) NOT NULL COMMENT '汇计划加入订单号',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划nid',
  `lock_period` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '锁定期(天)',
  `user_id` varchar(11) DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '用户属性',
  `accede_account` decimal(11,2) NOT NULL COMMENT '加入金额',
  `repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `repay_status` tinyint(1) DEFAULT NULL COMMENT '回款状态 0 未回款，1 部分回款 2 已回款',
  `repay_already` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已回款',
  `repay_wait` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待回款',
  `repay_should` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '应还金额',
  `repay_actual` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实还金额',
  `order_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态 0自动投标中 1锁定中 2退出中 3已退出',
  `repay_actual_time` int(10) DEFAULT NULL COMMENT '计划实际还款时间',
  `repay_should_time` int(10) DEFAULT NULL COMMENT '计划应还时间',
  `plan_repay_capital` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `plan_repay_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `repay_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `plan_wait_captical` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `plan_wait_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `wait_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还总额',
  `service_fee` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `actual_revenue` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '实际收益(元)',
  `actual_pay_total` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '实际回款总额(元)',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user` int(11) NOT NULL COMMENT '创建人id',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `accede_order_id` (`accede_order_id`),
  KEY `idx_plan_nid` (`plan_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=712 DEFAULT CHARSET=utf8 COMMENT='计划还款表';

-- ----------------------------
-- Table structure for oa_allhydid
-- ----------------------------
DROP TABLE IF EXISTS `oa_allhydid`;
CREATE TABLE `oa_allhydid` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `time_month` int(8) NOT NULL COMMENT '所属月',
  `time_day` int(8) NOT NULL COMMENT '日期',
  `allids` text NOT NULL COMMENT '经过序列化的所有id数组',
  `addtime` int(11) DEFAULT NULL COMMENT '添加时间,',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=280 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='此表存放的为经过序列化的过滤掉为入职和已经离职的员工的HYD的ID. \r\n根据月份和日期来标识';

-- ----------------------------
-- Table structure for oa_asset
-- ----------------------------
DROP TABLE IF EXISTS `oa_asset`;
CREATE TABLE `oa_asset` (
  `aid` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) NOT NULL,
  `filename` varchar(50) DEFAULT NULL,
  `filesize` int(11) DEFAULT NULL,
  `filepath` varchar(200) NOT NULL,
  `uploadtime` int(11) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '1',
  `meta` text,
  `suffix` varchar(50) DEFAULT NULL,
  `download_times` int(6) NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_auth_access
-- ----------------------------
DROP TABLE IF EXISTS `oa_auth_access`;
CREATE TABLE `oa_auth_access` (
  `role_id` mediumint(8) unsigned NOT NULL COMMENT '角色',
  `rule_name` varchar(255) NOT NULL COMMENT '规则唯一英文标识,全小写',
  `type` varchar(30) DEFAULT NULL COMMENT '权限规则分类，请加应用前缀,如admin_',
  KEY `role_id` (`role_id`),
  KEY `rule_name` (`rule_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_auth_rule
-- ----------------------------
DROP TABLE IF EXISTS `oa_auth_rule`;
CREATE TABLE `oa_auth_rule` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '规则id,自增主键',
  `module` varchar(20) NOT NULL COMMENT '规则所属module',
  `type` varchar(30) NOT NULL DEFAULT '1' COMMENT '权限规则分类，请加应用前缀,如admin_',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '规则唯一英文标识,全小写',
  `param` varchar(255) DEFAULT NULL COMMENT '额外url参数',
  `title` varchar(20) NOT NULL DEFAULT '' COMMENT '规则中文描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效(0:无效,1:有效)',
  `condition` varchar(300) NOT NULL DEFAULT '' COMMENT '规则附加条件',
  PRIMARY KEY (`id`),
  KEY `module` (`module`,`status`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=277 DEFAULT CHARSET=utf8 COMMENT='权限规则表';

-- ----------------------------
-- Table structure for oa_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `oa_auth_user`;
CREATE TABLE `oa_auth_user` (
  `user_id` int(8) NOT NULL COMMENT 'User_id',
  `department_id` int(8) NOT NULL COMMENT '部门',
  `department_level` tinyint(1) NOT NULL DEFAULT '3' COMMENT '部门级别1,2,3',
  `is_list` tinyint(1) NOT NULL DEFAULT '0' COMMENT '查看员工',
  `is_add` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有添加权限,0:否,1是',
  `is_entry1` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有入职审核权限:0:否,1：是',
  `is_entry2` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有二级入职审核权限:0:否,1：是',
  `is_quit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '申请离职',
  `is_quit1` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有一级离职审核权限:0:否,1：是',
  `is_quit2` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有二级离职审核权限:0:否,1：是',
  `is_query_data` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '数据查询权限:1可查;0 不可查',
  UNIQUE KEY `user_id` (`user_id`,`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工档案权限';

-- ----------------------------
-- Table structure for oa_common_action_log
-- ----------------------------
DROP TABLE IF EXISTS `oa_common_action_log`;
CREATE TABLE `oa_common_action_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` bigint(20) DEFAULT '0' COMMENT '用户id',
  `object` varchar(100) DEFAULT NULL COMMENT '访问对象的id,格式：不带前缀的表名+id;如posts1表示xx_posts表里id为1的记录',
  `action` varchar(50) DEFAULT NULL COMMENT '操作名称；格式规定为：应用名+控制器+操作名；也可自己定义格式只要不发生冲突且惟一；',
  `count` int(11) DEFAULT '0' COMMENT '访问次数',
  `last_time` int(11) DEFAULT '0' COMMENT '最后访问的时间戳',
  `ip` varchar(15) DEFAULT NULL COMMENT '访问者最后访问ip',
  PRIMARY KEY (`id`),
  KEY `user_object_action` (`user`,`object`,`action`),
  KEY `user_object_action_ip` (`user`,`object`,`action`,`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_department
-- ----------------------------
DROP TABLE IF EXISTS `oa_department`;
CREATE TABLE `oa_department` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parentid` int(10) NOT NULL COMMENT '父类部门id',
  `name` varchar(50) NOT NULL COMMENT '部门名',
  `description` varchar(200) NOT NULL COMMENT '部门描述',
  `ishead` tinyint(4) NOT NULL COMMENT '是否为总部(1,0)',
  `ishr` tinyint(4) NOT NULL COMMENT '是否人力资源部(1,0)',
  `isfinance` tinyint(4) NOT NULL COMMENT '是否财务部(1,0)',
  `cuttype` tinyint(4) NOT NULL COMMENT '提成发放方式（1线上2线下）',
  `provinceid` varchar(20) NOT NULL COMMENT '所在省份',
  `cityid` varchar(20) NOT NULL COMMENT '所在城市',
  `header` int(10) DEFAULT '0' COMMENT '负责人：员工ID',
  `manager` int(10) DEFAULT '0' COMMENT '督导：用户ID',
  `position_category` int(4) NOT NULL COMMENT '岗位类别',
  `listorder` tinyint(3) unsigned NOT NULL COMMENT '排序',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `flag` int(1) DEFAULT NULL COMMENT '删除标记 0：已删除  1：未删除',
  PRIMARY KEY (`id`),
  KEY `index_name` (`parentid`),
  KEY `idx_name` (`name`),
  KEY `idx_header` (`header`),
  KEY `idx_manager` (`manager`)
) ENGINE=InnoDB AUTO_INCREMENT=544 DEFAULT CHARSET=utf8 COMMENT='本表存放部门信息';

-- ----------------------------
-- Table structure for oa_emails
-- ----------------------------
DROP TABLE IF EXISTS `oa_emails`;
CREATE TABLE `oa_emails` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email_sender` varchar(30) NOT NULL COMMENT '发件人名称',
  `email_hostName` varchar(60) NOT NULL COMMENT 'SMTP服务器',
  `email_address` varchar(60) NOT NULL COMMENT '邮箱地址',
  `email_userName` varchar(30) NOT NULL COMMENT '邮箱账号',
  `email_status` int(2) NOT NULL DEFAULT '0' COMMENT '邮箱状态',
  `email_password` varchar(100) NOT NULL COMMENT '邮箱密码',
  `create_time` int(10) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `create_by` varchar(30) NOT NULL COMMENT '创建人ID',
  `create_user` varchar(30) NOT NULL COMMENT '创建人用户名',
  `update_time` int(10) DEFAULT '0' COMMENT '更新时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '更新人ID',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新人用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_htj_achievements
-- ----------------------------
DROP TABLE IF EXISTS `oa_htj_achievements`;
CREATE TABLE `oa_htj_achievements` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(7) NOT NULL COMMENT '用户的汇盈贷ID',
  `truename` varchar(20) NOT NULL COMMENT '用户真实姓名',
  `tasknum` int(6) NOT NULL COMMENT '//任务编号',
  `reg_time` int(10) NOT NULL COMMENT '注册时间',
  `reg_num` int(5) NOT NULL COMMENT '注册人数',
  `open_num` int(5) DEFAULT NULL COMMENT '开户人数',
  `this_num1` int(5) DEFAULT NULL COMMENT '时间范围内投资(0<投资<100)',
  `this_num2` int(5) DEFAULT NULL COMMENT '时间范围内投资(100≤投资<3000)',
  `this_num3` int(5) DEFAULT NULL COMMENT '时间范围内投资(3000≤投资)',
  `discounte_record` decimal(10,2) DEFAULT NULL,
  `department_id` int(10) DEFAULT NULL COMMENT '三级部门id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37185548 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_htj_investment
-- ----------------------------
DROP TABLE IF EXISTS `oa_htj_investment`;
CREATE TABLE `oa_htj_investment` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `time_month` int(6) unsigned NOT NULL COMMENT '所属年月',
  `time_day` int(8) unsigned NOT NULL COMMENT '详细日期',
  `user_id` mediumint(8) unsigned NOT NULL COMMENT '用户ID',
  `hyd_id` int(8) unsigned NOT NULL COMMENT '用户在crm的hyd id',
  `reg_time` int(10) unsigned NOT NULL COMMENT '注册时间',
  `sex` tinyint(1) unsigned NOT NULL COMMENT '性别',
  `age` tinyint(3) unsigned NOT NULL COMMENT '年龄',
  `invite_user_id` int(10) unsigned DEFAULT NULL COMMENT '投资时推荐人ID',
  `departmentid` int(5) unsigned NOT NULL COMMENT '所属部门ID',
  `success_time` int(10) unsigned NOT NULL COMMENT '销售日期(投资成功日期)',
  `customer` varchar(15) DEFAULT NULL COMMENT '客户姓名',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `borrow_nid` varchar(16) DEFAULT NULL COMMENT '标的编号',
  `borrow_name` varchar(50) DEFAULT NULL COMMENT '标的名称',
  `frozen_time` int(10) unsigned DEFAULT NULL COMMENT '投标冻结时间',
  `recover_time` int(10) unsigned DEFAULT NULL COMMENT '投标到期时间',
  `borrow_status` varchar(25) NOT NULL COMMENT '//标的状态',
  `unit` tinyint(2) unsigned DEFAULT NULL COMMENT '标期单位(天1，月2，年3)',
  `time_limit` tinyint(2) unsigned DEFAULT NULL COMMENT '期限',
  `borrow_money` decimal(11,2) NOT NULL COMMENT '投标金额',
  `borrow_type` tinyint(2) unsigned NOT NULL COMMENT '标的类型(0-4)',
  `borrow_apr` decimal(11,2) NOT NULL COMMENT '利率%',
  `nid` varchar(32) NOT NULL COMMENT '投资编号(唯一)',
  `mbdate` int(10) DEFAULT '0' COMMENT '满标放款日期',
  `department_id` int(10) DEFAULT NULL COMMENT '三级部门id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `hyd_id` (`hyd_id`),
  KEY `idx_success_time` (`success_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2930 DEFAULT CHARSET=utf8 COMMENT='汇盈贷投资明细表';

-- ----------------------------
-- Table structure for oa_hyd_achievements
-- ----------------------------
DROP TABLE IF EXISTS `oa_hyd_achievements`;
CREATE TABLE `oa_hyd_achievements` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(7) NOT NULL COMMENT '用户的汇盈贷ID',
  `truename` varchar(20) NOT NULL COMMENT '用户真实姓名',
  `tasknum` int(6) NOT NULL COMMENT '//任务编号',
  `reg_time` int(10) NOT NULL COMMENT '注册时间',
  `reg_num` int(5) NOT NULL COMMENT '注册人数',
  `open_num` int(5) DEFAULT NULL COMMENT '开户人数',
  `invest_num1` int(5) DEFAULT '0' COMMENT '第一次投资大于3000人数',
  `invest_num` int(5) DEFAULT '0' COMMENT '第一次投资大于3000的人数',
  `this_num1` int(5) DEFAULT NULL COMMENT '时间范围内投资(0<投资<100)',
  `this_num2` int(5) DEFAULT NULL COMMENT '时间范围内投资(100≤投资<3000)',
  `this_num3` int(5) DEFAULT NULL COMMENT '时间范围内投资(3000≤投资)',
  `discounte_record` decimal(11,2) DEFAULT NULL,
  `hjh_discounte_record` decimal(11,2) DEFAULT '0.00' COMMENT '汇计划折比绩效',
  `department_id` int(10) DEFAULT NULL COMMENT '三级部门id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38631666 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_hyd_investment
-- ----------------------------
DROP TABLE IF EXISTS `oa_hyd_investment`;
CREATE TABLE `oa_hyd_investment` (
  `id` int(15) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(15) NOT NULL COMMENT '员工ID',
  `hyd_id` int(10) NOT NULL COMMENT 'hyd_id(平台用户ID)',
  `invite_user_id` int(10) NOT NULL COMMENT '//客户投标时推荐人的ID',
  `departmentid` int(8) NOT NULL COMMENT '部门ID',
  `success_time` int(10) NOT NULL COMMENT '销售日期(投资成功日期)',
  `customer` varchar(16) NOT NULL COMMENT '客户姓名',
  `username` varchar(32) NOT NULL COMMENT '客户账号',
  `borrow_nid` varchar(16) NOT NULL COMMENT '标的编号',
  `borrow_name` varchar(50) NOT NULL COMMENT '标的名称',
  `frozen_time` int(10) NOT NULL COMMENT '投标冻结时间',
  `recover_time` int(10) NOT NULL COMMENT '投标到期时间',
  `unit` tinyint(2) NOT NULL COMMENT '标期单位(天1，月2，年3)',
  `time_limit` tinyint(2) NOT NULL COMMENT '期限',
  `borrow_money` decimal(11,2) NOT NULL COMMENT '投标金额',
  `borrow_type` tinyint(2) NOT NULL COMMENT '标的类型(0-4)',
  `borrow_apr` decimal(5,2) NOT NULL COMMENT '利率%',
  `nid` varchar(32) NOT NULL COMMENT '投资编号（唯一）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nid` (`nid`)
) ENGINE=InnoDB AUTO_INCREMENT=446446 DEFAULT CHARSET=utf8 COMMENT='汇盈贷投资明细表';

-- ----------------------------
-- Table structure for oa_hyd_report
-- ----------------------------
DROP TABLE IF EXISTS `oa_hyd_report`;
CREATE TABLE `oa_hyd_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `query_date` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `hyd_id` int(11) NOT NULL COMMENT '网站平台ID',
  `departmentid` int(11) NOT NULL COMMENT '部门ID',
  `reg_num` int(11) DEFAULT '0' COMMENT '新注册人数',
  `reg_recharge_num` int(11) DEFAULT '0' COMMENT '新注册人充值人数',
  `reg_recharge_amount` decimal(10,2) DEFAULT '0.00' COMMENT '新注册人充值金额',
  `reg_recharge_times` int(11) DEFAULT '0' COMMENT '新注册人充值次数',
  `reg_tender_num` int(11) DEFAULT '0' COMMENT '新注册人的投资人数',
  `reg_tender_amount` decimal(10,2) DEFAULT '0.00' COMMENT '新注册人的投资金额',
  `reg_tender_times` int(11) DEFAULT '0' COMMENT '新注册人的投资次数',
  `total_recharge_num` int(11) DEFAULT '0' COMMENT '总充值人数',
  `total_recharge_amount` decimal(10,2) DEFAULT '0.00' COMMENT '总充值金额',
  `total_recharge_times` int(11) DEFAULT '0' COMMENT '总充值次数',
  `total_withdraw_num` int(11) DEFAULT '0' COMMENT '总提现人数',
  `total_withdraw_amount` decimal(10,2) DEFAULT '0.00' COMMENT '总提现金额',
  `total_withdraw_times` int(11) DEFAULT '0' COMMENT '总提现次数',
  `total_tender_num` int(11) DEFAULT '0' COMMENT '总投资人数',
  `total_tender_amount` decimal(10,2) DEFAULT '0.00' COMMENT '总投资金额',
  `total_tender_times` int(11) DEFAULT '0' COMMENT '总投资次数',
  `first_recharge_num` int(11) DEFAULT '0' COMMENT '首次充值人数',
  `first_recharge_amount` decimal(10,2) DEFAULT '0.00',
  `first_recharge_times` int(11) DEFAULT '0',
  `first_tender_num` int(11) DEFAULT '0' COMMENT '首次投资人数',
  `total_recover_amount` decimal(10,2) DEFAULT '0.00' COMMENT '总还款金额',
  `total_recover_num` int(11) DEFAULT '0' COMMENT '总还款人数',
  `total_balance` decimal(11,2) DEFAULT '0.00' COMMENT '用户余额',
  PRIMARY KEY (`id`),
  KEY `idx_query_date` (`query_date`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_departmentid` (`departmentid`),
  KEY `idx_hyd_id` (`hyd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3892658 DEFAULT CHARSET=utf8 COMMENT='数据统计';

-- ----------------------------
-- Table structure for oa_logs
-- ----------------------------
DROP TABLE IF EXISTS `oa_logs`;
CREATE TABLE `oa_logs` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '添加者ID',
  `type` char(8) NOT NULL COMMENT '操作类型,用于区分操作的模块.',
  `operatorname` varchar(50) DEFAULT NULL COMMENT '操作者的姓名',
  `beoperatorname` varchar(50) DEFAULT NULL COMMENT '被操作者姓名',
  `method` varchar(20) DEFAULT NULL COMMENT '操作方法',
  `status` varchar(200) DEFAULT NULL COMMENT '是否成功',
  `ipaddress` int(10) unsigned DEFAULT '0' COMMENT 'IP地址',
  `times` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '当前时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=144276 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Table structure for oa_menu
-- ----------------------------
DROP TABLE IF EXISTS `oa_menu`;
CREATE TABLE `oa_menu` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `parentid` smallint(6) unsigned NOT NULL DEFAULT '0',
  `app` char(20) NOT NULL COMMENT '应用名称app',
  `model` char(20) NOT NULL COMMENT '控制器',
  `action` char(20) NOT NULL COMMENT '操作名称',
  `data` char(50) NOT NULL COMMENT '额外参数',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '菜单类型  1：权限认证+菜单；0：只作为菜单',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态，1显示，0不显示',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `remark` varchar(255) NOT NULL COMMENT '备注',
  `listorder` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '排序ID',
  PRIMARY KEY (`id`),
  KEY `status` (`status`),
  KEY `parentid` (`parentid`),
  KEY `model` (`model`)
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_new_hyd_investment
-- ----------------------------
DROP TABLE IF EXISTS `oa_new_hyd_investment`;
CREATE TABLE `oa_new_hyd_investment` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `time_month` int(6) unsigned NOT NULL COMMENT '所属年月',
  `time_day` int(8) unsigned NOT NULL COMMENT '详细日期',
  `user_id` mediumint(8) unsigned NOT NULL COMMENT '用户ID',
  `hyd_id` int(8) unsigned NOT NULL COMMENT '用户在crm的hyd id',
  `reg_time` int(10) unsigned NOT NULL COMMENT '注册时间',
  `sex` tinyint(1) unsigned NOT NULL COMMENT '性别',
  `age` tinyint(3) unsigned NOT NULL COMMENT '年龄',
  `invite_user_id` int(10) unsigned DEFAULT NULL COMMENT '投资时推荐人ID',
  `departmentid` int(5) unsigned NOT NULL COMMENT '所属部门ID',
  `success_time` int(10) unsigned NOT NULL COMMENT '销售日期(投资成功日期)',
  `customer` varchar(15) DEFAULT NULL COMMENT '客户姓名',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `borrow_nid` varchar(16) DEFAULT NULL COMMENT '标的编号',
  `borrow_name` varchar(50) DEFAULT NULL COMMENT '标的名称',
  `frozen_time` int(10) unsigned DEFAULT NULL COMMENT '投标冻结时间',
  `recover_time` int(10) unsigned DEFAULT NULL COMMENT '投标到期时间',
  `borrow_status` varchar(25) NOT NULL COMMENT '//标的状态',
  `unit` tinyint(2) unsigned DEFAULT NULL COMMENT '标期单位(天1，月2，年3)',
  `time_limit` tinyint(2) unsigned DEFAULT NULL COMMENT '期限',
  `borrow_money` decimal(11,2) NOT NULL COMMENT '投标金额',
  `borrow_type` tinyint(2) unsigned NOT NULL COMMENT '标的类型(0-4)',
  `borrow_apr` decimal(11,2) NOT NULL COMMENT '利率%',
  `nid` varchar(32) NOT NULL COMMENT '投资编号(唯一)',
  `mbdate` int(10) DEFAULT '0' COMMENT '满标放款日期',
  `department_id` int(10) DEFAULT NULL COMMENT '三级部门id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `hyd_id` (`hyd_id`),
  KEY `idx_success_time` (`success_time`) USING BTREE,
  KEY `idx_time_month` (`time_month`)
) ENGINE=InnoDB AUTO_INCREMENT=10342318 DEFAULT CHARSET=utf8 COMMENT='汇盈贷投资明细表';

-- ----------------------------
-- Table structure for oa_oauth_user
-- ----------------------------
DROP TABLE IF EXISTS `oa_oauth_user`;
CREATE TABLE `oa_oauth_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `from` varchar(20) NOT NULL COMMENT '用户来源key',
  `name` varchar(30) NOT NULL COMMENT '第三方昵称',
  `head_img` varchar(200) NOT NULL COMMENT '头像',
  `uid` int(20) NOT NULL COMMENT '关联的本站用户id',
  `create_time` datetime NOT NULL COMMENT '绑定时间',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(16) NOT NULL COMMENT '最后登录ip',
  `login_times` int(6) NOT NULL COMMENT '登录次数',
  `status` tinyint(2) NOT NULL,
  `access_token` varchar(60) NOT NULL,
  `expires_date` int(12) NOT NULL COMMENT 'access_token过期时间',
  `openid` varchar(40) NOT NULL COMMENT '第三方用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_options
-- ----------------------------
DROP TABLE IF EXISTS `oa_options`;
CREATE TABLE `oa_options` (
  `option_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `option_name` varchar(64) NOT NULL DEFAULT '',
  `option_value` longtext NOT NULL,
  `autoload` int(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`option_id`),
  UNIQUE KEY `option_name` (`option_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_position
-- ----------------------------
DROP TABLE IF EXISTS `oa_position`;
CREATE TABLE `oa_position` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '岗位名称',
  `description` varchar(255) NOT NULL COMMENT '岗位描述',
  `category_id` int(4) NOT NULL COMMENT '类别ID',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(1:正常,0:,3:删除)',
  `listorder` tinyint(3) NOT NULL COMMENT '排序',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8 COMMENT='岗位表（权限无关，管理员维护）';

-- ----------------------------
-- Table structure for oa_position_category
-- ----------------------------
DROP TABLE IF EXISTS `oa_position_category`;
CREATE TABLE `oa_position_category` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '岗位类别描述',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态(0:禁用;1:启用;2:删除)',
  `listorder` tinyint(4) NOT NULL COMMENT '排序',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='岗位分类表';

-- ----------------------------
-- Table structure for oa_rework_info
-- ----------------------------
DROP TABLE IF EXISTS `oa_rework_info`;
CREATE TABLE `oa_rework_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) NOT NULL COMMENT '员工ID',
  `entry_success_time` int(11) NOT NULL COMMENT '入职二审时间',
  `status` int(1) NOT NULL COMMENT '状态 0：未审核；1：审核通过；2：审核失败；3：作废',
  `submission_time` int(11) NOT NULL COMMENT '提交时间',
  `submissioner` varchar(10) NOT NULL COMMENT '提交人',
  `approve_time` int(11) DEFAULT NULL COMMENT '审核时间',
  `approver` varchar(10) DEFAULT NULL COMMENT '审核人',
  `department_id` int(8) DEFAULT NULL COMMENT '申请转正时所在部门id',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8 COMMENT='转正信息表';

-- ----------------------------
-- Table structure for oa_role
-- ----------------------------
DROP TABLE IF EXISTS `oa_role`;
CREATE TABLE `oa_role` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '角色名称',
  `pid` smallint(6) DEFAULT NULL COMMENT '父角色ID',
  `department_id` int(8) NOT NULL COMMENT '所属部门ID',
  `status` tinyint(1) unsigned DEFAULT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  `listorder` int(3) NOT NULL DEFAULT '0' COMMENT '排序字段',
  PRIMARY KEY (`id`),
  KEY `parentId` (`pid`),
  KEY `status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_role_user
-- ----------------------------
DROP TABLE IF EXISTS `oa_role_user`;
CREATE TABLE `oa_role_user` (
  `role_id` mediumint(9) unsigned DEFAULT NULL,
  `user_id` char(32) DEFAULT NULL,
  KEY `group_id` (`role_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_role_user_department
-- ----------------------------
DROP TABLE IF EXISTS `oa_role_user_department`;
CREATE TABLE `oa_role_user_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '员工ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `department_id` int(11) NOT NULL COMMENT '部门ID',
  `create_time` int(10) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `create_by` varchar(30) NOT NULL COMMENT '创建人ID',
  `create_user` varchar(30) NOT NULL COMMENT '创建人用户名',
  `update_time` int(10) DEFAULT '0' COMMENT '更新时间',
  `update_by` varchar(30) DEFAULT NULL COMMENT '更新人ID',
  `update_user` varchar(30) DEFAULT NULL COMMENT '更新人用户名',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24461 DEFAULT CHARSET=utf8 COMMENT='用户角色部门关联关系表';

-- ----------------------------
-- Table structure for oa_rtb_achievements
-- ----------------------------
DROP TABLE IF EXISTS `oa_rtb_achievements`;
CREATE TABLE `oa_rtb_achievements` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(7) NOT NULL COMMENT '用户的汇盈贷ID',
  `truename` varchar(20) NOT NULL COMMENT '用户真实姓名',
  `tasknum` int(6) NOT NULL COMMENT '//任务编号',
  `reg_time` int(10) NOT NULL COMMENT '注册时间',
  `reg_num` int(5) NOT NULL COMMENT '注册人数',
  `open_num` int(5) DEFAULT NULL COMMENT '开户人数',
  `this_num1` int(5) DEFAULT NULL COMMENT '时间范围内投资(0<投资<100)',
  `this_num2` int(5) DEFAULT NULL COMMENT '时间范围内投资(100≤投资<3000)',
  `this_num3` int(5) DEFAULT NULL COMMENT '时间范围内投资(3000≤投资)',
  `discounte_record` decimal(11,2) DEFAULT NULL,
  `department_id` int(10) DEFAULT NULL COMMENT '三级部门id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42074781 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_rtb_investment
-- ----------------------------
DROP TABLE IF EXISTS `oa_rtb_investment`;
CREATE TABLE `oa_rtb_investment` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `time_month` int(6) unsigned NOT NULL COMMENT '所属年月',
  `time_day` int(8) unsigned NOT NULL COMMENT '详细日期',
  `user_id` mediumint(8) unsigned NOT NULL COMMENT '用户ID',
  `hyd_id` int(8) unsigned NOT NULL COMMENT '用户在crm的hyd id',
  `reg_time` int(10) unsigned NOT NULL COMMENT '注册时间',
  `sex` tinyint(1) unsigned NOT NULL COMMENT '性别',
  `age` tinyint(3) unsigned NOT NULL COMMENT '年龄',
  `invite_user_id` int(10) unsigned DEFAULT NULL COMMENT '投资时推荐人ID',
  `departmentid` int(5) unsigned NOT NULL COMMENT '所属部门ID',
  `success_time` int(10) unsigned NOT NULL COMMENT '销售日期(投资成功日期)',
  `customer` varchar(15) DEFAULT NULL COMMENT '客户姓名',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `borrow_nid` varchar(16) DEFAULT NULL COMMENT '标的编号',
  `borrow_name` varchar(50) DEFAULT NULL COMMENT '标的名称',
  `frozen_time` int(10) unsigned DEFAULT NULL COMMENT '投标冻结时间',
  `recover_time` int(10) unsigned DEFAULT NULL COMMENT '投标到期时间',
  `borrow_status` varchar(25) NOT NULL COMMENT '//标的状态',
  `unit` tinyint(2) unsigned DEFAULT NULL COMMENT '标期单位(天1，月2，年3)',
  `time_limit` tinyint(2) unsigned DEFAULT NULL COMMENT '期限',
  `borrow_money` decimal(11,2) NOT NULL COMMENT '投标金额',
  `borrow_type` tinyint(2) unsigned NOT NULL COMMENT '标的类型(0-4)',
  `borrow_apr` decimal(11,2) NOT NULL COMMENT '利率%',
  `nid` varchar(32) NOT NULL COMMENT '投资编号(唯一)',
  `mbdate` int(10) DEFAULT '0' COMMENT '满标放款日期',
  `department_id` int(10) DEFAULT NULL COMMENT '三级部门id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `idx_success_time` (`success_time`) USING BTREE,
  KEY `idx_hyd_id` (`hyd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2904359 DEFAULT CHARSET=utf8 COMMENT='汇盈贷投资明细表';

-- ----------------------------
-- Table structure for oa_task
-- ----------------------------
DROP TABLE IF EXISTS `oa_task`;
CREATE TABLE `oa_task` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `disnum` int(3) NOT NULL COMMENT '//折比系数ID',
  `tasknum` char(6) NOT NULL COMMENT '任务编号（201504）',
  `yearnum` char(4) NOT NULL COMMENT '任务年编号',
  `monthnum` char(2) NOT NULL COMMENT '任务编号，月',
  `begintime` int(11) NOT NULL COMMENT '开始时间',
  `endtime` int(11) NOT NULL COMMENT '结束时间',
  `htjnum` int(3) DEFAULT NULL COMMENT '汇添金折比系数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tasknum` (`tasknum`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='任务设置表';

-- ----------------------------
-- Table structure for oa_task_discount
-- ----------------------------
DROP TABLE IF EXISTS `oa_task_discount`;
CREATE TABLE `oa_task_discount` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) NOT NULL COMMENT '任务折比名称',
  `addtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='任务折比系数表';

-- ----------------------------
-- Table structure for oa_task_discount_detail
-- ----------------------------
DROP TABLE IF EXISTS `oa_task_discount_detail`;
CREATE TABLE `oa_task_discount_detail` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `discountid` int(4) NOT NULL COMMENT '折比ID',
  `time_limit` int(11) DEFAULT NULL COMMENT '产品日期',
  `ratio` decimal(10,8) DEFAULT NULL COMMENT '折比系数',
  `category` int(11) DEFAULT NULL COMMENT '产品日期类别(0:月 1:天)',
  PRIMARY KEY (`id`),
  KEY `discountid` (`discountid`,`time_limit`)
) ENGINE=InnoDB AUTO_INCREMENT=392 DEFAULT CHARSET=utf8 COMMENT='任务折比系数明细表';

-- ----------------------------
-- Table structure for oa_transfer_position
-- ----------------------------
DROP TABLE IF EXISTS `oa_transfer_position`;
CREATE TABLE `oa_transfer_position` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(8) DEFAULT NULL COMMENT '当事人',
  `bef_department_id` int(8) DEFAULT NULL COMMENT '原部门',
  `aft_department_id` int(8) DEFAULT NULL COMMENT '现部门',
  `bef_role_id` int(8) DEFAULT NULL COMMENT '原角色',
  `aft_role_id` int(8) DEFAULT NULL COMMENT '现角色',
  `bef_temporary` int(2) DEFAULT NULL COMMENT '原来： 1,兼职/2,正式员工',
  `aft_temporary` int(2) DEFAULT NULL COMMENT '现在： 1,兼职/2,正式员工',
  `creater` int(8) DEFAULT NULL COMMENT '执行操作人uid',
  `createtime` char(11) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1477476106 DEFAULT CHARSET=utf8 COMMENT='员工调岗信息表';

-- ----------------------------
-- Table structure for oa_user_cuttpye_log
-- ----------------------------
DROP TABLE IF EXISTS `oa_user_cuttpye_log`;
CREATE TABLE `oa_user_cuttpye_log` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` int(6) NOT NULL COMMENT '被操作用户ID',
  `operator_user_id` int(6) NOT NULL COMMENT '操作者用户ID',
  `departmentid` int(5) NOT NULL COMMENT '所在部门ID',
  `old_cuttype` int(2) NOT NULL DEFAULT '0' COMMENT '用户原始Cuttype. 用户新入职为:0',
  `new_cuttype` int(2) NOT NULL COMMENT '用户当前的cuttype',
  `explain_log` varchar(80) NOT NULL COMMENT '操作说明',
  `addip` varchar(15) NOT NULL COMMENT '操作者ip',
  `addtime` int(10) NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=784 DEFAULT CHARSET=utf8 COMMENT='用户cuttype日志表.用户入职时old_cuttype为零,其他状态时为当前所属部门的cuttype状态,new_cuttype为当前的.';

-- ----------------------------
-- Table structure for oa_user_entry
-- ----------------------------
DROP TABLE IF EXISTS `oa_user_entry`;
CREATE TABLE `oa_user_entry` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `otype` char(1) NOT NULL COMMENT '工单类型',
  `oprocess` char(6) NOT NULL COMMENT '工单进度（E1 入职一级审批 E2 入职二级审批 Q1离职一级审批Q2离职二级审批）',
  `ostatus` char(1) NOT NULL COMMENT '工单状态0：不成功 1：成功通过',
  `userid` int(8) NOT NULL COMMENT '当事人',
  `creater` int(8) NOT NULL COMMENT '执行操作人uid',
  `remark` varchar(255) NOT NULL COMMENT '备注',
  `createtime` char(11) NOT NULL COMMENT '操作时间',
  `entrydate` date DEFAULT NULL,
  `department_id` int(8) DEFAULT NULL COMMENT '入职时所在部门id',
  `position_id` int(8) DEFAULT NULL COMMENT '入职时岗位名称',
  `level` int(8) DEFAULT NULL COMMENT '入职时角色等级',
  `temporary` int(2) DEFAULT NULL COMMENT '入职时： 1,兼职/2,正式员工',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=7456 DEFAULT CHARSET=utf8 COMMENT='员工入职工单日志表';

-- ----------------------------
-- Table structure for oa_user_leave
-- ----------------------------
DROP TABLE IF EXISTS `oa_user_leave`;
CREATE TABLE `oa_user_leave` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `oprocess` char(6) NOT NULL COMMENT '工单进度（E1 入职一级审批 E2 入职二级审批 Q1离职一级审批Q2离职二级审批）',
  `userid` int(8) NOT NULL COMMENT '当事人',
  `creater` int(4) NOT NULL COMMENT '申请离职员工',
  `leave_time` date NOT NULL DEFAULT '0000-00-00' COMMENT '//离职日期',
  `end_time` date NOT NULL DEFAULT '0000-00-00' COMMENT '//工薪截止日期',
  `remark` int(2) NOT NULL COMMENT '//离职原因',
  `with_remark` varchar(160) NOT NULL COMMENT '离职原因',
  `f_creater` int(3) NOT NULL COMMENT '//一审员工',
  `f_remark` varchar(255) NOT NULL COMMENT '//一审意见',
  `f_time` char(11) NOT NULL COMMENT '//第一次审核时间',
  `s_creater` int(3) NOT NULL COMMENT '//二审员工',
  `s_remark` varchar(255) NOT NULL COMMENT '//二审意见',
  `s_time` char(11) NOT NULL COMMENT '//第二次审核时间',
  `q_creater` int(3) NOT NULL COMMENT '//取消离职员工',
  `q_remark` varchar(80) NOT NULL COMMENT '//是否是取消离职',
  `q_time` char(11) NOT NULL COMMENT '//取消离职时间',
  `createtime` char(11) NOT NULL COMMENT '操作时间',
  `department_id` int(8) DEFAULT NULL COMMENT '离职时所在部门id',
  `position_id` int(8) DEFAULT NULL COMMENT '离职时岗位名称',
  `level` int(8) DEFAULT NULL COMMENT '离职时角色等级',
  `temporary` int(2) DEFAULT NULL COMMENT '离职时： 1,兼职/2,正式员工',
  `entry_time` int(11) DEFAULT NULL COMMENT '入职时间',
  `f_check_result` int(10) DEFAULT NULL COMMENT '一审结果1成功0失败',
  `s_check_time` int(10) DEFAULT NULL COMMENT '二审结果1成功0失败',
  `t_check_time` int(10) DEFAULT NULL COMMENT '三审结果1成功0失败',
  `t_creater` int(10) NOT NULL COMMENT '//三审员工',
  `t_remark` varchar(255) NOT NULL COMMENT '//三审意见',
  `t_time` char(11) NOT NULL COMMENT '//第三次审核时间',
  PRIMARY KEY (`id`),
  KEY `idx_userid` (`userid`),
  KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1809 DEFAULT CHARSET=utf8 COMMENT='员工离职工单日志表';

-- ----------------------------
-- Table structure for oa_users
-- ----------------------------
DROP TABLE IF EXISTS `oa_users`;
CREATE TABLE `oa_users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_login` varchar(60) NOT NULL DEFAULT '' COMMENT '用户名',
  `user_pass` varchar(64) NOT NULL DEFAULT '' COMMENT '登录密码；oa_password加密',
  `user_realname` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `user_email` varchar(100) NOT NULL DEFAULT '' COMMENT '登录邮箱',
  `idcard` varchar(20) NOT NULL DEFAULT '' COMMENT '身份证号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像，相对于upload/avatar目录',
  `sex` smallint(1) DEFAULT '0' COMMENT '性别；0：保密，1：男；2：女',
  `acc_province` varchar(120) NOT NULL COMMENT '户口省份',
  `acc_city` varchar(20) NOT NULL COMMENT '户口城市',
  `acc_address` varchar(120) NOT NULL COMMENT '户口所在地址',
  `departmentid` int(8) NOT NULL COMMENT '部门',
  `positionid` int(8) NOT NULL COMMENT '岗位名称',
  `level` smallint(1) NOT NULL COMMENT '角色等级: 2-员工/1-leader',
  `temporary` int(2) NOT NULL DEFAULT '2' COMMENT '1,兼职/2,正式员工',
  `rework` int(1) DEFAULT NULL COMMENT '是否转正 0：试用期 1：转正',
  `rework_time` varchar(20) DEFAULT NULL COMMENT '入职时间或角色（正式/兼职）修改时间',
  `ispart` char(1) NOT NULL COMMENT 'Y/N',
  `payroll_try` int(8) DEFAULT NULL COMMENT '试用期月薪',
  `payroll` int(8) DEFAULT NULL COMMENT '转正月薪',
  `entrydate` date NOT NULL COMMENT '入职时间',
  `reference` varchar(20) NOT NULL COMMENT '入职推荐人',
  `education` tinyint(2) NOT NULL COMMENT '学历',
  `school` varchar(30) NOT NULL COMMENT '毕业院校',
  `specialty` varchar(30) NOT NULL COMMENT '专业',
  `mobile` char(11) NOT NULL COMMENT '手机',
  `last_login_ip` varchar(16) NOT NULL COMMENT '最后登录ip',
  `last_login_time` char(11) NOT NULL COMMENT '最后登录时间',
  `create_time` char(11) NOT NULL COMMENT '注册时间',
  `bank_address` varchar(80) NOT NULL COMMENT '开户行地址',
  `bank_user` varchar(30) DEFAULT NULL COMMENT '开户人姓名',
  `bank_num` varchar(30) DEFAULT NULL COMMENT '银行卡账号',
  `user_status` char(3) NOT NULL DEFAULT 'E' COMMENT '用户状态 E1:一级未审核;E2二级未审核 ；E已入职；E11一级审核未通过；E21二级审核未通过;E3取消入职；Q已离职；Q1离职一级未审核；Q2离职一级审核未通过；Q2离职二级未审核；Q22离职二级审核未通过；Q3取消离职',
  `age` int(3) NOT NULL COMMENT '年龄',
  `hyd_name` varchar(20) NOT NULL COMMENT '平台账号',
  `hyd_id` int(8) NOT NULL COMMENT '//汇盈贷账号ID',
  `user_type` smallint(1) DEFAULT '2' COMMENT '用户类型，1:admin ;2:会员',
  `entry_success_time` int(11) DEFAULT NULL COMMENT '入职终审通过时间',
  `leave_success_time` int(11) DEFAULT NULL COMMENT '离职终审通过时间',
  `safety_high` tinyint(1) NOT NULL DEFAULT '0' COMMENT '安全性是否高？',
  PRIMARY KEY (`id`),
  KEY `idx_user_login_` (`user_login`),
  KEY `idx_user_nicename` (`user_realname`),
  KEY `idx_hyd_id` (`hyd_id`),
  KEY `idx_departmentid` (`departmentid`),
  KEY `idx_hyd_name` (`hyd_name`),
  KEY `idx_user_status` (`user_status`),
  KEY `idx_idcard` (`idcard`)
) ENGINE=InnoDB AUTO_INCREMENT=11437 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for temp_ht_account
-- ----------------------------
DROP TABLE IF EXISTS `temp_ht_account`;
CREATE TABLE `temp_ht_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `account_id` varchar(20) DEFAULT NULL COMMENT '用户电子账户号(开户后,维护)',
  `total` decimal(21,2) DEFAULT '0.00' COMMENT '资金总额',
  `balance` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '可用金额',
  `balance_cash` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '可提现',
  `balance_frost` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '不可提现',
  `frost` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `await` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待收金额',
  `repay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还金额',
  `plan_repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '计划累计收益',
  `plan_balance` decimal(11,2) DEFAULT '0.00' COMMENT '计划可用余额',
  `plan_frost` decimal(11,2) DEFAULT '0.00' COMMENT '计划冻结金额',
  `plan_accede_total` decimal(11,2) DEFAULT '0.00' COMMENT '计划累计加入金额',
  `plan_accede_balance` decimal(11,2) DEFAULT '0.00' COMMENT '计划加入可用余额',
  `plan_accede_frost` decimal(11,2) DEFAULT '0.00' COMMENT '投资汇添金标的投资的未放款金额',
  `plan_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '计划累计待收总额',
  `plan_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '计划累计待收本金',
  `plan_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '计划待收利息',
  `bank_total` decimal(21,2) DEFAULT '0.00' COMMENT '银行总资产',
  `bank_balance` decimal(11,2) DEFAULT '0.00' COMMENT '江西银行可用余额',
  `bank_frost` decimal(11,2) DEFAULT '0.00' COMMENT '银行冻结金额',
  `bank_wait_repay` decimal(11,2) DEFAULT '0.00' COMMENT '银行待还本息',
  `bank_wait_capital` decimal(11,2) DEFAULT '0.00' COMMENT '银行待还本金',
  `bank_wait_interest` decimal(11,2) DEFAULT '0.00' COMMENT '银行待还利息',
  `bank_wait_repay_org` decimal(11,2) DEFAULT '0.00' COMMENT '待还垫付机构金额',
  `bank_interest_sum` decimal(21,2) DEFAULT '0.00' COMMENT '银行累计收益',
  `bank_invest_sum` decimal(21,2) DEFAULT '0.00' COMMENT '银行累计投资',
  `bank_await` decimal(11,2) DEFAULT '0.00' COMMENT '银行待收总额',
  `bank_await_capital` decimal(11,2) DEFAULT '0.00' COMMENT '银行待收本金',
  `bank_await_interest` decimal(11,2) DEFAULT '0.00' COMMENT '银行待收利息',
  `bank_await_org` decimal(11,2) DEFAULT '0.00' COMMENT '银行垫付机构待收垫付总额',
  `bank_balance_cash` decimal(11,2) DEFAULT '0.00' COMMENT '江西银行可提现金额(银行电子账户余额)',
  `bank_frost_cash` decimal(11,2) DEFAULT '0.00' COMMENT '江西银行冻结金额(银行电子账户冻结金额)',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `idx_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1806 DEFAULT CHARSET=utf8 COMMENT='账户信息管理';

-- ----------------------------
-- Table structure for temp_ht_borrow_log
-- ----------------------------
DROP TABLE IF EXISTS `temp_ht_borrow_log`;
CREATE TABLE `temp_ht_borrow_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款编号',
  `borrow_status_cd` int(8) DEFAULT NULL COMMENT '项目状态',
  `borrow_status` varchar(50) DEFAULT NULL COMMENT '项目状态名称',
  `type` varchar(50) DEFAULT NULL COMMENT '修改类型-创建标的-新增-修改-删除',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建用户Name',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
